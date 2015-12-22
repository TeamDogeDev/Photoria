package de.dogedevs.photoria.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.generators.AbstractMapGenerator;
import de.dogedevs.photoria.generators.ChunkDebugMapGenerator;
import de.dogedevs.photoria.model.map.ChunkBuffer;
import de.dogedevs.photoria.model.map.ChunkCell;
import de.dogedevs.photoria.rendering.tiles.Tile;
import de.dogedevs.photoria.rendering.tiles.TileMapper;

import java.util.HashMap;

/** @brief Layer for a TiledMap */
public class ChunkTileLayer extends TiledMapTileLayer {

	private AbstractMapGenerator generator;
	private int width;
	private int height;

	private float tileWidth;
	private float tileHeight;

	private ChunkBuffer buffer;
	private int layer;

	/** @return layer's width in tiles */
	public int getWidth () {
		return width;
	}

	/** @return layer's height in tiles */
	public int getHeight () {
		return height;
	}

	/** @return tiles' width in pixels */
	public float getTileWidth () {
		return tileWidth;
	}

	/** @return tiles' height in pixels */
	public float getTileHeight () {
		return tileHeight;
	}

	/** Creates TiledMap layer
	 * 
	 * @param tileWidth tile width in pixels
	 * @param tileHeight tile height in pixels */
	public ChunkTileLayer(AbstractMapGenerator generator, int tileWidth, int tileHeight, int layer, ChunkBuffer buffer) {
		super(1, 1, tileWidth, tileHeight);
		this.width = Integer.MAX_VALUE;
		this.height = Integer.MAX_VALUE;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.generator = generator;
		this.buffer = buffer;
		this.layer = layer;
	}

