package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 27.12.2015.
 */
public class ItemComponent implements Component, Pool.Poolable {

    public String name;
    public ItemType type;

    public float maxLife;
    public float maxEnergy;
    public float lifeReg;
    public float energyReg;
    public float dmgElementPurple;
    public float dmgElementGreen;
    public float dmgElementRed;
    public float dmgElementBlue;
    public float dmgElementYellow;
    public float defElementPurple;
    public float defElementGreen;
    public float defElementRed;
    public float defElementBlue;
    public float defElementYellow;
    public float movementSpeed;

    public ItemComponent() {
        reset();
    }

    @Override
    public void reset() {
        name = "Item";
        type = ItemType.OTHER;
        maxLife = 0;
        maxEnergy = 0;
        lifeReg = 0;
        energyReg = 0;
        dmgElementPurple = 0;
        dmgElementGreen = 0;
        dmgElementRed = 0;
        dmgElementBlue = 0;
        dmgElementYellow = 0;
        defElementPurple = 0;
        defElementGreen = 0;
        defElementRed = 0;
        defElementBlue = 0;
        defElementYellow = 0;
        movementSpeed = 0;
    }

    public enum ItemType {
        ATTACK, DEFENSE, REGENERATION, STATSUP, OTHER, USE;
    }
}
