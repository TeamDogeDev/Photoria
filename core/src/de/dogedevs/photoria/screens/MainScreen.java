package de.dogedevs.photoria.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import de.dogedevs.photoria.Config;
import de.dogedevs.photoria.model.entity.components.AnimationComponent;
import de.dogedevs.photoria.model.entity.components.PlayerComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.entity.components.VelocityComponent;
import de.dogedevs.photoria.model.entity.systems.CameraSystem;
import de.dogedevs.photoria.model.entity.systems.EntityDrawSystem;
import de.dogedevs.photoria.model.entity.systems.MovingEntitySystem;
import de.dogedevs.photoria.model.entity.systems.PlayerControllSystem;
import de.dogedevs.photoria.rendering.map.CustomTiledMapRenderer;
import de.dogedevs.photoria.rendering.map.MapBuilder;
import de.dogedevs.photoria.rendering.overlay.AbstractOverlay;
import de.dogedevs.photoria.rendering.overlay.DebugOverlay;
import de.dogedevs.photoria.rendering.overlay.GameOverlay;
import de.dogedevs.photoria.utlis.ScreenshotFactory;

/**
 * Created by Furuha on 20.12.2015.
 */
public class MainScreen implements Screen {

    static private PooledEngine ashley;

    private Batch batch, waterBatch, mapBatch;
    private MapBuilder mapBuilder;
    private CustomTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;

    private ShaderProgram shader;

    private Array<AbstractOverlay> overlays = new Array();

    private final int[] fluidLayer = { 0 };
    private final int[] foregroundLayers = { 1,2,3 };

    public void show() {

        initCamera();
        initAshley();
        initOverlays();
        initMap();
        initEntities();

        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean scrolled(int amount) {
                camera.zoom += amount;
                if(camera.zoom < 1){
                    camera.zoom = 1;
                }
                return super.scrolled(amount);
            }

            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Input.Keys.NUM_1){
                    mapBuilder.getTiledMap().getLayers().get("ground").setVisible(!mapBuilder.getTiledMap().getLayers().get("ground").isVisible());
                }
                if(keycode == Input.Keys.NUM_2){
                    mapBuilder.getTiledMap().getLayers().get("ground2").setVisible(!mapBuilder.getTiledMap().getLayers().get("ground2").isVisible());
                }
                if(keycode == Input.Keys.NUM_3){
                    mapBuilder.getTiledMap().getLayers().get("debug").setVisible(!mapBuilder.getTiledMap().getLayers().get("debug").isVisible());
                }
                if(keycode == Input.Keys.F12){
                    ScreenshotFactory.saveScreenshot();
                }
                return super.keyDown(keycode);
            }
        });
    }



    private void initAshley() {
        getAshley().addSystem(new PlayerControllSystem());
        getAshley().addSystem(new EntityDrawSystem(camera));
        getAshley().addSystem(new MovingEntitySystem());
        if(!Config.enableDebugCamera){
            getAshley().addSystem(new CameraSystem(camera));
        }
    }

    private void initOverlays() {
        if(Config.showDebugUi){
            overlays.add(new DebugOverlay(camera, getAshley()));
        }
        overlays.add(new GameOverlay());
    }

    private void initCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.translate(300*64*32, 300*64*32);
        camera.zoom = 1;

        camera.update();
    }

    private void initMap() {
        mapBuilder = new MapBuilder();
        tiledMapRenderer = new CustomTiledMapRenderer(mapBuilder.getTiledMap());
        mapBatch = tiledMapRenderer.getBatch();
        batch = new SpriteBatch();
        waterBatch = new SpriteBatch();

        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("./shaders/liquidShader.vsh"), Gdx.files.internal("./shaders/liquidShader.fsh"));
        System.out.println(shader.isCompiled() ? "Shader compiled" : shader.getLog());
        waterBatch.setShader(shader);

        tiledMapRenderer.setBatch(waterBatch);
    }

    private void initEntities() {

        Texture walkSheet = new Texture(Gdx.files.internal("eyeball.png"));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/3, walkSheet.getHeight()/4);
        TextureRegion[][] walkFrames = new TextureRegion[4][3 * 1];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                walkFrames[i][j] = tmp[i][j];
            }
        }
        Animation walkAnimationU = new Animation(0.3f, walkFrames[0]);
        Animation walkAnimationD = new Animation(0.3f, walkFrames[2]);
        Animation walkAnimationL = new Animation(0.3f, walkFrames[1]);
        Animation walkAnimationR = new Animation(0.3f, walkFrames[3]);

        Entity eyeball = getAshley().createEntity();
        eyeball.add(new PlayerComponent());
        eyeball.add(new PositionComponent(300 * 64 * 32 + (32*32), 300 * 64 * 32 + (32*32)));
        eyeball.add(new VelocityComponent(0, 1));
        AnimationComponent ac = new AnimationComponent(walkAnimationD);
        ac.leftAnimation = walkAnimationL;
        ac.rightAnimation = walkAnimationR;
        ac.upAnimation = walkAnimationU;
        ac.downAnimation = walkAnimationD;
        eyeball.add(ac);

        getAshley().addEntity(eyeball);

        if(Config.spawnDebugEntities){
            int max = Config.debugEntitiesPosMax;
            int min = Config.debugEntitiesPosMin;
            int numEntities = Config.debugEntities; // 4000
            for (int i = 0; i < numEntities; i++) {
                eyeball = getAshley().createEntity();
                eyeball.add(new PositionComponent(MathUtils.random(min*64*32, max*64*32), MathUtils.random(min*64*32, max*64*32)));
                ac = new AnimationComponent(walkAnimationU);
                ac.leftAnimation = walkAnimationL;
                ac.rightAnimation = walkAnimationR;
                ac.upAnimation = walkAnimationU;
                ac.downAnimation = walkAnimationD;
                eyeball.add(ac);
                eyeball.add(new VelocityComponent(0, 20));
                getAshley().addEntity(eyeball);
            }
        }

    }

    private float angleWaveSpeed = 2.5f;
    private float amplitudeWave = 2;
    private float angleWave = 0;

    public void render(float delta) {
        //Clear buffer
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Process debug camera controls
        if(Config.enableDebugCamera){
            input();
        }

        //Camera
        camera.update();

        //Render fluid maps
        angleWave += delta * angleWaveSpeed;
        while(angleWave > Math.PI*2){
            angleWave -= Math.PI*2;
        }

        shader.begin();
        shader.setUniformf("waveData", angleWave, amplitudeWave);
        shader.end();

        tiledMapRenderer.setBatch(waterBatch);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(fluidLayer);

        //Render map
        tiledMapRenderer.setBatch(mapBatch);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(foregroundLayers);

        //Process entities
        ashley.update(Gdx.graphics.getDeltaTime());

        //Render Overlays
        for(AbstractOverlay overlay : overlays) {
            if(overlay.isVisible()) {
                overlay.render();
            }
        }

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
            camera.translate(-40*camera.zoom,0);
        if( Gdx.input.isKeyPressed(Input.Keys.D))
            camera.translate(40*camera.zoom,0);
        if( Gdx.input.isKeyPressed(Input.Keys.S))
            camera.translate(0,-40*camera.zoom);
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            camera.translate(0,40*camera.zoom);
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

    public static PooledEngine getAshley(){
        if(ashley == null){
            ashley = new PooledEngine();
        }
        return ashley;
    }

}
