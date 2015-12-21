package de.dogedevs.photoria.generators;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class MapGeneratorStub extends AbstractMapGenerator {

    @Override
    public int[][] generate(int xPos, int yPos, int size) {
        if(chunk == null) {
            chunk = new int[size][size];
        }
        return chunk;
    }

}
