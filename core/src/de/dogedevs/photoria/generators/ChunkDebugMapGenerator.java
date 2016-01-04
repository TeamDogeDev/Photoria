package de.dogedevs.photoria.generators;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class ChunkDebugMapGenerator extends AbstractMapGenerator {

    @Override
    public int[][][] generate(int xPos, int yPos, int size, int overlap) {
        if(chunk == null) {
            chunk = new int[2][size][size];
        }
        for (int row = 0; row < chunk.length; row++) {
            for (int col = 0; col < chunk[row].length; col++) {
                    if(row == size-1 || col == 0 ){
                        chunk[TILELAYER][row][col] = 1;
                    } else{
                        chunk[TILELAYER][row][col] = 0;
                    }

            }
        }
        return chunk;
    }

}
