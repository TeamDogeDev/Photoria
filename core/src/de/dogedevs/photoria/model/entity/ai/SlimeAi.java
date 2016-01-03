package de.dogedevs.photoria.model.entity.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.AiComponent;
import de.dogedevs.photoria.model.entity.components.PlayerComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.entity.components.VelocityComponent;
import de.dogedevs.photoria.screens.GameScreen;

/**
 * Created by elektropapst on 03.01.2016.
 */
public class SlimeAi implements AiComponent.AiInterface {

    private Entity playerEntity;
    private static final float DIST = 200;

    public SlimeAi() {
        playerEntity = GameScreen.getAshley().getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0);
    }

    @Override
    public void tick(float deltaTime, Entity self) {
        PlayerComponent playerComponent = ComponentMappers.player.get(playerEntity);
        PositionComponent playerPosition = ComponentMappers.position.get(playerEntity);


        VelocityComponent velocity = ComponentMappers.velocity.get(self);
        PositionComponent selfPosition = ComponentMappers.position.get(self);
        if(velocity != null && selfPosition != null) {
            double eDist = Math.sqrt(Math.pow(playerPosition.x - selfPosition.x, 2) +
                    Math.pow(playerPosition.y - selfPosition.y, 2));

            if (eDist <= DIST) {
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
                velocity.speed = 80;
            } else {
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