package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 27.12.2015.
 */
public class ItemComponent implements Component, Pool.Poolable {

    String name;

    public ItemComponent() {
        name = "Item";
    }

    @Override
    public void reset() {
        name = "Item";
    }
}
