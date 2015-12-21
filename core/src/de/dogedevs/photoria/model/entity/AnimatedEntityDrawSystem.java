package de.dogedevs.photoria.model.entity;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Furuha on 21.12.2015.
 */
public class AnimatedEntityDrawSystem extends EntitySystem {


    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private ImmutableArray<Entity> entities;

    public AnimatedEntityDrawSystem(OrthographicCamera camera) {
        this.camera = camera;
        batch = new SpriteBatch();
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, AnimationComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
//        MainGame.log("update: "+entities.size());
        PositionComponent position;
        AnimationComponent visual;
        VelocityComponent velocity;

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);

            position = ComponentMappers.position.get(e);
            visual = ComponentMappers.animation.get(e);
            velocity = ComponentMappers.velocity.get(e);
            visual.stateTime += deltaTime;

            if(velocity != null){
                switch (velocity.direction){
                    case VelocityComponent.DOWN:
                        batch.draw(visual.downAnimation.getKeyFrame(visual.stateTime, true), position.x, position.y);
                        break;
                    case VelocityComponent.UP:
                        batch.draw(visual.upAnimation.getKeyFrame(visual.stateTime, true), position.x, position.y);
                        break;
                    case VelocityComponent.LEFT:
                        batch.draw(visual.leftAnimation.getKeyFrame(visual.stateTime, true), position.x, position.y);
                        break;
                    case VelocityComponent.RIGHT:
                        batch.draw(visual.rightAnimation.getKeyFrame(visual.stateTime, true), position.x, position.y);
                        break;
                }
            } else {
                batch.draw(visual.idleAnimation.getKeyFrame(visual.stateTime, true), position.x, position.y);
            }
        }

        batch.end();
    }
}
