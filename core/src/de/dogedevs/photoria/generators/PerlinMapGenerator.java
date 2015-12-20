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

    @Override
    public int[][] generate(int chunkX, int chunkY, int size) {
        int[][] chunk = new int[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                double local_x = row + (chunkX*64);
                double local_y = col + (chunkY*64);
                double eval = osn.eval(local_x / 64, local_y / 64);
                eval = Math.abs(eval);
                chunk[row][col] = (int) (eval*13);
            }
        }

        return chunk;
    }

    private double noise(double x, double y) {
        return (Math.sin(((int)x>>1)*y - (Math.PI/2)) + Math.cos(((int)y>>1)*y))%2;
    }
}
