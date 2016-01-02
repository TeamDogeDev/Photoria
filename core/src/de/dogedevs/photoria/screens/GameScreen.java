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
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import de.dogedevs.photoria.Config;
import de.dogedevs.photoria.MainGame;
import de.dogedevs.photoria.model.entity.components.*;
import de.dogedevs.photoria.model.entity.systems.*;
import de.dogedevs.photoria.model.map.MapCompositor;
import de.dogedevs.photoria.rendering.map.CustomTiledMapRenderer;
import de.dogedevs.photoria.rendering.overlay.AbstractOverlay;
import de.dogedevs.photoria.rendering.overlay.DebugOverlay;
import de.dogedevs.photoria.rendering.overlay.GameOverlay;
import de.dogedevs.photoria.rendering.sprites.AnimationLoader;
import de.dogedevs.photoria.rendering.tiles.TileCollisionMapper;
import de.dogedevs.photoria.utils.ScreenshotFactory;

import java.util.Arrays;

/**
 * Created by Furuha on 20.12.2015.
 */
public class GameScreen implements Screen {

    static private PooledEngine ashley;

    private Batch batch, waterBatch, mapBatch, cloudBatch;
    private MapCompositor mapCompositor;
    private CustomTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;

    private ShaderProgram waterShader, cloudShader;

    private Array<AbstractOverlay> overlays = new Array();

    private final int[] fluidLayer = { 0 };
    private final int[] foregroundLayers = { 1,2,3 };

    private Texture clouds = new Texture(Gdx.files.internal("clouds.png"));

