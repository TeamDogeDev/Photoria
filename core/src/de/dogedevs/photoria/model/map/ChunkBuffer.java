package de.dogedevs.photoria.model.map;

import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.generators.AbstractMapDecorator;
import de.dogedevs.photoria.generators.AbstractMapGenerator;
import de.dogedevs.photoria.generators.MapDecorator;
import de.dogedevs.photoria.generators.SimplexMapGenerator;
import de.dogedevs.photoria.model.entity.EntityLoader;
import de.dogedevs.photoria.rendering.tiles.TileCollisionMapper;
import de.dogedevs.photoria.rendering.tiles.TileMapper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Furuha on 21.12.2015.
 */
public class ChunkBuffer {

    public static final int COLLISION = 0;
    public static final int GROUND = 1;
    public static final int FLUID = 3;
    public static final int DECO1 = 2;

    HashMap<String, Chunk> chunks; //String x+"_"+y
    private AbstractMapGenerator generator;
    private AbstractMapDecorator decorator;
    private EntityLoader entityLoader;

    public static final long SEED = 31337;
    public static final int CHUNK_SIZE = 64;

    public ChunkBuffer(){
        chunks = new HashMap<>();
        generator = new SimplexMapGenerator(SEED);
        decorator = new MapDecorator();
        entityLoader = new EntityLoader();
    }
    int count;
    public ChunkCell getCell(int x, int y, int layer) {
//        x += FixFloatSystem.offsetX;
//        y += FixFloatSystem.offsetY;
        Chunk chunk = chunks.get((x/CHUNK_SIZE)+"_"+(y/CHUNK_SIZE));

        if(chunk == null){
            chunk = generateChunk(x, y);
            chunks.put(chunk.getHashCode(), chunk);

            entityLoader.createChunkEntities(x/64, y/64, SEED, this);
        }

        return chunk.getCell(x,y, layer);
    }

