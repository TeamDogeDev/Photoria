package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.dogedevs.photoria.model.entity.components.AnimationComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.entity.components.SpriteComponent;
import de.dogedevs.photoria.model.entity.components.VelocityComponent;

/**
 * Created by Furuha on 21.12.2015.
 */
public class EntityDrawSystem extends EntitySystem {


    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private ImmutableArray<Entity> entities;

    public EntityDrawSystem(OrthographicCamera camera) {
        this.camera = camera;
        batch = new SpriteBatch();
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class).one(SpriteComponent.class, AnimationComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {

        PositionComponent position;
        SpriteComponent visual;
        AnimationComponent animation;
        VelocityComponent velocity;
//        MainGame.log("update: " + entities.size());
//        sortedEntities.sort(comparator);

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);

            position = ComponentMappers.position.get(e);
            animation = ComponentMappers.animation.get(e);
            velocity = ComponentMappers.velocity.get(e);
            visual = ComponentMappers.sprite.get(e);

            if(animation != null) {
                animation.stateTime += deltaTime;

                if (velocity != null) {
                    if(velocity.speed == 0){
                        batch.draw(animation.idleAnimation.getKeyFrame(animation.stateTime, true), position.x, position.y);
                    } else {
                        switch (velocity.direction) {
                            case VelocityComponent.DOWN:
                                batch.draw(animation.downAnimation.getKeyFrame(animation.stateTime, true), position.x, position.y);
                                break;
                            case VelocityComponent.UP:
                                batch.draw(animation.upAnimation.getKeyFrame(animation.stateTime, true), position.x, position.y);
                                break;
                            case VelocityComponent.LEFT:
                                batch.draw(animation.leftAnimation.getKeyFrame(animation.stateTime, true), position.x, position.y);
                                break;
                            case VelocityComponent.RIGHT:
                                batch.draw(animation.rightAnimation.getKeyFrame(animation.stateTime, true), position.x, position.y);
                                break;
                        }
                    }
                } else {
                    batch.draw(animation.idleAnimation.getKeyFrame(animation.stateTime, true), position.x, position.y);
                }
            } else {
                batch.draw(visual.region, position.x, position.y);
            }
        }

        batch.end();
    }
}
