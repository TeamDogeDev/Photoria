package de.dogedevs.photoria.generators;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class MapGeneratorStub extends AbstractMapGenerator {

    @Override
    public int[][][] generate(int xPos, int yPos, int size, int overlap) {
        if(chunk == null) {
            chunk = new int[2][size+(2*overlap)][size+(2*overlap)];
        }
        return chunk;
    }


}
