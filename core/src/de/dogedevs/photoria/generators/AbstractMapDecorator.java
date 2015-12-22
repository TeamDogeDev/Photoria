package de.dogedevs.photoria.generators;

/**
 * Created by elektropapst on 20.12.2015.
 */
public abstract class AbstractMapDecorator {

    protected int[][] chunk;

//    public abstract int[][] decorate(Chunk[][] chunks);
    public abstract int[][] decorate(int[][] ground);

}
