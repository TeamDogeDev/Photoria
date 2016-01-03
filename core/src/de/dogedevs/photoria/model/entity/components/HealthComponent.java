package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 27.12.2015.
 */
public class HealthComponent implements Component, Pool.Poolable {

    public float health;
    public float maxHealth;

    public HealthComponent() {
        health = 100;
        maxHealth = 100;
    }

    public HealthComponent(float health, float maxHealth) {
        this.health = health;
        this.maxHealth = maxHealth;
    }

    @Override
    public void reset() {
        health = 100;
        maxHealth = 100;
    }
}
