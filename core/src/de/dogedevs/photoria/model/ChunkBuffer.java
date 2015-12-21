package de.dogedevs.photoria.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import de.dogedevs.photoria.generators.AbstractMapGenerator;
import de.dogedevs.photoria.generators.ChunkDebugMapGenerator;
import de.dogedevs.photoria.generators.SimplexMapGenerator;
import de.dogedevs.photoria.rendering.tiles.Tile;

import java.util.HashMap;

/**
 * Created by Furuha on 21.12.2015.
 */
public class ChunkBuffer {

    HashMap<String, Chunk> chunks; //String x+"_"+y
    private AbstractMapGenerator generator;

    public ChunkBuffer(){
        chunks = new HashMap<>();
        generator = new SimplexMapGenerator();
    }

    public ChunkCell getCell(int x, int y, int layer) {

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

            ChunkCell cell;
            chunk.addLayer(1, new ChunkCell[64][64]);
            for (int row = 0; row < 32 << 1; row++) {
                for (int col = 0; col < 32 << 1; col++) {
                    cell = new ChunkCell();
                    cell.value = generatedMap[row][col];
                    if(generator instanceof ChunkDebugMapGenerator){
                        chunk.setCell(cell, row, col, 1);
                        continue;
                    }
                    chunk.setCell(cell, row, col, 1);
                }
            }

            generatedMap = generator.generate(chunk.x, chunk.y, 64);

            chunk.addLayer(2, new ChunkCell[64][64]);
            for (int row = 0; row < 32 << 1; row++) {
                for (int col = 0; col < 32 << 1; col++) {
                    cell = new ChunkCell();
                    cell.value = generatedMap[row][col];
                    if(generator instanceof ChunkDebugMapGenerator){
                        chunk.setCell(cell, row, col, 2);
                        continue;
                    }
                    chunk.setCell(cell, row, col, 2);
                }
            }

        }

        return chunks.get((x/64)+"_"+(y/64)).getCell(x,y, layer);
    }

}
