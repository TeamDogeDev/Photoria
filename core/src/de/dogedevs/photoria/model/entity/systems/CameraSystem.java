package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.model.entity.components.AnimationComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.entity.components.SpriteComponent;

/**
 * Created by Furuha on 21.12.2015.
 */
public class CameraSystem extends EntitySystem {


    private final OrthographicCamera camera;
    private ImmutableArray<Entity> entities;
    private int entityNumber;
    private float deltaSum;

    public CameraSystem(OrthographicCamera camera) {
        this.camera = camera;
        entityNumber = 0;
        deltaSum = 0;
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class).one(AnimationComponent.class, SpriteComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
//        MainGame.log("update: "+entities.size());
        PositionComponent position;

        if(entities.size() == 0){
            return;
        }
        deltaSum += deltaTime;
        if(deltaSum > 3){
            deltaSum = 0;
            entityNumber = MathUtils.random(entities.size()-1);
        }
        Entity e = entities.get(entityNumber);
        position = ComponentMappers.position.get(e);
        camera.position.y = position.y+16;
        camera.position.x = position.x+16;
    }
}
