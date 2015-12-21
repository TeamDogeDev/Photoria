package de.dogedevs.photoria.model.entity;

import com.badlogic.ashley.core.ComponentMapper;

/**
 * Created by Furuha on 21.12.2015.
 */
public class ComponentMappers {

        public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
        public static final ComponentMapper<SpriteComponent> sprite = ComponentMapper.getFor(SpriteComponent.class);
}
