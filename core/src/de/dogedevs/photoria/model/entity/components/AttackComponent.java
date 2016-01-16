package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pool;
import de.dogedevs.photoria.content.weapons.Weapon;

/**
 * Created by Furuha on 27.12.2015.
 */
public class AttackComponent implements Component, Pool.Poolable {


    public OnHitListener listener;
    public Weapon weapon;

    public AttackComponent() {
        weapon = null;
        listener = null;
    }

    @Override
    public void reset() {
        weapon.updateInactive(null, Gdx.graphics.getDeltaTime(), 0);
        weapon = null;
        listener = null;
    }

    public interface OnHitListener {

        public void onEnemyHit(Entity target, Entity attack, Entity parent);

    }
}