    public void show() {

        initCamera();
        initMap();
        initAshley();
        initEntities();
        initOverlays();

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
                    mapCompositor.getTiledMap().getLayers().get("ground").setVisible(!mapCompositor.getTiledMap().getLayers().get("ground").isVisible());
                }
                if(keycode == Input.Keys.NUM_2){
                    mapCompositor.getTiledMap().getLayers().get("ground2").setVisible(!mapCompositor.getTiledMap().getLayers().get("ground2").isVisible());
                }
                if(keycode == Input.Keys.NUM_3){
                    mapCompositor.getTiledMap().getLayers().get("debug").setVisible(!mapCompositor.getTiledMap().getLayers().get("debug").isVisible());
                }
                if(keycode == Input.Keys.F12){
                    ScreenshotFactory.saveScreenshot();
                }
                return super.keyDown(keycode);
            }
        });
    }

    private void initAshley() {
        getAshley().addSystem(new AiSystem());
        getAshley().addSystem(new PlayerControllSystem());
        getAshley().addSystem(new MovingEntitySystem(mapCompositor.getBuffer()));
        getAshley().addSystem(new EntityDrawSystem(camera));
        getAshley().addSystem(new EntityGcSystem(camera, mapCompositor.getBuffer()));
        if(!Config.enableDebugCamera){
            getAshley().addSystem(new CameraSystem(camera));
        }
    }

    private void initOverlays() {
        if(Config.showDebugUi){
            overlays.add(new DebugOverlay(camera, getAshley(), mapCompositor.getBuffer()));
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
        mapCompositor = new MapCompositor();
        tiledMapRenderer = new CustomTiledMapRenderer(mapCompositor.getTiledMap());
        mapBatch = tiledMapRenderer.getBatch();
        batch = new SpriteBatch();
        waterBatch = new SpriteBatch();
        cloudBatch = new SpriteBatch();

        ShaderProgram.pedantic = false;
        cloudShader = new ShaderProgram(Gdx.files.internal("./shaders/cloudShader.vsh"), Gdx.files.internal("./shaders/cloudShader.fsh"));
        MainGame.log(cloudShader.isCompiled() ? "CloudShader compiled" : cloudShader.getLog());
        cloudBatch.setShader(cloudShader);

        ShaderProgram.pedantic = false;
        waterShader = new ShaderProgram(Gdx.files.internal("./shaders/liquidShader.vsh"), Gdx.files.internal("./shaders/liquidShader.fsh"));
        MainGame.log(waterShader.isCompiled() ? "WaterShader compiled" : waterShader.getLog());
        waterBatch.setShader(waterShader);

        tiledMapRenderer.setBatch(waterBatch);
    }

    private void initEntities() {

//        Texture walkSheet = new Texture(Gdx.files.internal("eyeball.png"));
//        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/3, walkSheet.getHeight()/4);
//        TextureRegion[][] walkFrames = new TextureRegion[4][3 * 1];
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 3; j++) {
//                walkFrames[i][j] = tmp[i][j];
//            }
//        }





//        Animation shipRight = AnimationLoader.getShipAnimation()[0];
        Animation[] playerAnimations = AnimationLoader.getPlayerAnimations();

        Entity player = getAshley().createEntity();
        player.add(new PlayerComponent());
        CollisionComponent cc = ashley.createComponent(CollisionComponent.class);
        cc.groundCollision = TileCollisionMapper.normalBorderCollision;
        Arrays.sort(cc.groundCollision);
        player.add(cc);
        player.add(new PositionComponent(300 * 64 * 32 + (32*32), 300 * 64 * 32 + (32*32)));
        player.add(new VelocityComponent(0, 10));
        AnimationComponent ac = new AnimationComponent(playerAnimations[4]);
        ac.leftAnimation = playerAnimations[2];
        ac.rightAnimation = playerAnimations[3];
        ac.upAnimation = playerAnimations[0];
        ac.downAnimation = playerAnimations[1];
        player.add(ac);
        getAshley().addEntity(player);

//
//        if(Config.spawnDebugEntities){
//
//            int max = Config.debugEntitiesPosMax;
//            int min = Config.debugEntitiesPosMin;
//            int numEntities = Config.debugEntities;
//
//            AiComponent aiComponent = new AiComponent();
//            aiComponent.ai = new DefaultMovingAi();
//
//            Animation[] animations = AnimationLoader.getMovementAnimations("eyeball.png", true, 4, 3);
//            Animation walkAnimationU = animations[0];
//            Animation walkAnimationD = animations[1];
//            Animation walkAnimationL = animations[2];
//            Animation walkAnimationR = animations[3];
//
//            for (int i = 0; i < numEntities/2; i++) {
//                Entity eyeball = getAshley().createEntity();
//                eyeball.add(new PositionComponent(MathUtils.random(min*64*32, max*64*32), MathUtils.random(min*64*32, max*64*32)));
//                ac = new AnimationComponent(walkAnimationU);
//                ac.leftAnimation = walkAnimationL;
//                ac.rightAnimation = walkAnimationR;
//                ac.upAnimation = walkAnimationU;
//                ac.downAnimation = walkAnimationD;
//                eyeball.add(ac);
//                eyeball.add(new CollisionComponent());
//                eyeball.add(aiComponent);
//                eyeball.add(new VelocityComponent(0, 20));
//                getAshley().addEntity(eyeball);
//            }
//
//            animations = AnimationLoader.getMovementAnimations("slime.png", true, 4, 3);
//            walkAnimationU = animations[0];
//            walkAnimationD = animations[1];
//            walkAnimationL = animations[2];
//            walkAnimationR = animations[3];
//
//            for (int i = 0; i < numEntities/2; i++) {
//                Entity slime = getAshley().createEntity();
//                slime.add(new PositionComponent(MathUtils.random(min*64*32, max*64*32), MathUtils.random(min*64*32, max*64*32)));
//                ac = new AnimationComponent(walkAnimationU);
//                ac.leftAnimation = walkAnimationL;
//                ac.rightAnimation = walkAnimationR;
//                ac.upAnimation = walkAnimationU;
//                ac.downAnimation = walkAnimationD;
//                slime.add(ac);
//                slime.add(new CollisionComponent());
//                slime.add(aiComponent);
//                slime.add(new VelocityComponent(0, 20));
//                getAshley().addEntity(slime);
//            }
//        }

    }

    private float angleWaveSpeed = 2.5f;
    private float amplitudeWave = 2;
    private float angleWave = 0;
    private float tmp = 0;

    public void render(float delta) {
        //Clear buffer
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Process debug camera controls
        if(Config.enableDebugCamera){
            input();
        }

        //Camera
        mapCompositor.getBuffer().purgeChunks();
        camera.update();

        //Render fluid maps
        angleWave += delta * angleWaveSpeed;
        while(angleWave > Math.PI*2){
            angleWave -= Math.PI*2;
        }

        waterShader.begin();
        waterShader.setUniformf("waveData", angleWave, amplitudeWave);
        waterShader.end();

        tiledMapRenderer.setBatch(waterBatch);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(fluidLayer);

        //Render map
        tiledMapRenderer.setBatch(mapBatch);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(foregroundLayers);



        //Process entities
        ashley.update(Gdx.graphics.getDeltaTime());

        tmp += 0.001f;
        cloudShader.begin();
        cloudShader.setUniformf("camPosition", camera.position);
        cloudShader.setUniformf("scroll", new Vector2(tmp, 0));
        cloudShader.end();


//        ImmutableArray<Entity> entitiesFor = ashley.getEntitiesFor(Family.all(PlayerComponent.class).get());
//        PositionComponent playerPos = ComponentMappers.position.get(entitiesFor.get(0));
//        entitiesFor.get(0);
//        cloudBatch.setBlendFunction(Gdx.gl.GL_ONE, Gdx.gl.GL_ONE_MINUS_SRC_COLOR);
//        cloudBatch.setBlendFunction(Gdx.gl.GL_DST_COLOR, Gdx.gl.GL_SRC_ALPHA);
        cloudBatch.setBlendFunction(Gdx.gl.GL_DST_COLOR, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
//        cloudBatch.setBlendFunction(Gdx.gl.GL_DST_COLOR, Gdx.gl.GL_ONE);
        cloudBatch.begin();
        cloudBatch.draw(clouds, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cloudBatch.end();

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
        batch.dispose();
        cloudBatch.dispose();
        mapBatch.dispose();
        waterBatch.dispose();
        waterShader.dispose();
        tiledMapRenderer.dispose();
    }

    public static PooledEngine getAshley(){
        if(ashley == null){
            ashley = new PooledEngine();
        }
        return ashley;
    }

}
