package de.dogedevs.photoria.model.entity.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
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

    public SlimeAi() {
        playerEntity = GameScreen.getAshley().getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0);
    }

    @Override
    public void tick(float deltaTime, Entity self) {
        PlayerComponent playerComponent = ComponentMappers.player.get(playerEntity);
        PositionComponent playerPosition = ComponentMappers.position.get(playerEntity);


        VelocityComponent velocity = ComponentMappers.velocity.get(self);
        PositionComponent selfPosition = ComponentMappers.position.get(self);

        double sqrt = Math.sqrt(Math.pow(playerPosition.x - selfPosition.x, 2) +
                Math.pow(playerPosition.y - selfPosition.y, 2));
        if(sqrt <= 100) {
            velocity.speed = 20;
        } else {
            velocity.speed = 0;
        }
    }

}
