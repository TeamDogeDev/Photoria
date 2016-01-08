package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 08.01.2016.
 */
public class ParentComponent implements Component, Pool.Poolable {

    public Entity parent;

    public ParentComponent() {
        parent = null;
    }

    @Override
    public void reset() {
        parent = null;
    }

}
