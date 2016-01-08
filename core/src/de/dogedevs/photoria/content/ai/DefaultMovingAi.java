package de.dogedevs.photoria.content.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.AiComponent;
import de.dogedevs.photoria.model.entity.components.VelocityComponent;

/**
 * Created by Furuha on 01.01.2016.
 */
public class DefaultMovingAi implements AiComponent.AiInterface {

    @Override
    public void tick(float deltaTime, Entity self) {

        VelocityComponent velocity = ComponentMappers.velocity.get(self);

        if(velocity != null){
            if(MathUtils.randomBoolean(0.001f)){
                velocity.direction = MathUtils.random(0, 7);
            }
            if(velocity.blockedDelta > 0.5f){
                velocity.direction = MathUtils.random(0, 7);
                velocity.blockedDelta = 0;
            }
        }
    }

}
