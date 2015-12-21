package de.dogedevs.photoria.generators;

import com.badlogic.gdx.utils.Queue;

/**
 * Created by elektropapst on 20.12.2015.
 */
public abstract class AbstractMapGenerator {

//    protected Map<String, int[][]>
    
    protected int[][] chunk;

    public abstract int[][] generate(int xPos, int yPos, int size);

}
