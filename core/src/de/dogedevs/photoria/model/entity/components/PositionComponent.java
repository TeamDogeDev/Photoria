package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 21.12.2015.
 */
public class PositionComponent implements Component, Pool.Poolable {

    public float x;
    public float y;
    public float z;

    public OnBiomeChangeListener listener;

    public PositionComponent() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public PositionComponent(float x, float y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    @Override
    public void reset() {
        x = 0;
        y = 0;
        z = 0;
    }

    public interface OnBiomeChangeListener {
        void onBiomeChange(int newBiome, int oldBiome);
    }
}
