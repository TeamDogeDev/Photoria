package de.dogedevs.photoria.model.entity.components.stats;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 27.12.2015.
 */
public class LifetimeComponent implements Component, Pool.Poolable {

    public float currentTime;
    public float maxTime;

    public LifetimeComponent() {
        currentTime = 0;
        maxTime = 10;
    }

    public LifetimeComponent(float maxTime) {
        this.maxTime = maxTime;
    }

    @Override
    public void reset() {
        currentTime = 0;
        maxTime = 10;
    }

}
