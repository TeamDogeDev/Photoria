package de.dogedevs.photoria.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import de.dogedevs.photoria.rendering.MapBuilder;

/**
 * Created by Furuha on 20.12.2015.
 */
public class MainScreen implements Screen {

    SpriteBatch batch;
    Texture img;
    MapBuilder renderer;
    TiledMapRenderer tiledMapRenderer;
    OrthographicCamera camera;
    private BitmapFont font;

    public void show() {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.translate(200000, 200000);
        camera.zoom = 4;
        camera.update();

        font = new BitmapFont();

        renderer = new MapBuilder();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(renderer.getTiledMap());
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean scrolled(int amount) {
                camera.zoom += amount;
                if(camera.zoom < 1){
                    camera.zoom = 1;
                }
                return super.scrolled(amount);
            }
        });
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batch.begin();
//        batch.draw(img, 0, 0);
//        batch.end();


        input();
        update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        batch.end();
    }

    private void input() {
        if( Gdx.input.isKeyJustPressed(Input.Keys.A)){
            camera.translate(-32,0);
            return;
        }
        if( Gdx.input.isKeyJustPressed(Input.Keys.D)){
            camera.translate(32,0);
            return;
        }
        if( Gdx.input.isKeyJustPressed(Input.Keys.S)){
            camera.translate(0,-32);
            return;
        }
        if( Gdx.input.isKeyJustPressed(Input.Keys.W)){
            camera.translate(0,32);
            return;
        }

        if( Gdx.input.isKeyPressed(Input.Keys.A))
            camera.translate(-40,0);
        if( Gdx.input.isKeyPressed(Input.Keys.D))
            camera.translate(40,0);
        if( Gdx.input.isKeyPressed(Input.Keys.S))
            camera.translate(0,-40);
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            camera.translate(0,40);
    }

    private void update() {
        camera.update();
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
