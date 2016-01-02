package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.AiComponent;

/**
 * Created by Furuha on 21.12.2015.
 */
public class AiSystem extends EntitySystem  {

    private ImmutableArray<Entity> entities;

    public AiSystem() {
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(AiComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        AiComponent ai;

        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);

            ai = ComponentMappers.ai.get(e);
            ai.ai.tick(deltaTime, e);
        }
    }

}
