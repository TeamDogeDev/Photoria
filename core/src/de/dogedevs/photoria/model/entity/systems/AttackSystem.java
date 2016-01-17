package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import de.dogedevs.photoria.content.weapons.AcidShooter;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.AttackComponent;
import de.dogedevs.photoria.model.entity.components.ParentComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.entity.components.TargetComponent;

import java.util.ArrayList;

/**
 * Created by Furuha on 21.12.2015.
 */
public class AttackSystem extends EntitySystem {


    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> allEntities;
    private ArrayList<Entity> resultList;

    public AttackSystem() {
        resultList = new ArrayList<>();
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(AttackComponent.class).get());
        allEntities = engine.getEntitiesFor(Family.all(PositionComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {

        for (int i = 0; i < entities.size(); ++i) {
            Entity self = entities.get(i);

            AttackComponent attack = ComponentMappers.attack.get(self);
            ParentComponent parent = ComponentMappers.parent.get(self);
            TargetComponent target = ComponentMappers.target.get(self);

            if(attack.weapon != null){
                Entity parentEntity = null;
                target.thrust = null;
                if(parent != null && parent.parent != null){
                    parentEntity = parent.parent;
                    PositionComponent parentPosition = ComponentMappers.position.get(parentEntity);
                    if(parentPosition != null){
                        attack.weapon.setBegin(new Vector2(parentPosition.x, parentPosition.y));
                    }
                    TargetComponent parentTarget = ComponentMappers.target.get(parentEntity);
                    if(parentTarget == null) {
                        getEngine().removeEntity(self);
                        continue;
                    } else {
                        target.x = parentTarget.x;
                        target.y = parentTarget.y;
                        target.thrust = parentTarget.thrust;
                        target.isShooting = parentTarget.isShooting;
                        attack.weapon.setAngle(new Vector2(target.x, target.y));
                    }
                }
                if(!target.isShooting && !(attack.weapon instanceof AcidShooter)){
                    continue;
                }
                resultList.clear();
                attack.weapon.checkCollision(allEntities, resultList);
                for(Entity targetEntity: resultList){
                    attack.listener.onEnemyHit(targetEntity, self, parentEntity);
                }
            }

        }
    }

}
