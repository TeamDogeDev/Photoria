package de.dogedevs.photoria.model.entity.components.stats;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 27.12.2015.
 */
public class ElementsComponent implements Component, Pool.Poolable {

    public float red;
    public float green;
    public float blue;
    public float yellow;
    public float purple;

    public ElementsComponent(float red, float green, float blue, float yellow, float purple) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.yellow = yellow;
        this.purple = purple;
    }

    public ElementsComponent() {
        red = 0;
        green = 0;
        blue = 0;
        yellow = 0;
        purple = 0;
    }

    @Override
    public void reset() {
        red = 0;
        green = 0;
        blue = 0;
        yellow = 0;
        purple = 0;
    }
}
