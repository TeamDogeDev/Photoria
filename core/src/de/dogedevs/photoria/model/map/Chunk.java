package de.dogedevs.photoria.model.map;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Furuha on 21.12.2015.
 */
public class Chunk {

    Map<Integer, ChunkCell[][]> cells;
    public int x;
    public int y;

    public long lastRead;

    public Chunk(int x, int y) {
        this.x = x;
        this.y = y;
        this.cells = new HashMap<>();
        this.lastRead = System.currentTimeMillis();
    }

    public Chunk() {
        this(0, 0);
    }

    public void addLayer(Integer layerID, ChunkCell[][] layer){
        cells.put(layerID, layer);
    }

    //Todo: What to do if layer is missing!?
    public void setCell(ChunkCell cell, int x, int y, int layer){
        cells.get(layer)[x][y] = cell;
    }

    public ChunkCell getCell(int x, int y, int layer){
        this.lastRead = System.currentTimeMillis();
        return cells.get(layer)[x%64][y%64];
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

    public ChunkCell getCellLazy(int x, int y, int layer) {
        return cells.get(layer)[x%64][y%64];
    }
}
