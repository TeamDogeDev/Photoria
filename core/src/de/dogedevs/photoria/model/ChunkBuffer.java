package de.dogedevs.photoria.model;

import com.badlogic.gdx.Gdx;
import de.dogedevs.photoria.generators.*;

import java.util.HashMap;

/**
 * Created by Furuha on 21.12.2015.
 */
public class ChunkBuffer {

    HashMap<String, Chunk> chunks; //String x+"_"+y
    private AbstractMapGenerator generator;
    private AbstractMapDecorator decorator;

    public ChunkBuffer(){
        chunks = new HashMap<>();
        generator = new SimplexMapGenerator();
        decorator = new MapDecorator();
    }

    private int[][] createGroundLayer(Chunk chunk, int size, AbstractMapGenerator generator) {
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
        return generatedMap;
    }

    private void createDecoration(Chunk chunk, int[][] generatedMap) {
        generatedMap = decorator.decorate(generatedMap);

        ChunkCell cell;
        chunk.addLayer(2, new ChunkCell[64][64]);
        for (int row = 0; row < 32 << 1; row++) {
            for (int col = 0; col < 32 << 1; col++) {
                cell = new ChunkCell();
                cell.value = generatedMap[row][col];
                chunk.setCell(cell, row, col, 2);
            }
        }
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

            int[][] groundLayer = createGroundLayer(chunk, 64, generator);
            createDecoration(chunk, groundLayer);


        }

        return chunks.get((x/64)+"_"+(y/64)).getCell(x,y, layer);
    }

}
