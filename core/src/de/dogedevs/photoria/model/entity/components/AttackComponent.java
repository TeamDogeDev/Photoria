package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import de.dogedevs.photoria.rendering.weapons.Flamethrower;
import de.dogedevs.photoria.rendering.weapons.Laser;

/**
 * Created by Furuha on 27.12.2015.
 */
public class AttackComponent implements Component, Pool.Poolable {

    public Laser laser;
    public Flamethrower flamethrower;

    public CollisionComponent.CollisionListener listener;

    public AttackComponent() {
        laser = null;
        flamethrower = null;
        listener = null;
    }

    @Override
    public void reset() {
        laser = null;
        flamethrower = null;
        listener = null;
    }
}
