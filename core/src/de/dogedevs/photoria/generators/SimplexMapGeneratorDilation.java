package de.dogedevs.photoria.generators;

import de.dogedevs.photoria.rendering.tiles.TileMapper;

import static de.dogedevs.photoria.rendering.tiles.TileMapper.*;

/**
 * Created by elektropapst on 23.12.2015.
 */
public class SimplexMapGeneratorDilation extends SimplexMapGenerator {

    @Override
    public int[][] generate(int chunkX, int chunkY, int size, int overlap) {
        if (chunk == null) {
            chunk = new int[size+(2*overlap)][size+(2*overlap)];
        }

        realSize = size+(2*overlap);
        for (int row = 0; row < realSize; row++) {
            for (int col = 0; col < realSize; col++) {
                chunk[row][col] = GROUND;
            }
        }
        return chunk;
//        return dilate(chunk, 3);
    }

    public int[][] dilate(int[][] arr, int i) {
        int[][] copy = new int[arr.length][arr[0].length];
        for (int j = 0; j < copy.length; j++) {
            for (int k = 0; k < copy[j].length; k++) {
                copy[j][k] = WATER;
            }
        }
        return copy;
    }
}
