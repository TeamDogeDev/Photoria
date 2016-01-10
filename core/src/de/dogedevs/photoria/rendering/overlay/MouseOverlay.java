package de.dogedevs.photoria.rendering.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import de.dogedevs.photoria.utils.Utils;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.utils.assets.enums.Textures;

/**
 * Created by elektropapst on 09.01.2016.
 */
public class MouseOverlay extends AbstractOverlay {

    private Texture cursorTexture;

    public MouseOverlay() {
        init();
    }

    @Override
    public void init() {
        cursorTexture = Statics.asset.getTexture(Textures.MOUSE_CURSOR);
    }

    @Override
    public void update(float delta) {
        Utils.lockMouseToScreen();
    }

    @Override
    public void render() {
        update(Gdx.graphics.getDeltaTime());
        batch.begin();
        batch.draw(cursorTexture, Gdx.input.getX() - (cursorTexture.getWidth() / 2), Gdx.graphics.getHeight() - Gdx.input.getY() - (cursorTexture.getWidth() / 2));
        batch.end();
    }
}
