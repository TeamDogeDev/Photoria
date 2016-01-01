package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 21.12.2015.
 */
public class CollisionComponent implements Component, Pool.Poolable {

    int height;
    int width;
    boolean ghost;
    CollisionListener collisionListener;

    public CollisionComponent() {
        this.height = 32;
        this.width = 32;
        this.ghost = false;
    }

    public CollisionComponent(int height, int width, CollisionListener collisionListener) {
        this.height = height;
        this.width = width;
        this.collisionListener = collisionListener;
        this.ghost = false;
    }

    @Override
    public void reset() {
        height = 32;
        width = 32;
        collisionListener = null;
        this.ghost = false;
    }


    public interface CollisionListener{

        boolean onCollision(Entity other);

    }
}
