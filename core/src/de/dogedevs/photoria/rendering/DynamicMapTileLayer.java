package de.dogedevs.photoria.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import de.dogedevs.photoria.generators.AbstractMapGenerator;
import de.dogedevs.photoria.generators.ChunkDebugMapGenerator;
import de.dogedevs.photoria.model.ChunkBuffer;
import de.dogedevs.photoria.model.ChunkCell;
import de.dogedevs.photoria.rendering.tiles.Tile;
import de.dogedevs.photoria.rendering.tiles.TileMapper;

import java.util.HashMap;

/** @brief Layer for a TiledMap */
public class DynamicMapTileLayer extends TiledMapTileLayer {

	private AbstractMapGenerator generator;
	private int width;
	private int height;

	private float tileWidth;
	private float tileHeight;

	private ChunkBuffer buffer;
	private int layer;

//	public Chunk[][] chunks;
	public HashMap<String, Chunk> chunks;

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
	public DynamicMapTileLayer(AbstractMapGenerator generator, int tileWidth, int tileHeight, int layer, ChunkBuffer buffer) {
		super(1, 1, tileWidth, tileHeight);
		this.width = Integer.MAX_VALUE;
		this.height = Integer.MAX_VALUE;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.chunks = new HashMap<>();
		this.generator = generator;
		this.buffer = buffer;
		this.layer = layer;
	}

	/** @param x X coordinate
	 * @param y Y coordinate
	 * @return {@link Cell} at (x, y) */
	public Cell getCell (int x, int y) {
//		MainGame.log("Chunks: "+chunks.size());

		if(generator instanceof ChunkDebugMapGenerator){
			Chunk chunk = chunks.get((x/64)+"_"+(y/64));
			if(chunk == null){
				chunk = new Chunk();
				chunk.x = x/64;
				chunk.y = y/64;

				for(final String key: chunks.keySet()){
					if(System.currentTimeMillis()-chunks.get(key).lastRead > 1000){
						Gdx.app.postRunnable(new Runnable() {
							@Override
							public void run() {
								chunks.remove(key);
							}
						});
					}
				}

				chunks.put(chunk.getHashCode(), chunk);

				int[][] generatedMap = generator.generate(chunk.x, chunk.y, 64);

				for (int row = 0; row < 32 << 1; row++) {
					for (int col = 0; col < 32 << 1; col++) {
						TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
							if(generatedMap[row][col] == 1){
								cell.setTile(Tile.DEBUG);
							}
							chunk.setCell(cell, row, col);
							continue;
					}
				}

			}

			return chunk.getCell(x, y);
		}
		ChunkCell chunkCell = this.buffer.getCell(x,y,layer);

		if(chunkCell.cell == null){
			chunkCell.cell = new Cell();
			switch(chunkCell.value) {
				default:
				case TileMapper.VOID : chunkCell.cell.setTile(Tile.VOID); break;
				case TileMapper.GROUND : chunkCell.cell.setTile(Tile.GROUND); break;
				case TileMapper.WATER : chunkCell.cell.setTile(Tile.WATER); break;
				case TileMapper.LAVA_STONE : chunkCell.cell.setTile(Tile.LAVA_STONE); break;
				case TileMapper.LAVA : chunkCell.cell.setTile(Tile.LAVA); break;

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
//		if (x < 0 || x >= width) return;
//		if (y < 0 || y >= height) return;
//		cells[x][y] = cell;
	}

	public static class Chunk{
		public int x;
		public int y;

		public long lastRead;

		Cell[][] cells;

		public Chunk(int x, int y, Cell[][] cells) {
			this.x = x;
			this.y = y;
			this.cells = cells;
			this.lastRead = System.currentTimeMillis();
		}

		public Chunk() {
			this.cells = new Cell[64][64];
			this.lastRead = System.currentTimeMillis();
		}

		public void setCell(Cell cell, int x, int y){
			cells[x][y] = cell;
		}

		public Cell getCell(int x, int y){
			this.lastRead = System.currentTimeMillis();
			return cells[x%64][y%64];
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Chunk chunkPos = (Chunk) o;

			if (x != chunkPos.x) return false;
			return y == chunkPos.y;

		}

		public String getHashCode(){
			return x+"_"+y;
		}
	}

}