package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 27.12.2015.
 */
public class DecreaseZComponent implements Component, Pool.Poolable {

    public OnDecreaseListener listener;
    public float rate;

    public DecreaseZComponent() {
        rate = 0.01f;
    }

    @Override
    public void reset() {
        rate = 0.1f;
        listener = null;
    }

    public interface OnDecreaseListener {

        public void onDecrease(float newZValue, float x, float y, Entity entity);

    }
}
