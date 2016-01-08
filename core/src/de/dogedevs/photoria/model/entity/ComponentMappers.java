package de.dogedevs.photoria.model.entity;

import com.badlogic.ashley.core.ComponentMapper;
import de.dogedevs.photoria.model.entity.components.*;
import de.dogedevs.photoria.model.entity.components.rendering.AnimationComponent;
import de.dogedevs.photoria.model.entity.components.rendering.SpriteComponent;
import de.dogedevs.photoria.model.entity.components.stats.ElementsComponent;
import de.dogedevs.photoria.model.entity.components.stats.EnergyComponent;
import de.dogedevs.photoria.model.entity.components.stats.HealthComponent;
import de.dogedevs.photoria.model.entity.components.stats.LifetimeComponent;

/**
 * Created by Furuha on 21.12.2015.
 */
public class ComponentMappers {

        public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
        public static final ComponentMapper<AnimationComponent> animation = ComponentMapper.getFor(AnimationComponent.class);
        public static final ComponentMapper<SpriteComponent> sprite = ComponentMapper.getFor(SpriteComponent.class);
        public static final ComponentMapper<VelocityComponent> velocity = ComponentMapper.getFor(VelocityComponent.class);
        public static final ComponentMapper<PlayerComponent> player = ComponentMapper.getFor(PlayerComponent.class);
        public static final ComponentMapper<CollisionComponent> collision = ComponentMapper.getFor(CollisionComponent.class);
        public static final ComponentMapper<HealthComponent> health = ComponentMapper.getFor(HealthComponent.class);
        public static final ComponentMapper<AiComponent> ai = ComponentMapper.getFor(AiComponent.class);
        public static final ComponentMapper<EnergyComponent> energy = ComponentMapper.getFor(EnergyComponent.class);
        public static final ComponentMapper<ElementsComponent> elements = ComponentMapper.getFor(ElementsComponent.class);
        public static final ComponentMapper<MapCollisionComponent> mapCollision = ComponentMapper.getFor(MapCollisionComponent.class);
        public static final ComponentMapper<InventoryComponent> inventory = ComponentMapper.getFor(InventoryComponent.class);
        public static final ComponentMapper<ItemComponent> item = ComponentMapper.getFor(ItemComponent.class);
        public static final ComponentMapper<LifetimeComponent> lifetime = ComponentMapper.getFor(LifetimeComponent.class);
        public static final ComponentMapper<AttackComponent> attack = ComponentMapper.getFor(AttackComponent.class);
        public static final ComponentMapper<ParentComponent> parent = ComponentMapper.getFor(ParentComponent.class);
        public static final ComponentMapper<TargetComponent> target = ComponentMapper.getFor(TargetComponent.class);
}
