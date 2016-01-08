package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.stats.HealthComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.utils.assets.ParticlePool;
import de.dogedevs.photoria.utils.assets.SoundManager;
import de.dogedevs.photoria.utils.assets.enums.Sounds;

/**
 * Created by Furuha on 21.12.2015.
 */
public class HealthSystem extends EntitySystem {


    private ImmutableArray<Entity> entities;

    public HealthSystem() {
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(HealthComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        HealthComponent healthComponent;
        for(Entity entity: entities){
            healthComponent = ComponentMappers.health.get(entity);
            if(healthComponent.immuneTime > 0){
                healthComponent.immuneTime -= deltaTime;
            }
            healthComponent.immuneTime = MathUtils.clamp(healthComponent.immuneTime, 0, healthComponent.maxImmuneTime);
            if(healthComponent.health <= 0){
                PositionComponent pc = ComponentMappers.position.get(entity);

                if(!ComponentMappers.player.has(entity)){
                    ParticlePool.instance().createParticleAt(ParticlePool.ParticleType.BLOOD, pc.x, pc.y);
                    SoundManager.playSound(Sounds.MOB_DIE);

                    getEngine().removeEntity(entity);
                }
            }
        }
    }
}
