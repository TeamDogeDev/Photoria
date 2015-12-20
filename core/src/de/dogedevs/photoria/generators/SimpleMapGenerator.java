package de.dogedevs.photoria.generators;

import java.util.Random;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class SimpleMapGenerator implements AbstractMapGenerator {

    private static final double NOISE_PROBABILITY = 0.5;
    private static final Random random = new Random(31337);

    @Override
    public int[][] generate(int xPos, int yPos, int size) {
        int[][] mapPart = new int[size << 1][size << 1];

        for (int row = 0; row < mapPart.length; row++) {
            for (int col = 0; col < mapPart[row].length; col++) {
                if (random.nextDouble() <= NOISE_PROBABILITY) {
                    mapPart[row][col] = 1;
                }
            }
        }
        return mapPart;
    }

}
