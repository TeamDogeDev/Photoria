package de.dogedevs.photoria.model.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.generators.AbstractMapDecorator;
import de.dogedevs.photoria.generators.AbstractMapGenerator;
import de.dogedevs.photoria.generators.MapDecorator;
import de.dogedevs.photoria.generators.SimplexMapGenerator;
import de.dogedevs.photoria.rendering.tiles.TileMapper;

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

    public ChunkCell getCell(int x, int y, int layer) {
//        x += FixFloatSystem.offsetX;
//        y += FixFloatSystem.offsetY;
        Chunk chunk = chunks.get((x/CHUNK_SIZE)+"_"+(y/CHUNK_SIZE));

        if(chunk == null){

            purgeChunks();

            chunk = generateChunk(x, y);
            chunks.put(chunk.getHashCode(), chunk);
        }

        return chunk.getCell(x,y, layer);
    }

    private void purgeChunks() {
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
    }

    private Chunk generateChunk(int x, int y) {
        Chunk chunk = new Chunk();
        chunk.x = x/64;
        chunk.y = y/64;



        int[][] generatedMap = generator.generate(chunk.x, chunk.y, 64, 4);
        createGroundLayer(chunk, generatedMap, 4);
        createDecoration(chunk, generatedMap, 4);

        return chunk;
    }

    private int[][] createGroundLayer(Chunk chunk, int[][] generatedMap, int overlap) {
        ChunkCell cell;
        ChunkCell cell2;
        chunk.addLayer(1, new ChunkCell[CHUNK_SIZE][CHUNK_SIZE]);
        chunk.addLayer(3, new ChunkCell[CHUNK_SIZE][CHUNK_SIZE]);
        for (int row = overlap; row < CHUNK_SIZE+overlap; row++) {
            for (int col = overlap; col < CHUNK_SIZE+overlap; col++) {
                cell = new ChunkCell();
                cell2 = new ChunkCell();
                cell.value = generatedMap[row][col];
                cell2.value = generatedMap[row][col];
                chunk.setCell(cell, row-overlap, col-overlap, 1);
                if(chunk.getCell(row-overlap, col-overlap, 3) == null) {
                    chunk.setCell(cell2, row - overlap, col - overlap, 3);
                }
                if(generatedMap[row][col] == TileMapper.LAVA || generatedMap[row][col] == TileMapper.WATER) {
                    chunk.setCell(cell2, MathUtils.clamp(row - overlap - 1, 0, 63), col - overlap, 3);
                    chunk.setCell(cell2, MathUtils.clamp(row - overlap + 1, 0, 63), col - overlap, 3);
                    chunk.setCell(cell2, row - overlap, MathUtils.clamp(col - overlap - 1, 0, 63), 3);
                    chunk.setCell(cell2, row - overlap, MathUtils.clamp(col - overlap + 1, 0, 63), 3);

                    chunk.setCell(cell2, MathUtils.clamp(row - overlap - 1, 0, 63), MathUtils.clamp(col - overlap - 1, 0, 63), 3);
                    chunk.setCell(cell2, MathUtils.clamp(row - overlap + 1, 0, 63), MathUtils.clamp(col - overlap - 1, 0, 63), 3);
                    chunk.setCell(cell2, MathUtils.clamp(row - overlap - 1, 0, 63), MathUtils.clamp(col - overlap + 1, 0, 63), 3);
                    chunk.setCell(cell2, MathUtils.clamp(row - overlap + 1, 0, 63), MathUtils.clamp(col - overlap + 1, 0, 63), 3);
                }
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
                if(cell.value != TileMapper.VOID){
                    cell.collides = true;
                }
                chunk.setCell(cell, row-overlap, col-overlap, 2);
            }
        }
    }



}
