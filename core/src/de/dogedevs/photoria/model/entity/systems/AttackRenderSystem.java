package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.AttackComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.entity.components.TargetComponent;
import de.dogedevs.photoria.model.entity.components.rendering.ParticleComponent;
import de.dogedevs.photoria.utils.assets.ParticlePool;

/**
 * Created by Furuha on 21.12.2015.
 */
public class AttackRenderSystem extends EntitySystem {


    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> particelEntities;

    public AttackRenderSystem(OrthographicCamera camera) {
        this.camera = camera;
        batch = new SpriteBatch();
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(AttackComponent.class).get());
        particelEntities = engine.getEntitiesFor(Family.all(ParticleComponent.class, PositionComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {
    }

    @Override
    public void update(float deltaTime) {

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);
            //Check special attack hits
            AttackComponent attack = ComponentMappers.attack.get(e);
            TargetComponent targetComponent = ComponentMappers.target.get(e);
            if (attack.weapon != null && targetComponent !=null && targetComponent.isShooting) {
                attack.weapon.updateActive(batch, deltaTime, 25);
            } else if(attack.weapon != null && targetComponent !=null && !targetComponent.isShooting){
                attack.weapon.updateInactive(batch, deltaTime, 25);
            }
        }

        for (int i = 0; i < particelEntities.size(); ++i) {
            Entity e = particelEntities.get(i);
            PositionComponent positionComponent = ComponentMappers.position.get(e);
            //Render particle here!!!
            Statics.particle.createParticleAt(ParticlePool.ParticleType.ENERGY_BALL, positionComponent.x, positionComponent.y);
        }

        batch.end();
    }
}
