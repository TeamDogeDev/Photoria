package de.dogedevs.photoria.rendering.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by elektropapst on 27.12.2015.
 */
public class GameOverlay extends AbstractOverlay {

    private static final String HUD_PATH = "./hud.png";
    private static final String NET_PATH = "./net.png";

    private static final int HUD_TILE_WIDTH = 32;
    private static final int HUD_TILE_HEIGHT = 32;

    private BitmapFont font;
    private Texture hudTexture = new Texture(Gdx.files.internal(HUD_PATH));
    private Texture netTexture = new Texture(Gdx.files.internal(NET_PATH));
    private TextureRegion[][] hudParts = TextureRegion.split(hudTexture, HUD_TILE_WIDTH, HUD_TILE_HEIGHT);

    private TextureRegion heart_full = hudParts[0][0];
    private TextureRegion heart_half = hudParts[0][1];
    private TextureRegion heart_empty = hudParts[0][2];

    private Vector2 netOffset = new Vector2(Gdx.graphics.getWidth()-netTexture.getWidth(), Gdx.graphics.getHeight()-netTexture.getHeight());

    private Vector2 netCenter = new Vector2(128,128);
    private Vector2 stat0 = new Vector2(128,244);
    private Vector2 stat1 = new Vector2(250,156);
    private Vector2 stat2 = new Vector2(204,12);
    private Vector2 stat3 = new Vector2(52,12);
    private Vector2 stat4 = new Vector2(6,156);

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
        renderHealth();
        renderStats();
    }

    private static ShapeRenderer shapeRenderer = new ShapeRenderer();

    private void renderStats() {
        batch.begin();
        batch.draw(netTexture, netOffset.x, netOffset.y);
        batch.end();

        Gdx.gl.glLineWidth(3);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN.add(Color.YELLOW));
        shapeRenderer.polygon(statsToVertices(.25f, .75f, .5f, .6f, .8f));

        shapeRenderer.end();
        Gdx.gl.glLineWidth(1);
    }

    private float[] statsToVertices(float st0, float st1, float st2, float st3, float st4) {
//        Vector2 posst0 = stat0.cpy().add(netCenter).add(netOffset).scl(1f);
        Vector2 posst0 = netOffset.cpy().add(netCenter.cpy().add(stat0.cpy().sub(netCenter).scl(st0)));
        Vector2 posst1 = netOffset.cpy().add(netCenter.cpy().add(stat1.cpy().sub(netCenter).scl(st1)));
        Vector2 posst2 = netOffset.cpy().add(netCenter.cpy().add(stat2.cpy().sub(netCenter).scl(st2)));
        Vector2 posst3 = netOffset.cpy().add(netCenter.cpy().add(stat3.cpy().sub(netCenter).scl(st3)));
        Vector2 posst4 = netOffset.cpy().add(netCenter.cpy().add(stat4.cpy().sub(netCenter).scl(st4)));
        return new float[]{posst0.x, posst0.y, posst1.x, posst1.y, posst2.x, posst2.y, posst3.x, posst3.y, posst4.x, posst4.y, };
    }

    private void renderHealth() {
        batch.begin();
        for (int i = 0; i < 10; i++) {
            if (i % 3 == 0) {
                batch.draw(heart_full, HUD_TILE_WIDTH / 2 + (i * HUD_TILE_WIDTH), Gdx.graphics.getHeight() - HUD_TILE_HEIGHT - (HUD_TILE_HEIGHT / 2));
            } else if (i % 3 == 1) {
                batch.draw(heart_half, HUD_TILE_WIDTH / 2 + (i * HUD_TILE_WIDTH), Gdx.graphics.getHeight() - HUD_TILE_HEIGHT - (HUD_TILE_HEIGHT / 2));
            } else {
                batch.draw(heart_empty, HUD_TILE_WIDTH / 2 + (i * HUD_TILE_WIDTH), Gdx.graphics.getHeight() - HUD_TILE_HEIGHT - (HUD_TILE_HEIGHT / 2));
            }
        }
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
        hudTexture.dispose();
        netTexture.dispose();
    }
}
