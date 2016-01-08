package de.dogedevs.photoria.model.entity.components.rendering;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 21.12.2015.
 */
public class ParticleComponent implements Component, Pool.Poolable{

    public ParticleComponent() {
    }

    public ParticleComponent(Animation animation) {

    }

    @Override
    public void reset() {

    }

}
