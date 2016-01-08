package de.dogedevs.photoria.model.entity.components.rendering;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 21.12.2015.
 */
public class AnimationComponent implements Component, Pool.Poolable{

    public Animation idleAnimation;
    public Animation upAnimation;
    public Animation downAnimation;
    public Animation leftAnimation;
    public Animation rightAnimation;

    public float stateTime;

    public AnimationComponent() {
    }

    public AnimationComponent(Animation animation) {
        this.idleAnimation = animation;
    }

    @Override
    public void reset() {
        idleAnimation = null;
        upAnimation = null;
        downAnimation = null;
        leftAnimation = null;
        rightAnimation = null;
        stateTime = 0;
    }

}
