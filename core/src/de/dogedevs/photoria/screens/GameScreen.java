package de.dogedevs.photoria.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import de.dogedevs.photoria.Config;
import de.dogedevs.photoria.MainGame;
import de.dogedevs.photoria.model.entity.components.*;
import de.dogedevs.photoria.model.entity.components.rendering.AnimationComponent;
import de.dogedevs.photoria.model.entity.components.stats.ElementsComponent;
import de.dogedevs.photoria.model.entity.components.stats.EnergyComponent;
import de.dogedevs.photoria.model.entity.components.stats.HealthComponent;
import de.dogedevs.photoria.model.entity.systems.*;
import de.dogedevs.photoria.model.map.MapCompositor;
import de.dogedevs.photoria.rendering.map.CustomTiledMapRenderer;
import de.dogedevs.photoria.rendering.overlay.*;
import de.dogedevs.photoria.rendering.tiles.TileCollisionMapper;
import de.dogedevs.photoria.utils.ScreenshotFactory;
import de.dogedevs.photoria.utils.assets.AnimationLoader;
import de.dogedevs.photoria.utils.assets.AssetLoader;
import de.dogedevs.photoria.utils.assets.enums.ShaderPrograms;
import de.dogedevs.photoria.utils.assets.enums.Textures;

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

    private ShaderProgram waterShader, cloudShader, postShader;
    private FrameBuffer buffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    private Mesh quadMesh;

    private Array<AbstractOverlay> overlays = new Array();

    private final int[] fluidLayer = {0};
    private final int[] foregroundLayers = {1, 2, 3};

    private Texture clouds = AssetLoader.getTexture(Textures.CLOUD_STUB);

    public void show() {
//        ambient.play();

        initCamera();
        initMap();
        initShader();
        initAshley();
        initEntities();
        initOverlays();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean scrolled(int amount) {
                camera.zoom += amount;
                if (camera.zoom < 1) {
                    camera.zoom = 1;
                }
                return super.scrolled(amount);
            }

            boolean fullscreen = false;

            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.NUM_1) {
                    mapCompositor.getTiledMap().getLayers().get("ground").setVisible(!mapCompositor.getTiledMap().getLayers().get("ground").isVisible());
                }
                if (keycode == Input.Keys.NUM_2) {
                    mapCompositor.getTiledMap().getLayers().get("ground2").setVisible(!mapCompositor.getTiledMap().getLayers().get("ground2").isVisible());
                }
                if (keycode == Input.Keys.NUM_3) {
                    mapCompositor.getTiledMap().getLayers().get("debug").setVisible(!mapCompositor.getTiledMap().getLayers().get("debug").isVisible());
                }
                if (keycode == Input.Keys.F12) {
                    ScreenshotFactory.saveScreenshot();
                }
                if(keycode == Input.Keys.F11){
                    fullscreen = !fullscreen;
                    Gdx.graphics.setDisplayMode(1280, 720, fullscreen);
                    return true;
                } else if(keycode == Input.Keys.F9){
                    Gdx.app.exit();
                }

                return super.keyDown(keycode);
            }
        });
    }

    private void initAshley() {
        getAshley().addSystem(new AiSystem());
        getAshley().addSystem(new PlayerControllSystem());
        getAshley().addSystem(new MovingEntitySystem(mapCompositor.getBuffer()));
        getAshley().addSystem(new AttackSystem());
        getAshley().addSystem(new EntityDrawSystem(camera));
        getAshley().addSystem(new AttackRenderSystem(camera));
        getAshley().addSystem(new EntityGcSystem(camera, mapCompositor.getBuffer()));
        getAshley().addSystem(new MapCollisionSystem(mapCompositor.getBuffer()));
        getAshley().addSystem(new LifetimeSystem());
        getAshley().addSystem(new HealthSystem());
        if (!Config.enableDebugCamera) {
            getAshley().addSystem(new CameraSystem(camera));
        }
    }

    private void initOverlays() {
        if (Config.showDebugUi) {
            overlays.add(new DebugOverlay(camera, getAshley(), mapCompositor.getBuffer()));
        }
        overlays.add(new ParticleOverlay(camera));
        overlays.add(new LaserOverlay(camera));
        Entity playerEntity = getAshley().getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0);
        overlays.add(new GameOverlay(playerEntity));
        overlays.add(new MouseOverlay());
    }

    private void initCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.translate(300 * 64 * 32, 300 * 64 * 32);
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


        tiledMapRenderer.setBatch(waterBatch);
    }

    private void initEntities() {

//        Animation shipRight = AnimationLoader.getShipAnimation()[0];
        Animation[] playerAnimations = AnimationLoader.getPlayerAnimations();

        Entity player = getAshley().createEntity();
        player.add(new PlayerComponent());

        CollisionComponent cc = ashley.createComponent(CollisionComponent.class);
        cc.groundCollision = TileCollisionMapper.normalBorderCollision;
        Arrays.sort(cc.groundCollision);
        player.add(cc);
        PositionComponent pc = new PositionComponent(300 * 64 * 32 + (32 * 32), 300 * 64 * 32 + (32 * 32));
        pc.listener = new PositionComponent.OnBiomeChangeListener() {
            @Override
            public void onBiomeChange(int newBiome, int oldBiome) {
                MainGame.log("New Biome: "+newBiome +" OldBiome: "+oldBiome);
            }
        };
        player.add(pc);
        player.add(new VelocityComponent(0, 10));

        HealthComponent hc = ashley.createComponent(HealthComponent.class);
        hc.maxHealth = 1000000000;
        hc.health = 1000000000;
        hc.maxImmuneTime = 2;
        player.add(hc);

        EnergyComponent ec = ashley.createComponent(EnergyComponent.class);
        player.add(ec);

        ElementsComponent elc = ashley.createComponent(ElementsComponent.class);
        player.add(elc);

        InventoryComponent inventory = ashley.createComponent(InventoryComponent.class);
        player.add(inventory);

        AnimationComponent ac = new AnimationComponent(playerAnimations[4]);
        ac.leftAnimation = playerAnimations[2];
        ac.rightAnimation = playerAnimations[3];
        ac.upAnimation = playerAnimations[0];
        ac.downAnimation = playerAnimations[1];
        player.add(ac);

        getAshley().addEntity(player);

    }

    private void initShader() {
        cloudShader = AssetLoader.getShader(ShaderPrograms.CLOUD_SHADER);
        cloudBatch.setShader(cloudShader);
        cloudShader.begin();
        cloudShader.setUniformf("resolution", new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        cloudShader.setUniformf("cloudsize", 0.4f);
        cloudShader.end();

        waterShader = AssetLoader.getShader(ShaderPrograms.WATER_SHADER);
        waterBatch.setShader(waterShader);

        postShader = AssetLoader.getShader(ShaderPrograms.PASSTHROUGH_SHADER);
        testBatch.setShader(postShader);
//        quadMesh = Utils.createFullscreenQuad();

    }

    private float angleWaveSpeed = 2.5f;
    private float amplitudeWave = 2;
    private float angleWave = 0;
    private Vector2 windVelocity = new Vector2(0.0005f, 0f);
    private Vector2 windData = new Vector2(0, 0);

    private Batch testBatch = new SpriteBatch();

    public void render(float delta) {
        buffer.begin();
        {
            //Process debug camera controls
            if (Config.enableDebugCamera) {
                input();
            }

            //Camera
            mapCompositor.getBuffer().purgeChunks();
            camera.update();

            //Render fluid maps
            angleWave += delta * angleWaveSpeed;
            while (angleWave > Math.PI * 2) {
                angleWave -= Math.PI * 2;
            }

            waterShader.begin();
            waterShader.setUniformf("waveData", angleWave, amplitudeWave);
            waterShader.end();

            postShader.begin();
            postShader.setUniformf("waveData", angleWave, amplitudeWave);
            postShader.end();

            tiledMapRenderer.setBatch(waterBatch);
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render(fluidLayer);

            //Render map
            tiledMapRenderer.setBatch(mapBatch);
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render(foregroundLayers);

//        windVelocity.x = Gdx.input.getX() - (Gdx.graphics.getWidth()>>1);
//        windVelocity.y = Gdx.input.getY() - (Gdx.graphics.getHeight()>>1);
//        windVelocity.x /= 10_000;
//        windVelocity.y /= -10_000;

            //Process entities
            ashley.update(Gdx.graphics.getDeltaTime());

            windData.add(windVelocity);

            cloudShader.begin();
            cloudShader.setUniformf("waveData", angleWave, amplitudeWave);
            cloudShader.setUniformf("camPosition", camera.position);
            cloudShader.setUniformf("scroll", windData);
            cloudShader.end();


//        ImmutableArray<Entity> entitiesFor = ashley.getEntitiesFor(Family.all(PlayerComponent.class).get());
//        PositionComponent playerPos = ComponentMappers.position.get(entitiesFor.get(0));
//        entitiesFor.get(0);
//        cloudBatch.setBlendFunction(Gdx.gl.GL_ONE, Gdx.gl.GL_ONE_MINUS_SRC_COLOR);
//        cloudBatch.setBlendFunction(Gdx.gl.GL_DST_COLOR, Gdx.gl.GL_SRC_ALPHA);
//        cloudBatch.setBlendFunction(Gdx.gl.GL_DST_COLOR, Gdx.gl.GL_ONE);

            cloudBatch.setBlendFunction(Gdx.gl.GL_DST_COLOR, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
            cloudBatch.begin();
            cloudBatch.draw(clouds, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            cloudBatch.end();


        }
        buffer.end();

        //Clear buffer
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        testBatch.begin();
        testBatch.draw(buffer.getColorBufferTexture(),
                        0, 0,
                buffer.getColorBufferTexture().getWidth(), buffer.getColorBufferTexture().getHeight(),
                0, 0,
                buffer.getColorBufferTexture().getWidth(), buffer.getColorBufferTexture().getHeight(),
                false, true);

        testBatch.end();

        //Render Overlays
        for (AbstractOverlay overlay : overlays) {
            if (overlay.isVisible()) {
                overlay.render();
            }
        }


    }

    private void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            camera.translate(-32, 0);
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            camera.translate(32, 0);
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            camera.translate(0, -32);
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            camera.translate(0, 32);
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A))
            camera.translate(-40 * camera.zoom, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            camera.translate(40 * camera.zoom, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            camera.translate(0, -40 * camera.zoom);
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            camera.translate(0, 40 * camera.zoom);
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
        mapBatch.dispose();
        batch.dispose();
        cloudBatch.dispose();
        waterBatch.dispose();
        testBatch.dispose();
        waterShader.dispose();
//        ambient.stop();
//        ambient.dispose();

        for(AbstractOverlay overlay : overlays) {
            overlay.dispose();
        }
    }

    public static PooledEngine getAshley() {
        if (ashley == null) {
            ashley = new PooledEngine();
        }
        return ashley;
    }

}
