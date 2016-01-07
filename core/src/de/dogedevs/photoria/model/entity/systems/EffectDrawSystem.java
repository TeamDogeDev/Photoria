package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.AttackComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;

/**
 * Created by Furuha on 21.12.2015.
 */
public class EffectDrawSystem extends EntitySystem  {


    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private ImmutableArray<Entity> entities;

    public EffectDrawSystem(OrthographicCamera camera) {
        this.camera = camera;
        batch = new SpriteBatch();
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, AttackComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {
    }

    @Override
    public void update (float deltaTime) {

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);

            //Check special attack hits
            AttackComponent attack = ComponentMappers.attack.get(e);
            if(attack != null){
                if(attack.laser != null){
                    attack.laser.render(batch, deltaTime);
                }
            }
        }

        batch.end();
    }
}
