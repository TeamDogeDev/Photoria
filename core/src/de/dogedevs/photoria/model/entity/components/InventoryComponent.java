package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Furuha on 27.12.2015.
 */
public class InventoryComponent implements Component, Pool.Poolable {

    public List<Entity> items;

    public InventoryComponent() {
        items = new ArrayList<>();
    }

    @Override
    public void reset() {
        items = new ArrayList<>();
    }

}
