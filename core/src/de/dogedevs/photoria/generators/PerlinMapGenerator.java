package de.dogedevs.photoria.generators;

import de.dogedevs.photoria.MainGame;
import de.dogedevs.photoria.OpenSimplexNoise;

import java.util.Random;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class PerlinMapGenerator implements AbstractMapGenerator {

    private Random random = new Random(31337);
    private OpenSimplexNoise osn = new OpenSimplexNoise();
    private int[][] chunk;
    private double local_x;
    private double local_y;
    private double eval;

    @Override
    public int[][] generate(int chunkX, int chunkY, int size) {
        if(chunk == null) {
            chunk = new int[size][size];
        }
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                local_x = row + (chunkX*size);
                local_y = col + (chunkY*size);
                eval = osn.eval(local_x / size, local_y / size);
                eval = Math.abs(eval);
                chunk[row][col] = (int) (eval*13);
            }
        }

        return chunk;
    }

}
