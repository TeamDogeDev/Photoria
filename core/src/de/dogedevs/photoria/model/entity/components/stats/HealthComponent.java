package de.dogedevs.photoria.model.entity.components.stats;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 27.12.2015.
 */
public class HealthComponent implements Component, Pool.Poolable {

    public float health = 100;
    public float maxHealth = 100; // Template
    public float maxHealthUse = 100; // use this !!!

    public float regHealthSec = 10; // Template
    public float regHealthSecUse = 10; // use this !!!

    public float immuneTime = 0;
    public float maxImmuneTime = 0;


    @Override
    public void reset() {
        health = 100;
        maxHealth = 100;
        maxHealthUse = 100;
        immuneTime = 0;
        maxImmuneTime = 0;
    }
}
