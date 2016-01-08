package de.dogedevs.photoria.model.entity.components.rendering;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 21.12.2015.
 */
public class SpriteComponent implements Component, Pool.Poolable{

    public TextureRegion region;

    public SpriteComponent() {
    }

    public SpriteComponent(TextureRegion region) {
        this.region = region;
    }

    @Override
    public void reset() {
        region = null;
    }
}
