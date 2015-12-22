package de.dogedevs.photoria.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import de.dogedevs.photoria.generators.AbstractMapGenerator;
import de.dogedevs.photoria.generators.ChunkDebugMapGenerator;
import de.dogedevs.photoria.model.map.ChunkBuffer;
import de.dogedevs.photoria.model.map.ChunkCell;
import de.dogedevs.photoria.rendering.tiles.Tile;
import de.dogedevs.photoria.rendering.tiles.TileMapper;

import java.util.HashMap;

/** @brief Layer for a TiledMap */
public class DebugChunkLayer extends TiledMapTileLayer {

	private int width;
	private int height;

	private float tileWidth;
	private float tileHeight;

	Cell[][] cells;

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
	public DebugChunkLayer(int tileWidth, int tileHeight) {
		super(1, 1, tileWidth, tileHeight);
		this.width = Integer.MAX_VALUE;
		this.height = Integer.MAX_VALUE;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.cells = new Cell[64][64];
		for(int x = 0; x < cells.length; x++){
			for (int y = 0; y < cells[x].length; y++){
				cells[x][y] = new Cell();
				if(x == 0 || y == 0){
					cells[x][y].setTile(Tile.DEBUG);
				}
			}
		}
	}

	/** @param x X coordinate
	 * @param y Y coordinate
	 * @return {@link Cell} at (x, y) */
	public Cell getCell (int x, int y) {
		return cells[x%64][y%64];
	}

	/** Sets the {@link Cell} at the given coordinates.
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param cell the {@link Cell} to set at the given coordinates. */
	public void setCell (int x, int y, Cell cell) {
		cells[x%64][y%64] = cell;
	}

}