package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 21.12.2015.
 */
public class CollisionComponent implements Component, Pool.Poolable {

    public float size;
    boolean ghost;
    CollisionListener collisionListener;

    public CollisionComponent() {
        this.size = 16;
        this.ghost = false;
    }

    public CollisionComponent(float size,CollisionListener collisionListener) {
        this.size = size;
        this.collisionListener = collisionListener;
        this.ghost = false;
    }

    @Override
    public void reset() {
        size = 16;
        collisionListener = null;
        this.ghost = false;
    }


    public interface CollisionListener{

        boolean onCollision(Entity other);

    }
}
