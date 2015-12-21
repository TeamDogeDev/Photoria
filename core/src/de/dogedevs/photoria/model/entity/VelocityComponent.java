package de.dogedevs.photoria.model.entity;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 21.12.2015.
 */
public class VelocityComponent implements Component, Pool.Poolable {

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    int direction;
    float speed;

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
        speed = 0;
    }

}
