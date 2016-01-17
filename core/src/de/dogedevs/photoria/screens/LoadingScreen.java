package de.dogedevs.photoria.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.dogedevs.photoria.MainGame;
import de.dogedevs.photoria.Statics;

/**
 * Created by elektropapst on 17.01.2016.
 */
public class LoadingScreen implements Screen {

    private Batch mainBatch;
    private BitmapFont menuFont;
    private ShapeRenderer shapeRenderer;
    public LoadingScreen() {
        init();
        Statics.initCat();
    }

    private void update(float delta) {
        if(Statics.asset.load()){
            MainGame.game.setScreen(new MainMenuScreen());
        }
    }

    private void init() {
        mainBatch = new SpriteBatch();
        menuFont = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        float progress = Statics.asset.progress();
        float val = 191*progress;
        Gdx.gl.glClearColor(val / 255f, val/ 255f, val/ 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.75f, 0.5f, 0, 1);
        float width = 300;
        float height = 20;
        float x = (Gdx.graphics.getWidth()- width)/2;
        float y = (Gdx.graphics.getHeight()- height)/2;
        shapeRenderer.rect(x, y, width*progress, height);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 0, 1);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

//        mainBatch.begin();
//        menuFont.draw(mainBatch, "Loading: " + Statics.asset.progress() + " ", 100, 100);
//        mainBatch.end();
    }

    private void load() {
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
