package de.dogedevs.photoria.rendering.overlay;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by elektropapst on 27.12.2015.
 */
public class GameOverlay extends AbstractOverlay {

    private BitmapFont font;

    public GameOverlay() {
        init();
    }

    @Override
    public void init() {
        font = new BitmapFont();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render() {
        batch.begin();
        font.setColor(1, 0, 0, 1);
        font.draw(batch, "HELLO GAME OVERLAY", 100, 100);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
    }
}
