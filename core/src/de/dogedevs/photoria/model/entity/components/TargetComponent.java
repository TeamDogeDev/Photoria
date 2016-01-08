package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 08.01.2016.
 */
public class TargetComponent implements Component, Pool.Poolable {

    public float x;
    public float y;

    public boolean on;

    @Override
    public void reset() {
        on = false;
        x = 0;
        y = 0;
    }
}
