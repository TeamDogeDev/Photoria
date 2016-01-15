package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.DecreaseZComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;

/**
 * Created by Furuha on 21.12.2015.
 */
public class DecreaseZSystem extends EntitySystem {


    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(DecreaseZComponent.class, PositionComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {

        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            DecreaseZComponent z = ComponentMappers.z.get(entity);
            PositionComponent pos = ComponentMappers.position.get(entity);
            pos.z -= z.rate;
            if(z.listener != null){
                z.listener.onDecrease(pos.z, pos.x, pos.y, entity);
            }

        }
    }

}
