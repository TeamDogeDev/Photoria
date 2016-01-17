package de.dogedevs.photoria.model.entity.components.stats;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 27.12.2015.
 */
public class EnergyComponent implements Component, Pool.Poolable {

    public float energy = 100;
    public float maxEnergy = 100; // Template
    public float maxEnergyUse = 100; // Use this !!!
    public float regEnergySec = 10; // Template
    public float regEnergySecUse = 10; // Use this !!!

    @Override
    public void reset() {
        energy = 100;
        maxEnergy = 100;
        maxEnergyUse = 100;
        regEnergySec = 10;
        regEnergySecUse = 10;
    }
}
