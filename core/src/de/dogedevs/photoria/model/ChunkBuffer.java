package de.dogedevs.photoria.model;

import com.badlogic.gdx.Gdx;
import de.dogedevs.photoria.generators.*;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Furuha on 21.12.2015.
 */
public class ChunkBuffer {

    HashMap<String, Chunk> chunks; //String x+"_"+y
    private AbstractMapGenerator generator;
    private AbstractMapDecorator decorator;

    public static final int CHUNK_SIZE = 64;

    public ChunkBuffer(){
        chunks = new HashMap<>();
        generator = new SimplexMapGenerator();
        decorator = new MapDecorator();
    }

    private int[][] createGroundLayer(Chunk chunk, int[][] generatedMap, int overlap) {
        ChunkCell cell;
        chunk.addLayer(1, new ChunkCell[CHUNK_SIZE][CHUNK_SIZE]);
        for (int row = overlap; row < CHUNK_SIZE+overlap; row++) {
            for (int col = overlap; col < CHUNK_SIZE+overlap; col++) {
                cell = new ChunkCell();
                cell.value = generatedMap[row][col];
                if(generator instanceof ChunkDebugMapGenerator){
                    chunk.setCell(cell, row-overlap, col-overlap, 1);
                    continue;
                }
                chunk.setCell(cell, row-overlap, col-overlap, 1);
            }
        }

        return generatedMap;
    }

    private void createDecoration(Chunk chunk, int[][] generatedMap, int overlap) {
        generatedMap = decorator.decorate(generatedMap);

        ChunkCell cell;
        chunk.addLayer(2, new ChunkCell[CHUNK_SIZE][CHUNK_SIZE]);
        for (int row = overlap; row < CHUNK_SIZE+overlap; row++) {
            for (int col = overlap; col < CHUNK_SIZE+overlap; col++) {
                cell = new ChunkCell();
                cell.value = generatedMap[row][col];
                chunk.setCell(cell, row-overlap, col-overlap, 2);
            }
        }
    }

    public ChunkCell getCell(int x, int y, int layer) {

        Chunk chunk = chunks.get((x/CHUNK_SIZE)+"_"+(y/CHUNK_SIZE));

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
            int[][] generatedMap = generator.generate(chunk.x, chunk.y, 64, 1);
            createGroundLayer(chunk, generatedMap, 1);
            createDecoration(chunk, generatedMap, 1);

        }

        return chunks.get((x/CHUNK_SIZE)+"_"+(y/CHUNK_SIZE)).getCell(x,y, layer);
    }

}
