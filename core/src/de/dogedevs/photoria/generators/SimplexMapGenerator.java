package de.dogedevs.photoria.generators;

import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.OpenSimplexNoise;
import de.dogedevs.photoria.rendering.tiles.Tile;
import de.dogedevs.photoria.rendering.tiles.TileMapper;

import java.util.Map;
import java.util.Random;

import static de.dogedevs.photoria.rendering.tiles.TileMapper.*;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class SimplexMapGenerator extends AbstractMapGenerator {

    private Random random = new Random(31337);
    private OpenSimplexNoise osn = new OpenSimplexNoise(random.nextLong());
//    private int[][] chunk;
    private double local_x;
    private double local_y;
    private double eval;
    protected int realSize;
    // - 0.86
    // 0.86
    @Override
    public int[][] generate(int chunkX, int chunkY, int size, int overlap) {
        if (chunk == null) {
            chunk = new int[size+(2*overlap)][size+(2*overlap)];
        }

        realSize = size+(2*overlap);

        for (int row = 0; row < realSize; row++) {
            for (int col = 0; col < realSize; col++) {
                local_x = row + (chunkX * size);
                local_y = col + (chunkY * size);
                eval = osn.eval((local_x / size), (local_y / size));

                if (eval < -0.4f) {
                    chunk[row][col] = ((int) (eval / 0.12f));
                    int val = MathUtils.clamp(chunk[row][col], 1, 2);
                    if(val == 1) {
                        chunk[row][col] = WATER;
                    } else {
                        chunk[row][col] = GROUND;
                    }
                } else if (eval > 0.2f) {
                    chunk[row][col] = (int) (eval / 0.14f);
                    int val = MathUtils.clamp(chunk[row][col], 3, 4);
                    if(val == 3) {
                        chunk[row][col] = LAVA_STONE;
                    } else {
                        chunk[row][col] = LAVA;
                    }
                } else {
                    chunk[row][col] = GROUND;
                }
//                chunk[row][col] = (int) (eval*7);
            }
        }
//        MainGame.log("MIN: " + minNum + " MAX: " + maxNum);
        return chunk;
    }



}
