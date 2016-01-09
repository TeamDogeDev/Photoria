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

    public List<Entity> slotUse;

    public Entity slotAttack;
    public Entity slotDefense;
    public Entity slotRegeneration;
    public Entity slotStatsUp;
    public Entity slotOther;

    public InventoryComponent() {
        slotUse = new ArrayList<>();
    }

    @Override
    public void reset() {
        slotAttack = null;
        slotDefense = null;
        slotRegeneration = null;
        slotStatsUp = null;
        slotOther = null;
        slotUse = new ArrayList<>();
    }

}
