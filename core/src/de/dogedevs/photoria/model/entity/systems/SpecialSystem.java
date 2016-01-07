package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.AttackComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;

/**
 * Created by Furuha on 21.12.2015.
 */
public class SpecialSystem extends EntitySystem {


    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> allEntities;

    public SpecialSystem() {

    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class).one(AttackComponent.class).get());
        allEntities = engine.getEntitiesFor(Family.all(PositionComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {

        for (int i = 0; i < entities.size(); ++i) {
            Entity self = entities.get(i);

            //Check special attack hits
            AttackComponent attack = ComponentMappers.attack.get(self);
            if(attack != null){
                if(attack.laser != null){
                    Vector2 end = attack.laser.getEndPoint();
                    for(Entity entity: allEntities){
                        if(entity  == self){
                            continue;
                        }
                        PositionComponent pc = ComponentMappers.position.get(entity);
                        float dist = Intersector.distanceSegmentPoint(attack.laser.begin.x, attack.laser.begin.y, end.x, end.y, pc.x, pc.y);

                        if(dist < 24){
                            if(attack.listener != null){
                                attack.listener.onCollision(entity, self);
                            }
                        }
                    }
                }
            }



        }
    }

}
