package de.dogedevs.photoria.model.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Furuha on 27.12.2015.
 */
public class AiComponent implements Component, Pool.Poolable {

    public AiInterface ai;

    public AiComponent() {
    }

    public AiComponent(AiInterface ai) {
        this.ai = ai;
    }

    @Override
    public void reset() {
        ai = null;
    }

    public interface AiInterface {

        void tick(float deltaTime, Entity self);

    }
}
