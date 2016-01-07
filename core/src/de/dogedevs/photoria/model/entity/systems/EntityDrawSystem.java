package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.*;
import de.dogedevs.photoria.utils.assets.AssetLoader;
import de.dogedevs.photoria.utils.assets.enums.Textures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Furuha on 21.12.2015.
 */
public class EntityDrawSystem extends EntitySystem implements EntityListener {


    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private ImmutableArray<Entity> entities;
    private ArrayList<Entity> sortedEntities;
    private YComparator comparator = new YComparator();

    public EntityDrawSystem(OrthographicCamera camera) {
        this.camera = camera;
        batch = new SpriteBatch();
        sortedEntities = new ArrayList<>();
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class).one(SpriteComponent.class, AnimationComponent.class).get());
        engine.addEntityListener(Family.all(PositionComponent.class).one(SpriteComponent.class, AnimationComponent.class).get(), this);
        for(Entity e: entities){
            sortedEntities.add(e);
        }
        Collections.sort(sortedEntities, comparator);
    }

    @Override
    public void removedFromEngine (Engine engine) {
        engine.removeEntityListener(this);
    }

    private class YComparator implements Comparator<Entity> {
        @Override
        public int compare(Entity e1, Entity e2) {
            return (int)Math.signum(ComponentMappers.position.get(e2).y - ComponentMappers.position.get(e1).y);
        }
    }

    @Override
    public void entityAdded (Entity entity) {
        sortedEntities.add(entity);
    }

    @Override
    public void entityRemoved (Entity entity) {
        sortedEntities.remove(entity);
    }

    @Override
    public void update (float deltaTime) {

        PositionComponent position;
        SpriteComponent visual;
        AnimationComponent animation;
        VelocityComponent velocity;
        HealthComponent health;
//        MainGame.log("update: " + entities.size());
//        Collections.sort(sortedEntities, comparator);

        Collections.sort(sortedEntities, comparator);

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        for (int i = 0; i < sortedEntities.size(); ++i) {
            Entity e = sortedEntities.get(i);

            position = ComponentMappers.position.get(e);
            animation = ComponentMappers.animation.get(e);
            velocity = ComponentMappers.velocity.get(e);
            visual = ComponentMappers.sprite.get(e);
            health = ComponentMappers.health.get(e);

            if(health != null) {
                if(health.health < health.maxHealth) {
                    batch.draw(AssetLoader.getTexture(Textures.BULLET), position.x, position.y);
                }
            }
            
            if(animation != null) {
                animation.stateTime += deltaTime;
                float yOffset = 0;
                if(ComponentMappers.mapCollision.get(e) == null){
                    yOffset = animation.idleAnimation.getKeyFrames()[0].getRegionWidth()/2;
                }
                if (velocity != null) {
                    if(velocity.speed == 0){
                        batch.draw(animation.idleAnimation.getKeyFrame(animation.stateTime, true), position.x-yOffset, position.y);
                    } else {
                        switch (velocity.direction) {
                            case VelocityComponent.SOUTH:
                                batch.draw(animation.downAnimation.getKeyFrame(animation.stateTime, true), position.x-yOffset, position.y+position.z);
                                break;
                            case VelocityComponent.NORTH:
                                batch.draw(animation.upAnimation.getKeyFrame(animation.stateTime, true), position.x-yOffset, position.y+position.z);
                                break;
                            case VelocityComponent.WEST:
                                batch.draw(animation.leftAnimation.getKeyFrame(animation.stateTime, true), position.x-yOffset, position.y+position.z);
                                break;
                            case VelocityComponent.EAST:
                                batch.draw(animation.rightAnimation.getKeyFrame(animation.stateTime, true), position.x-yOffset, position.y+position.z);
                                break;
                            case VelocityComponent.SOUTH_WEST:
                                batch.draw(animation.leftAnimation.getKeyFrame(animation.stateTime, true), position.x-yOffset, position.y+position.z);
                                break;
                            case VelocityComponent.SOUTH_EAST:
                                batch.draw(animation.rightAnimation.getKeyFrame(animation.stateTime, true), position.x-yOffset, position.y+position.z);
                                break;
                            case VelocityComponent.NORTH_WEST:
                                batch.draw(animation.leftAnimation.getKeyFrame(animation.stateTime, true), position.x-yOffset, position.y+position.z);
                                break;
                            case VelocityComponent.NORTH_EAST:
                                batch.draw(animation.rightAnimation.getKeyFrame(animation.stateTime, true), position.x-yOffset, position.y+position.z);
                                break;
                        }
                    }
                } else {
                    batch.draw(animation.idleAnimation.getKeyFrame(animation.stateTime, true), position.x-yOffset, position.y+position.z);
                }
            } else {
                float yOffset = 0;
                if(ComponentMappers.mapCollision.get(e) == null){
                    yOffset = visual.region.getRegionWidth()/2;
                }
                batch.draw(visual.region, position.x-yOffset, position.y+position.z);
            }

            //Check special attack hits
            AttackComponent attack = ComponentMappers.attack.get(e);
            if(attack != null){
                if(attack.laser != null){
                    attack.laser.render(batch, deltaTime);
                }
            }
        }

        batch.end();
    }
}
