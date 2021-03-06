package de.dogedevs.photoria.content.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.AiComponent;
import de.dogedevs.photoria.model.entity.components.PlayerComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.entity.components.VelocityComponent;
import de.dogedevs.photoria.model.entity.components.stats.HealthComponent;
import de.dogedevs.photoria.utils.Utils;

/**
 * Created by elektropapst on 08.01.2016.
 */
public class EscapeOnDamageAi implements AiComponent.AiInterface {
    private Entity playerEntity;
    private static final float DIST = 600;
    public EscapeOnDamageAi() {
        playerEntity = Statics.ashley.getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0);
    }

    @Override
    public void tick(float deltaTime, Entity self) {
        PositionComponent playerPosition = ComponentMappers.position.get(playerEntity);
        HealthComponent selfHealth = ComponentMappers.health.get(self);

        VelocityComponent selfVelocity = ComponentMappers.velocity.get(self);
        PositionComponent selfPosition = ComponentMappers.position.get(self);

        if(selfVelocity != null && selfPosition != null && selfHealth != null) {
            double eDist = Utils.euclDist(selfPosition, playerPosition);
            if(eDist <= DIST && selfHealth.health < selfHealth.maxHealthUse) {
                if (playerPosition.x - selfPosition.x > 0) {
                    if (playerPosition.y - selfPosition.y > 0) {
                        selfVelocity.direction = VelocityComponent.SOUTH_WEST;
                    } else {
                        selfVelocity.direction = VelocityComponent.NORTH_WEST;
                    }
                } else {
                    if (playerPosition.y - selfPosition.y > 0) {
                        selfVelocity.direction = VelocityComponent.SOUTH_EAST;
                    } else {
                        selfVelocity.direction = VelocityComponent.NORTH_EAST;
                    }
                }
                selfVelocity.speed = 80;
            } else {
                if(MathUtils.randomBoolean(0.001f)){
                    selfVelocity.direction = MathUtils.random(0, 7);
                }
                if(selfVelocity.blockedDelta > 0.5f){
                    selfVelocity.direction = MathUtils.random(0, 7);
                    selfVelocity.blockedDelta = 0;
                }
                selfVelocity.speed = 20;
            }
        } else {

        }

    }
}
