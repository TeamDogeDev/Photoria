package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.entity.components.stats.HealthComponent;
import de.dogedevs.photoria.rendering.overlay.GameOverlay;
import de.dogedevs.photoria.utils.assets.ParticlePool;

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
                Statics.attack.deleteWeaponsFrom(entity);
                Statics.particle.createParticleAt(ParticlePool.ParticleType.BLOOD, pc.x, pc.y);
                if(ComponentMappers.sound.has(entity)){
                    Statics.sound.playSound(ComponentMappers.sound.get(entity).deathSound);
                }

                if(ComponentMappers.player.has(entity)){
                    GameOverlay.addTextbox("You have died and failed to acquire the resources to power your ship again!", 100);
                }

                getEngine().removeEntity(entity);

            }
            healthComponent.health += healthComponent.regHealthSecUse * deltaTime;
            healthComponent.health  = MathUtils.clamp(healthComponent.health, 0, healthComponent.maxHealthUse);

        }
    }
}
