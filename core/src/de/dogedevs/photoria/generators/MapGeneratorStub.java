package de.dogedevs.photoria.generators;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class MapGeneratorStub implements AbstractMapGenerator {

    @Override
    public int[][] generate(int xPos, int yPos, int size) {
        int[][] mapPart = new int[size<<1][size<<1];
        return mapPart;
    }

}
