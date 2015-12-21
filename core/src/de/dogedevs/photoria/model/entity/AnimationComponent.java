package de.dogedevs.photoria.model.entity;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 21.12.2015.
 */
public class AnimationComponent implements Component, Pool.Poolable{

    public Animation animation;
    public float stateTime;

    public AnimationComponent() {
    }

    public AnimationComponent(Animation animation) {
        this.animation = animation;
    }

    @Override
    public void reset() {
        animation = null;
        stateTime = 0;
    }
}
