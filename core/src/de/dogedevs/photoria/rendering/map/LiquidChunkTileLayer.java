package de.dogedevs.photoria.rendering.map;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.model.map.ChunkBuffer;
import de.dogedevs.photoria.model.map.ChunkCell;
import de.dogedevs.photoria.rendering.tiles.Tile;
import de.dogedevs.photoria.rendering.tiles.TileMapper;

/** @brief Layer for a TiledMap */
public class LiquidChunkTileLayer extends TiledMapTileLayer {

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
    public LiquidChunkTileLayer(int tileWidth, int tileHeight, int layer, ChunkBuffer buffer) {
        super(1, 1, tileWidth, tileHeight);
        this.width = Integer.MAX_VALUE;
        this.height = Integer.MAX_VALUE;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.buffer = buffer;
        this.layer = layer;
    }

    /** @param x X coordinate
     * @param y Y coordinate
     * @return {@link Cell} at (x, y) */
    public Cell getCell (int x, int y) {
//		MainGame.log("Chunks: "+chunks.size());
        ChunkCell chunkCell = null;
        try{
            chunkCell = this.buffer.getCell(x, y, layer);
        } catch (Exception e){
            e.printStackTrace();
        }

        if(chunkCell.cell == null){
            chunkCell.cell = new Cell();
            switch(chunkCell.value) {
                default:
                case TileMapper.VOID : break;
                case TileMapper.WATER :
                    if(MathUtils.random(25) == 0){
                        switch(MathUtils.random(2)){
                            case 0 : chunkCell.cell.setTile(Tile.WATER2); break;
                            case 1 : chunkCell.cell.setTile(Tile.WATER3); break;
                            case 2 : chunkCell.cell.setTile(Tile.WATER4); break;
                        }
                    } else {
                        chunkCell.cell.setTile(Tile.WATER);
                    }
                    break;
                case TileMapper.LAVA :
                    if(MathUtils.random(25) == 0){
                        switch(MathUtils.random(2)){
                            case 0 : chunkCell.cell.setTile(Tile.LAVA2); break;
                            case 1 : chunkCell.cell.setTile(Tile.LAVA3); break;
                            case 2 : chunkCell.cell.setTile(Tile.LAVA4); break;
                        }
                    } else {
                        chunkCell.cell.setTile(Tile.LAVA);
                    }
                    break;
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