    public void purgeChunks() {
        ArrayList<String> removeKeys = new ArrayList<>();
        for(String key: chunks.keySet()){
            if(System.currentTimeMillis()-chunks.get(key).lastRead > 1000){
//                entityLoader.onChunkPurge(chunks.get(key));
                removeKeys.add(key);
            }
        }
        for(String key: removeKeys){
            chunks.remove(key);
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
        ChunkCell cell, cellFluid, cellCollision;
        chunk.addLayer(GROUND, new ChunkCell[CHUNK_SIZE][CHUNK_SIZE]);
        chunk.addLayer(COLLISION, new ChunkCell[CHUNK_SIZE][CHUNK_SIZE]);
        chunk.addLayer(FLUID, new ChunkCell[CHUNK_SIZE][CHUNK_SIZE]);
        for (int row = overlap; row < CHUNK_SIZE+overlap; row++) {
            for (int col = overlap; col < CHUNK_SIZE+overlap; col++) {

                //Normal Ground
                cell = new ChunkCell();
                cell.value = generatedMap[row][col];
                chunk.setCell(cell, row-overlap, col-overlap, GROUND);

                //Fluid layer
                cellFluid = new ChunkCell();
                cellFluid.value = generatedMap[row][col];
                if(chunk.getCell(row-overlap, col-overlap, FLUID) == null) {
                    chunk.setCell(cellFluid, row - overlap, col - overlap, FLUID); //Not rendered, only to avoid NullPointer
                }
                if(generatedMap[row][col] == TileMapper.LAVA || generatedMap[row][col] == TileMapper.WATER) {
                    chunk.setCell(cellFluid, MathUtils.clamp(row - overlap - 1, 0, 63), col - overlap, FLUID);
                    chunk.setCell(cellFluid, MathUtils.clamp(row - overlap + 1, 0, 63), col - overlap, FLUID);
                    chunk.setCell(cellFluid, row - overlap, MathUtils.clamp(col - overlap - 1, 0, 63), FLUID);
                    chunk.setCell(cellFluid, row - overlap, MathUtils.clamp(col - overlap + 1, 0, 63), FLUID);

                    chunk.setCell(cellFluid, MathUtils.clamp(row - overlap - 1, 0, 63), MathUtils.clamp(col - overlap - 1, 0, 63), FLUID);
                    chunk.setCell(cellFluid, MathUtils.clamp(row - overlap + 1, 0, 63), MathUtils.clamp(col - overlap - 1, 0, 63), FLUID);
                    chunk.setCell(cellFluid, MathUtils.clamp(row - overlap - 1, 0, 63), MathUtils.clamp(col - overlap + 1, 0, 63), FLUID);
                    chunk.setCell(cellFluid, MathUtils.clamp(row - overlap + 1, 0, 63), MathUtils.clamp(col - overlap + 1, 0, 63), FLUID);
                }

                //Collision layer
                cellCollision = new ChunkCell();
                if(generatedMap[row][col] == TileMapper.LAVA) {
                    cellCollision.value = TileCollisionMapper.LAVA;
                    chunk.setCell(cellCollision,  row-overlap, col - overlap, COLLISION);
                } else if(generatedMap[row][col] == TileMapper.WATER) {
                    cellCollision.value = TileCollisionMapper.WATER;
                    chunk.setCell(cellCollision,  row-overlap, col - overlap, COLLISION);
                } else {
                    cellCollision.value = TileCollisionMapper.VOID;
                    chunk.setCell(cellCollision,  row-overlap, col - overlap, COLLISION);
                }
            }
        }

        return generatedMap;
    }

    private void createDecoration(Chunk chunk, int[][] generatedMap, int overlap) {
        generatedMap = decorator.decorate(generatedMap);

        ChunkCell cell, cellCollision;
        chunk.addLayer(2, new ChunkCell[CHUNK_SIZE][CHUNK_SIZE]);
        for (int row = overlap; row < CHUNK_SIZE+overlap; row++) {
            for (int col = overlap; col < CHUNK_SIZE+overlap; col++) {
                cell = new ChunkCell();
                cell.value = generatedMap[row][col];
                //Collision layer
                if(cell.value != TileMapper.VOID){
                    cellCollision = new ChunkCell();
                    if(generatedMap[row][col] > TileMapper.WATER && generatedMap[row][col] <= TileMapper.WATER_BOTTOM_RIGHT_INNER) {
                        cellCollision.value = TileCollisionMapper.WATER_BORDER;
                        chunk.setCell(cellCollision, row-overlap, col-overlap, COLLISION);

                    } else if(generatedMap[row][col] > TileMapper.LAVA && generatedMap[row][col] <= TileMapper.LAVA_BOTTOM_RIGHT_INNER) {
                        cellCollision.value = TileCollisionMapper.LAVA_BORDER;
                        chunk.setCell(cellCollision, row-overlap, col-overlap, COLLISION);

                    } else if(generatedMap[row][col] > TileMapper.LAVA_STONE && generatedMap[row][col] <= TileMapper.LAVA_STONE_MIDDLE_RIGHT_WALL_STRAIGHT) {
                        cellCollision.value = TileCollisionMapper.LAVA_STONE_BORDER;
                        chunk.setCell(cellCollision, row-overlap, col-overlap, COLLISION);

                    } else {
                        cellCollision.value = TileCollisionMapper.VOID;
                        chunk.setCell(cellCollision, row-overlap, col-overlap, COLLISION);
                    }
                }
                chunk.setCell(cell, row-overlap, col-overlap, DECO1);
            }
        }
    }


    public ChunkCell getCellLazy(int x, int y, int layer) {
//        x += FixFloatSystem.offsetX;
//        y += FixFloatSystem.offsetY;
        Chunk chunk = chunks.get((x/CHUNK_SIZE)+"_"+(y/CHUNK_SIZE));

        if(chunk == null){
            return null;
        }

        return chunk.getCellLazy(x, y, layer);
    }

    public int getChunkCount() {
        return chunks.size();
    }
}
