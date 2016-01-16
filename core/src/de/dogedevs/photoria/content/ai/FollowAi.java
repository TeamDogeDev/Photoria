package de.dogedevs.photoria.content.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.*;
import de.dogedevs.photoria.model.entity.components.stats.HealthComponent;
import de.dogedevs.photoria.utils.Utils;

/**
 * Created by elektropapst on 03.01.2016.
 */
public class FollowAi implements AiComponent.AiInterface {

    private Entity playerEntity;
    private static final float DIST = 200;

    private float ptStarX, ptStarY;


    public FollowAi() {
        playerEntity = Statics.ashley.getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0);
    }

    @Override
    public void tick(float deltaTime, Entity self) {
        PlayerComponent playerComponent = ComponentMappers.player.get(playerEntity);
        PositionComponent playerPosition = ComponentMappers.position.get(playerEntity);
        HealthComponent selfHealth = ComponentMappers.health.get(self);

        VelocityComponent velocity = ComponentMappers.velocity.get(self);
        PositionComponent selfPosition = ComponentMappers.position.get(self);
        TargetComponent target = ComponentMappers.target.get(self);
        if(velocity != null && selfPosition != null && selfHealth != null && target != null) {
            double eDist = Utils.euclDist(selfPosition, playerPosition);

            if (eDist <= DIST ) {
                if (playerPosition.x - selfPosition.x > 0) {
                    if (playerPosition.y - selfPosition.y > 0) {
                        velocity.direction = VelocityComponent.NORTH_EAST;
                    } else {
                        velocity.direction = VelocityComponent.SOUTH_EAST;
                    }
                } else {
                    if (playerPosition.y - selfPosition.y > 0) {
                        velocity.direction = VelocityComponent.NORTH_WEST;
                    } else {
                        velocity.direction = VelocityComponent.SOUTH_WEST;
                    }
                }

                target.isShooting = true;

                if(ptStarX == 0 && ptStarY == 0) {
                    ptStarX = playerPosition.x;
                    ptStarY = playerPosition.y;
                } else {
                    float alpha = 0.95f;
                    ptStarX = (alpha * ptStarX) + ((1 - alpha) * playerPosition.x);
                    ptStarY = (alpha * ptStarY) + ((1 - alpha) * playerPosition.y);
                }

                target.x = ptStarX;
                target.y = ptStarY;
//                if(MathUtils.randomBoolean(0.1f)){
//                    Vector2 dir = new Vector2();
//                    dir.set(playerPosition.x, playerPosition.y).sub(selfPosition.x, selfPosition.y).nor();
//                    Statics.attack.shootNormal(self, dir, null);
                    target.isShooting = true;
//                } else {
//                target.isShooting = false;
//                }
                velocity.speed = 80;
            } else {
                ptStarX = ptStarY = 0;
                target.isShooting = false;
                if(MathUtils.randomBoolean(0.001f)){
                    velocity.direction = MathUtils.random(0, 7);
                }
                if(velocity.blockedDelta > 0.5f){
                    velocity.direction = MathUtils.random(0, 7);
                    velocity.blockedDelta = 0;
                }
                velocity.speed = 20;
            }
        }
    }

}
