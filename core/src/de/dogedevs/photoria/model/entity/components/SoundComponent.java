package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import de.dogedevs.photoria.utils.assets.enums.Sounds;

/**
 * Created by Furuha on 21.12.2015.
 */
public class SoundComponent implements Component, Pool.Poolable {

    public Sounds deathSound;
    public Sounds shotSound;
    public Sounds hitSound;
    public Sounds moveSound;
    public Sounds ambientSound;

    public float lastHitSound;
    public float lastAmbientSound;
    public float lastAmbientSoundDif;
    public float lastMoveSound;

    public SoundComponent() {
        reset();
    }

    @Override
    public void reset() {
        lastHitSound = 0;
        lastAmbientSound = 0;
        lastMoveSound = 0;
        lastAmbientSoundDif = 30;
        deathSound = null;
        shotSound = null;
        hitSound = null;
        moveSound = null;
        ambientSound = null;
    }

}
