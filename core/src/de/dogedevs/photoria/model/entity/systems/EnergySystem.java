package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.stats.EnergyComponent;

/**
 * Created by Furuha on 21.12.2015.
 */
public class EnergySystem extends EntitySystem {


    private ImmutableArray<Entity> entities;

    public EnergySystem() {
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(EnergyComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        EnergyComponent energyComponent;
        for(Entity entity: entities){
            energyComponent = ComponentMappers.energy.get(entity);
            energyComponent.energy += 0.1f;
            energyComponent.energy  = MathUtils.clamp(energyComponent.energy, 0, energyComponent.maxEnergyUse);
        }
    }
}
