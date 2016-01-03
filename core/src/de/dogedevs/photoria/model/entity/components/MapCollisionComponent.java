package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 21.12.2015.
 */
public class MapCollisionComponent implements Component, Pool.Poolable {


    public int oldValue;
    public int value;

    public MapCollisionComponent() {
        oldValue = 0;
        value = 0;
    }

    public MapCollisionComponent(int oldValue, int value) {
        this.oldValue = oldValue;
        this.value = value;
    }

    @Override
    public void reset() {
        oldValue = 0;
        value = 0;
    }

}
