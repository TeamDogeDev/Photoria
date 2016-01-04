package de.dogedevs.photoria.generators;

/**
 * Created by elektropapst on 20.12.2015.
 */
public abstract class AbstractMapGenerator {

    public static int BIOMLAYER = 1;
    public static int TILELAYER = 0;

    protected int[][][] chunk;

    // [0 = tile; 1 = biom][x][y]
    public abstract int[][][] generate(int xPos, int yPos, int size, int overlap);
}
