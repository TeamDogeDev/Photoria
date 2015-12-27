package de.dogedevs.photoria.rendering.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by elektropapst on 27.12.2015.
 */
public class GameOverlay extends AbstractOverlay {

    private static final String HUD_PATH ="./hud.png";
    private static final int HUD_TILE_WIDTH = 32;
    private static final int HUD_TILE_HEIGHT = 32;
    private static BitmapFont font;
    private static Texture hudTexture = new Texture(Gdx.files.internal(HUD_PATH));
    private static TextureRegion[][] hudParts = TextureRegion.split(hudTexture, HUD_TILE_WIDTH, HUD_TILE_HEIGHT);

    private static TextureRegion heart_full = hudParts[0][0];
    private static TextureRegion heart_half = hudParts[0][1];
    private static TextureRegion heart_empty = hudParts[0][2];
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
        renderHealth();
        batch.end();
    }

    private void renderHealth() {
        for (int i = 0; i < 10; i++) {
            if(i%3==0) {
                batch.draw(heart_full, HUD_TILE_WIDTH / 2 + (i * HUD_TILE_WIDTH), Gdx.graphics.getHeight() - HUD_TILE_HEIGHT - (HUD_TILE_HEIGHT / 2));
            } else if(i%3 == 1) {
                batch.draw(heart_half, HUD_TILE_WIDTH/2 + (i*HUD_TILE_WIDTH), Gdx.graphics.getHeight()-HUD_TILE_HEIGHT-(HUD_TILE_HEIGHT/2));
            } else {
                batch.draw(heart_empty, HUD_TILE_WIDTH/2 + (i*HUD_TILE_WIDTH), Gdx.graphics.getHeight()-HUD_TILE_HEIGHT-(HUD_TILE_HEIGHT/2));
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
        hudTexture.dispose();
    }
}
