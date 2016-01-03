package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 27.12.2015.
 */
public class EnergyComponent implements Component, Pool.Poolable {

    public float energy = 75;
    public float maxEnergy = 100;

    public EnergyComponent() {
        energy = 100;
        maxEnergy = 100;
    }

    public EnergyComponent(float energy, float maxEnergy) {
        this.energy = energy;
        this.maxEnergy = maxEnergy;
    }

    @Override
    public void reset() {
        energy = 100;
        maxEnergy = 100;
    }
}
