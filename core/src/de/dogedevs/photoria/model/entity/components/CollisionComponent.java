package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 21.12.2015.
 */
public class CollisionComponent implements Component, Pool.Poolable {

    public float size;
    public boolean ghost;
    public boolean projectile;
    public int[] groundCollision;
    public CollisionListener collisionListener;

    public CollisionComponent() {
        this.size = 16;
        this.ghost = false;
        this.groundCollision = null;
    }

    public CollisionComponent(float size,CollisionListener collisionListener) {
        this.size = size;
        this.collisionListener = collisionListener;
        this.ghost = false;
        this.projectile = false;
        this.groundCollision = null;
    }

    @Override
    public void reset() {
        this.size = 16;
        this.collisionListener = null;
        this.groundCollision = null;
        this.projectile = false;
        this.ghost = false;
    }


    public interface CollisionListener{

        boolean onCollision(Entity other, Entity self);

    }
}
