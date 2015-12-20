package de.dogedevs.photoria.generators;

import java.util.Random;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class ChunkDebugMapGenerator implements AbstractMapGenerator {

    @Override
    public int[][] generate(int xPos, int yPos, int size) {
        int[][] mapPart = new int[size][size];
        for (int row = 0; row < mapPart.length; row++) {
            for (int col = 0; col < mapPart[row].length; col++) {
                    if(row == 0 || col == 0 ){
                        mapPart[row][col] = 1;
                    } else{
                        mapPart[row][col] = 0;
                    }

            }
        }
        return mapPart;
    }

}
