package de.dogedevs.photoria.rendering.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by elektropapst on 27.12.2015.
 */
public abstract class AbstractOverlay {
    protected SpriteBatch batch = new SpriteBatch();
    protected boolean visible = true;

    public void update() {
        update(Gdx.graphics.getDeltaTime());
    }

    public abstract void init();
    public abstract void update(float delta);
    public abstract void render();

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void dispose() {
        batch.dispose();
    }
}
