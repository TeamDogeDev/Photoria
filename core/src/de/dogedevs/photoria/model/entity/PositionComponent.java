package de.dogedevs.photoria.model.entity;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 21.12.2015.
 */
public class PositionComponent implements Component, Pool.Poolable {

    float x;
    float y;

    public PositionComponent() {
        this.x = 0;
        this.y = 0;
    }

    public PositionComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void reset() {
        x = 0;
        y = 0;
    }

}