	/** @param x X coordinate
	 * @param y Y coordinate
	 * @return {@link Cell} at (x, y) */
	public Cell getCell (int x, int y) {
//		MainGame.log("Chunks: "+chunks.size());

		ChunkCell chunkCell = this.buffer.getCell(x, y, layer);

		if(chunkCell.cell == null){
			chunkCell.cell = new Cell();
			switch(chunkCell.value) {
				default:
				case TileMapper.VOID : break;
				case TileMapper.GROUND : chunkCell.cell.setTile(Tile.GROUND); break;
				case TileMapper.LAVA_STONE : chunkCell.cell.setTile(Tile.LAVA_STONE); break;

				case TileMapper.WATER :
					if(MathUtils.random(50) == 0){
						switch(MathUtils.random(2)){
							case 0 : chunkCell.cell.setTile(Tile.WATER4); break;
							case 1 : chunkCell.cell.setTile(Tile.WATER3); break;
							case 2 : chunkCell.cell.setTile(Tile.WATER4); break;
						}
					} else {
						chunkCell.cell.setTile(Tile.WATER);
					}
					break;
				case TileMapper.WATER_TOP_LEFT: chunkCell.cell.setTile(Tile.WATER_TOP_LEFT); break;
				case TileMapper.WATER_TOP_MIDDLE: chunkCell.cell.setTile(Tile.WATER_TOP_MIDDLE); break;
				case TileMapper.WATER_TOP_RIGHT: chunkCell.cell.setTile(Tile.WATER_TOP_RIGHT); break;
				case TileMapper.WATER_MIDDLE_LEFT: chunkCell.cell.setTile(Tile.WATER_MIDDLE_LEFT); break;
				case TileMapper.WATER_MIDDLE_RIGHT: chunkCell.cell.setTile(Tile.WATER_MIDDLE_RIGHT); break;
				case TileMapper.WATER_BOTTOM_LEFT: chunkCell.cell.setTile(Tile.WATER_BOTTOM_LEFT); break;
				case TileMapper.WATER_BOTTOM_MIDDLE: chunkCell.cell.setTile(Tile.WATER_BOTTOM_MIDDLE); break;
				case TileMapper.WATER_BOTTOM_RIGHT: chunkCell.cell.setTile(Tile.WATER_BOTTOM_RIGHT); break;

				case TileMapper.WATER_TOP_LEFT_INNER: chunkCell.cell.setTile(Tile.WATER_TOP_LEFT_INNER); break;
				case TileMapper.WATER_TOP_RIGHT_INNER: chunkCell.cell.setTile(Tile.WATER_TOP_RIGHT_INNER); break;
				case TileMapper.WATER_BOTTOM_LEFT_INNER: chunkCell.cell.setTile(Tile.WATER_BOTTOM_LEFT_INNER); break;
				case TileMapper.WATER_BOTTOM_RIGHT_INNER: chunkCell.cell.setTile(Tile.WATER_BOTTOM_RIGHT_INNER); break;

				case TileMapper.LAVA : chunkCell.cell.setTile(Tile.LAVA); break;
				case TileMapper.LAVA_TOP_LEFT: chunkCell.cell.setTile(Tile.LAVA_TOP_LEFT); break;
				case TileMapper.LAVA_TOP_MIDDLE: chunkCell.cell.setTile(Tile.LAVA_TOP_MIDDLE); break;
				case TileMapper.LAVA_TOP_RIGHT: chunkCell.cell.setTile(Tile.LAVA_TOP_RIGHT); break;
				case TileMapper.LAVA_MIDDLE_LEFT: chunkCell.cell.setTile(Tile.LAVA_MIDDLE_LEFT); break;
				case TileMapper.LAVA_MIDDLE_RIGHT: chunkCell.cell.setTile(Tile.LAVA_MIDDLE_RIGHT); break;
				case TileMapper.LAVA_BOTTOM_LEFT: chunkCell.cell.setTile(Tile.LAVA_BOTTOM_LEFT); break;
				case TileMapper.LAVA_BOTTOM_MIDDLE: chunkCell.cell.setTile(Tile.LAVA_BOTTOM_MIDDLE); break;
				case TileMapper.LAVA_BOTTOM_RIGHT: chunkCell.cell.setTile(Tile.LAVA_BOTTOM_RIGHT); break;

				case TileMapper.LAVA_TOP_LEFT_INNER: chunkCell.cell.setTile(Tile.LAVA_TOP_LEFT_INNER); break;
				case TileMapper.LAVA_TOP_RIGHT_INNER: chunkCell.cell.setTile(Tile.LAVA_TOP_RIGHT_INNER); break;
				case TileMapper.LAVA_BOTTOM_LEFT_INNER: chunkCell.cell.setTile(Tile.LAVA_BOTTOM_LEFT_INNER); break;
				case TileMapper.LAVA_BOTTOM_RIGHT_INNER: chunkCell.cell.setTile(Tile.LAVA_BOTTOM_RIGHT_INNER); break;

				case TileMapper.LAVA_STONE_TOP_LEFT: chunkCell.cell.setTile(Tile.LAVA_STONE_TOP_LEFT); break;
				case TileMapper.LAVA_STONE_TOP_MIDDLE: chunkCell.cell.setTile(Tile.LAVA_STONE_TOP_MIDDLE); break;
				case TileMapper.LAVA_STONE_BOTTOM_RIGHT_INNER: chunkCell.cell.setTile(Tile.LAVA_STONE_BOTTOM_RIGHT_INNER); break;
				case TileMapper.LAVA_STONE_LEFT_MIDDLE: chunkCell.cell.setTile(Tile.LAVA_STONE_LEFT_MIDDLE); break;

				case TileMapper.LAVA_STONE_BOTTOM_LEFT_0: chunkCell.cell.setTile(Tile.LAVA_STONE_BOTTOM_LEFT_0); break;
				case TileMapper.LAVA_STONE_BOTTOM_LEFT_1: chunkCell.cell.setTile(Tile.LAVA_STONE_BOTTOM_LEFT_1); break;
				case TileMapper.LAVA_STONE_BOTTOM_LEFT_2: chunkCell.cell.setTile(Tile.LAVA_STONE_BOTTOM_LEFT_2); break;

				case TileMapper.LAVA_STONE_BOTTOM_MIDDLE_0: chunkCell.cell.setTile(Tile.LAVA_STONE_BOTTOM_MIDDLE_0); break;
				case TileMapper.LAVA_STONE_BOTTOM_MIDDLE_1: chunkCell.cell.setTile(Tile.LAVA_STONE_BOTTOM_MIDDLE_1); break;
				case TileMapper.LAVA_STONE_BOTTOM_MIDDLE_2: chunkCell.cell.setTile(Tile.LAVA_STONE_BOTTOM_MIDDLE_2); break;

				case TileMapper.LAVA_STONE_TOP_RIGHT_INNER: chunkCell.cell.setTile(Tile.LAVA_STONE_TOP_RIGHT_INNER); break;
//				case -6 : chunkCell.cell.setTile(Tile.WATER); break;
//				case -5 : chunkCell.cell.setTile(Tile.WATER); break;
//				case -4 : chunkCell.cell.setTile(Tile.WATER); break;
//				case -3 : chunkCell.cell.setTile(Tile.WATER); break;
//				case -2 : chunkCell.cell.setTile(Tile.GROUND); break;
//				case -1 : chunkCell.cell.setTile(Tile.GROUND); break;
//				case 0 : chunkCell.cell.setTile(Tile.GROUND); break;
//				case 1 : chunkCell.cell.setTile(Tile.LAVA_STONE); break;
//				case 2 : chunkCell.cell.setTile(Tile.LAVA_STONE); break;
//				case 3 : chunkCell.cell.setTile(Tile.LAVA_STONE); break;
//				case 4 : chunkCell.cell.setTile(Tile.LAVA); break;
//				case 5 : chunkCell.cell.setTile(Tile.LAVA); break;
//				case 6 : chunkCell.cell.setTile(Tile.LAVA); break;
//				default: chunkCell.cell.setTile(Tile.VOID);
			}
		}

		return this.buffer.getCell(x,y,layer).cell;
	}

	/** Sets the {@link Cell} at the given coordinates.
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param cell the {@link Cell} to set at the given coordinates. */
	public void setCell (int x, int y, Cell cell) {
		//ChunkBuffer setCell(cell, x, y, layer) should exist here
	}

}