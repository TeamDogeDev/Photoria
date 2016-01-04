package de.dogedevs.photoria.generators;

import com.badlogic.gdx.math.MathUtils;

import java.util.Random;

import static de.dogedevs.photoria.rendering.tiles.TileMapper.*;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class SimplexMapGenerator extends AbstractMapGenerator {

    private Random random;
    private OpenSimplexNoise osn;
//    private int[][] chunk;
    private double local_x;
    private double local_y;
    private double eval;
    protected int realSize;
    // - 0.86
    // 0.86


    public SimplexMapGenerator(long seed) {
        random = new Random(seed);
        osn = new OpenSimplexNoise(random.nextLong());
    }

    public SimplexMapGenerator() {
        this(31337);
    }

    @Override
    public int[][][] generate(int chunkX, int chunkY, int size, int overlap) {
        if (chunk == null) {
            chunk = new int[2][size+(2*overlap)][size+(2*overlap)];
        }

        realSize = size+(2*overlap);

        for (int row = 0; row < realSize; row++) {
            for (int col = 0; col < realSize; col++) {
                local_x = row + (chunkX * size);
                local_y = col + (chunkY * size);
                eval = osn.eval((local_x / size), (local_y / size));

                if (eval < -0.4f) {
                    chunk[TILELAYER][row][col] = ((int) (eval / 0.12f));
                    int val = MathUtils.clamp(chunk[TILELAYER][row][col], 1, 2);
                    if(val == 1) {
                        chunk[TILELAYER][row][col] = WATER;
                    } else {
                        chunk[TILELAYER][row][col] = GROUND;
                    }
                } else if (eval > 0.2f) {
                    chunk[TILELAYER][row][col] = (int) (eval / 0.14f);
                    int val = MathUtils.clamp(chunk[TILELAYER][row][col], 3, 4);
                    if(val == 3) {
                        chunk[TILELAYER][row][col] = LAVA_STONE;
                    } else {
                        chunk[TILELAYER][row][col] = LAVA;
                    }
                } else {
                    chunk[TILELAYER][row][col] = GROUND;
                }
//                chunk[row][col] = (int) (eval*7);
            }
        }
//        MainGame.log("MIN: " + minNum + " MAX: " + maxNum);
        return chunk;
    }



}
