package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import de.dogedevs.photoria.model.entity.components.AnimationComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.entity.components.SpriteComponent;
import de.dogedevs.photoria.model.entity.components.VelocityComponent;

/**
 * Created by Furuha on 21.12.2015.
 */
public class ComponentMappers {

        public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
        public static final ComponentMapper<AnimationComponent> animation = ComponentMapper.getFor(AnimationComponent.class);
        public static final ComponentMapper<SpriteComponent> sprite = ComponentMapper.getFor(SpriteComponent.class);
        public static final ComponentMapper<VelocityComponent> velocity = ComponentMapper.getFor(VelocityComponent.class);
}
