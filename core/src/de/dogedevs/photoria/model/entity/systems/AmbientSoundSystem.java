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
import de.dogedevs.photoria.model.entity.components.SoundComponent;

/**
 * Created by Furuha on 21.12.2015.
 */
public class AmbientSoundSystem extends EntitySystem  {

    private ImmutableArray<Entity> entities;

    public AmbientSoundSystem() {
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, SoundComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        SoundComponent sound;
        PositionComponent pos;

        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);

            sound = ComponentMappers.sound.get(e);
            pos = ComponentMappers.position.get(e);

            if(sound != null && sound.ambientSound != null){
                if(Statics.time - sound.lastAmbientSound > sound.lastAmbientSoundDif){
                    if(MathUtils.randomBoolean(0.3f)){
                        Statics.sound.playSound(sound.ambientSound);
                        sound.lastAmbientSound = Statics.time;
                    } else {
                        sound.lastAmbientSound -= 3;
                    }

                }
            }
        }
    }

}
