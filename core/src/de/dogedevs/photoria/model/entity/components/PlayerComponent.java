package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 27.12.2015.
 */
public class PlayerComponent implements Component, Pool.Poolable {

    public int energy = 75;
    public int maxEnergy = 100;

    @Override
    public void reset() {

    }
}
