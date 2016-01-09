package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 27.12.2015.
 */
public class ItemComponent implements Component, Pool.Poolable {

    public String name;
    public ItemType type;

    public ItemComponent() {
        reset();
    }

    @Override
    public void reset() {
        name = "Item";
        type = ItemType.OTHER;
    }

    public enum ItemType {
        ATTACK, DEFENSE, REGENERATION, STATSUP, OTHER, USE;
    }
}
