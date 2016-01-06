package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import de.dogedevs.photoria.MainGame;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.LifetimeComponent;

/**
 * Created by Furuha on 21.12.2015.
 */
public class LifetimeSystem extends EntitySystem {


    private ImmutableArray<Entity> entities;

    public LifetimeSystem() {
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(LifetimeComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        LifetimeComponent lifetimeComponent;

        for(Entity entity: entities){
            lifetimeComponent = ComponentMappers.lifetime.get(entity);
            if(lifetimeComponent == null){
                continue;
            }
            lifetimeComponent.currentTime +=deltaTime;
            if(lifetimeComponent.currentTime >= lifetimeComponent.maxTime){
                MainGame.log("remove "+entity);
                getEngine().removeEntity(entity);
            }
        }
    }
}
