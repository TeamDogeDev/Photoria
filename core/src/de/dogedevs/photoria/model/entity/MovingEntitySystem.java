package de.dogedevs.photoria.model.entity;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Furuha on 21.12.2015.
 */
public class MovingEntitySystem extends EntitySystem {


    private ImmutableArray<Entity> entities;

    public MovingEntitySystem() {
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
//        MainGame.log("update: "+entities.size());
        PositionComponent position;
        VelocityComponent velocity;


        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);

            position = ComponentMappers.position.get(e);
            velocity = ComponentMappers.velocity.get(e);

            if(MathUtils.randomBoolean(0.001f)){
                velocity.direction = MathUtils.random(0, 4);
            }
            switch (velocity.direction){
                case VelocityComponent.DOWN:
                    position.y -= velocity.speed * deltaTime;
                    break;
                case VelocityComponent.UP:
                    position.y += velocity.speed * deltaTime;
                    break;
                case VelocityComponent.LEFT:
                    position.x -= velocity.speed * deltaTime;
                    break;
                case VelocityComponent.RIGHT:
                    position.x += velocity.speed * deltaTime;
                    break;
            }

        }
    }
}
