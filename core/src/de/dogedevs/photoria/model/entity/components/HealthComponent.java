package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 27.12.2015.
 */
public class HealthComponent implements Component, Pool.Poolable {

    public int health;
    public int maxHealth;

    public HealthComponent() {
    }

    public HealthComponent(int health, int maxHealth) {
        this.health = health;
        this.maxHealth = maxHealth;
    }

    @Override
    public void reset() {
        health = 0;
        maxHealth = 0;
    }
}
