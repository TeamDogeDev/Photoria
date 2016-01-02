package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 21.12.2015.
 */
public class VelocityComponent implements Component, Pool.Poolable {

    public static final int NORTH = 0;
    public static final int NORTH_WEST = 4;
    public static final int NORTH_EAST = 5;

    public static final int SOUTH = 1;
    public static final int SOUTH_WEST = 6;
    public static final int SOUTH_EAST = 7;

    public static final int WEST = 2;
    public static final int EAST = 3;

    public int direction;
    public float blockedDelta;
    public float speed;

    public VelocityComponent() {
        this.direction = 0;
        this.speed = 0;
    }

    public VelocityComponent(int direction, float speed) {
        this.direction = direction;
        this.speed = speed;
    }

    @Override
    public void reset() {
        direction = 0;
        blockedDelta = 0;
        speed = 0;
    }

}
