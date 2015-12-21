package de.dogedevs.photoria.generators;

/**
 * Created by elektropapst on 20.12.2015.
 */
public abstract class AbstractMapGenerator {

    protected int[][] chunk;

    public abstract int[][] generate(int xPos, int yPos, int size, int overlap);
}
