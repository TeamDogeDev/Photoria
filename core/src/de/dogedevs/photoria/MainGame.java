package de.dogedevs.photoria;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import de.dogedevs.photoria.rendering.MapBuilder;

public class MainGame extends ApplicationAdapter implements InputProcessor {

    SpriteBatch batch;
    Texture img;
    MapBuilder renderer;
    TiledMapRenderer tiledMapRenderer;
    OrthographicCamera camera;
    private BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();

        font = new BitmapFont();

        renderer = new MapBuilder();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(renderer.getTiledMap());

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
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

    float delta = 0;
    private void input() {
        if( Gdx.input.isKeyJustPressed(Input.Keys.A)){
            camera.translate(-32,0);
            delta = -0.1f;
            return;
        }
        if( Gdx.input.isKeyJustPressed(Input.Keys.D)){
            camera.translate(32,0);
            delta = -0.1f;
            return;
        }
        if( Gdx.input.isKeyJustPressed(Input.Keys.S)){
            camera.translate(0,-32);
            delta = -0.1f;
            return;
        }
        if( Gdx.input.isKeyJustPressed(Input.Keys.W)){
            camera.translate(0,32);
            delta = -0.1f;
            return;
        }

        delta += Gdx.graphics.getDeltaTime();
        if(delta < 0.2f){
            return;
        } else {
            delta = 0;
        }

        if( Gdx.input.isKeyPressed(Input.Keys.A))
            camera.translate(-32,0);
        if( Gdx.input.isKeyPressed(Input.Keys.D))
            camera.translate(32,0);
        if( Gdx.input.isKeyPressed(Input.Keys.S))
            camera.translate(0,-32);
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            camera.translate(0,32);
    }

    private void update() {
        camera.update();
    }

    @Override
    public boolean keyDown(int keycode) {
//        if(keycode == Input.Keys.A)
//            camera.translate(-32,0);
//        if(keycode == Input.Keys.D)
//            camera.translate(32,0);
//        if(keycode == Input.Keys.S)
//            camera.translate(0,-32);
//        if(keycode == Input.Keys.W)
//            camera.translate(0,32);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
