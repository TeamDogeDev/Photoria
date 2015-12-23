package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import de.dogedevs.photoria.model.entity.components.AnimationComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.entity.components.VelocityComponent;
import de.dogedevs.photoria.model.map.OffsetHolder;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Furuha on 21.12.2015.
 */
public class AnimatedEntityDrawSystem extends EntitySystem {


    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final YComparator comparator;
    private ImmutableArray<Entity> entities;
    private Array<Entity> sortedEntities;

    public AnimatedEntityDrawSystem(OrthographicCamera camera) {
        this.camera = camera;
        batch = new SpriteBatch();
        sortedEntities = new Array<Entity>(false, 16);
        comparator = new YComparator();
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class).one(AnimationComponent.class).get());
//        sortedEntities.clear();
//        if (entities.size() > 0) {
//            for (int i = 0; i < entities.size(); ++i) {
//                sortedEntities.add(entities.get(i));
//            }
//        }
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    private class YComparator implements Comparator<Entity> {
        @Override
        public int compare(Entity e1, Entity e2) {
            return (int)Math.signum(ComponentMappers.position.get(e1).y - ComponentMappers.position.get(e2).y);
        }
    }


    @Override
    public void update (float deltaTime) {
//        MainGame.log("update: "+entities.size());
        PositionComponent position;
        AnimationComponent visual;
        VelocityComponent velocity;

//        sortedEntities.sort(comparator);

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
                        batch.draw(visual.downAnimation.getKeyFrame(visual.stateTime, true),  OffsetHolder.offsetX+position.x, OffsetHolder.offsetX+position.y);
                        break;
                    case VelocityComponent.UP:
                        batch.draw(visual.upAnimation.getKeyFrame(visual.stateTime, true), OffsetHolder.offsetX+position.x, OffsetHolder.offsetX+position.y);
                        break;
                    case VelocityComponent.LEFT:
                        batch.draw(visual.leftAnimation.getKeyFrame(visual.stateTime, true), OffsetHolder.offsetX+position.x, OffsetHolder.offsetX+position.y);
                        break;
                    case VelocityComponent.RIGHT:
                        batch.draw(visual.rightAnimation.getKeyFrame(visual.stateTime, true), OffsetHolder.offsetX+position.x, OffsetHolder.offsetX+position.y);
                        break;
                }
            } else {
                batch.draw(visual.idleAnimation.getKeyFrame(visual.stateTime, true), OffsetHolder.offsetX+position.x, OffsetHolder.offsetX+position.y);
            }
        }

        batch.end();
    }
}
