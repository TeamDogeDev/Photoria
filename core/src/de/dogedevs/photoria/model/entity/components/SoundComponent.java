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
    public Sounds randomSound;
    public Sounds ambientSound;

    public SoundComponent() {
        reset();
    }

    @Override
    public void reset() {
        deathSound = null;
        shotSound = null;
        hitSound = null;
        moveSound = null;
        randomSound = null;
        ambientSound = null;
    }

}
