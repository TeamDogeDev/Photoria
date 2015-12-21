package de.dogedevs.photoria.generators;

import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.OpenSimplexNoise;

import java.util.Map;
import java.util.Random;

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

    // - 0.86
    // 0.86
    @Override
    public int[][] generate(int chunkX, int chunkY, int size) {
        if (chunk == null) {
            chunk = new int[size][size];
        }

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                local_x = row + (chunkX * size);
                local_y = col + (chunkY * size);
                eval = osn.eval(local_x / size, local_y / size);
//                if (eval < minNum) {
//                    minNum = eval;
//                }
//
//                if (eval > maxNum) {
//                    maxNum = eval;
//                }
//                eval = eval < 0 ? 0 : eval;
                if (eval < -0.4f) {
                    chunk[row][col] = ((int) (eval / 0.12f));
                    chunk[row][col] = MathUtils.clamp(chunk[row][col], -6, -1);
                } else if (eval > 0.2f) {
                    chunk[row][col] = (int) (eval / 0.14f);
                    chunk[row][col] = MathUtils.clamp(chunk[row][col], 1, 6);
                } else {
                    chunk[row][col] = 0;
                }
//                chunk[row][col] = (int) (eval*7);
            }
        }
//        MainGame.log("MIN: " + minNum + " MAX: " + maxNum);
        return chunk;
    }



}
