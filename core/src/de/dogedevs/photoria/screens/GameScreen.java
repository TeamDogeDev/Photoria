package de.dogedevs.photoria.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import de.dogedevs.photoria.Config;
import de.dogedevs.photoria.MainGame;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.model.entity.components.*;
import de.dogedevs.photoria.model.entity.components.rendering.AnimationComponent;
import de.dogedevs.photoria.model.entity.components.rendering.SpriteComponent;
import de.dogedevs.photoria.model.entity.components.stats.ElementsComponent;
import de.dogedevs.photoria.model.entity.components.stats.EnergyComponent;
import de.dogedevs.photoria.model.entity.components.stats.HealthComponent;
import de.dogedevs.photoria.model.entity.systems.*;
import de.dogedevs.photoria.model.map.ChunkBuffer;
import de.dogedevs.photoria.model.map.MapCompositor;
import de.dogedevs.photoria.rendering.map.CustomTiledMapRenderer;
import de.dogedevs.photoria.rendering.overlay.*;
import de.dogedevs.photoria.rendering.tiles.TileCollisionMapper;
import de.dogedevs.photoria.utils.ScreenshotFactory;
import de.dogedevs.photoria.utils.assets.enums.ShaderPrograms;
import de.dogedevs.photoria.utils.assets.enums.Sounds;
import de.dogedevs.photoria.utils.assets.enums.Textures;

import java.util.*;

/**
 * Created by Furuha on 20.12.2015.
 */
public class GameScreen implements Screen {

    private Batch batch, waterBatch, mapBatch, cloudBatch;
    private MapCompositor mapCompositor;
    private CustomTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;

    private ShaderProgram waterShader, cloudShader, postShaderOld, postShaderNew;
    private FrameBuffer buffer1 = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    private FrameBuffer buffer2 = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

    private Array<AbstractOverlay> overlays = new Array();

    private final int[] fluidLayer = {0};
    private final int[] foregroundLayers = {1, 2, 3};

    private Texture clouds = Statics.asset.getTexture(Textures.CLOUD_STUB);
    boolean pause = false;
    boolean fadingDone = true;

    public GameScreen() {
        initCamera();
        initMap();
        initShader();
        initAshley();
        initEntities();
        initOverlays();
    }

    public void show() {
//        ambient.play();
        pause = false;

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
                if (keycode == Input.Keys.ALT_RIGHT) {
                    Statics.stats.bottleYellow = 1;
                    Statics.stats.bottleRed = 1;
                    Statics.stats.bottleBlue = 1;
                    Statics.stats.bottleGreen = 1;
                    Statics.stats.bottlePurple = 1;
                }
                if (keycode == Input.Keys.F12) {
                    ScreenshotFactory.saveScreenshot();
                }
                if (keycode == Input.Keys.F11) {
                    fullscreen = !fullscreen;
                    Gdx.graphics.setDisplayMode(1280, 720, fullscreen);
                    return true;
                } else if (keycode == Input.Keys.F9) {
                    Gdx.app.exit();
                }

                return super.keyDown(keycode);
            }
        });


    }

    private void initAshley() {
        Statics.ashley.addSystem(new AiSystem());
        Statics.ashley.addSystem(new PlayerControllSystem(mapCompositor.getBuffer()));
        Statics.ashley.addSystem(new MovingEntitySystem(mapCompositor.getBuffer()));
        Statics.ashley.addSystem(new DecreaseZSystem());
        Statics.ashley.addSystem(new AttackSystem());
        Statics.ashley.addSystem(new EntityDrawSystem(camera));
        Statics.ashley.addSystem(new AttackRenderSystem(camera));
        Statics.ashley.addSystem(new EntityGcSystem(camera, mapCompositor.getBuffer()));
        Statics.ashley.addSystem(new MapCollisionSystem(mapCompositor.getBuffer()));
        Statics.ashley.addSystem(new LifetimeSystem());
        Statics.ashley.addSystem(new HealthSystem());
        Statics.ashley.addSystem(new EnergySystem());
        Statics.ashley.addSystem(new AmbientSoundSystem());
        if (!Config.enableDebugCamera) {
            Statics.ashley.addSystem(new CameraSystem(camera));
        }
    }

    private void initOverlays() {
        if (Config.showDebugUi) {
            overlays.add(new DebugOverlay(camera, Statics.ashley, mapCompositor.getBuffer()));
        }
        overlays.add(new ParticleOverlay(camera));
        overlays.add(new LaserOverlay(camera));
        Entity playerEntity = Statics.ashley.getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0);
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

//        Animation shipRight = AnimationManager.getShipAnimation()[0];
        Animation[] playerAnimations = Statics.animation.getPlayerAnimations();

        Entity player = Statics.ashley.createEntity();
        player.add(new PlayerComponent());

        CollisionComponent cc = Statics.ashley.createComponent(CollisionComponent.class);
        cc.groundCollision = TileCollisionMapper.normalBorderCollision;
        Arrays.sort(cc.groundCollision);
        player.add(cc);
        PositionComponent pc = new PositionComponent(300 * 64 * 32 + (32 * 32), 300 * 64 * 32 + (32 * 32));
        pc.listener = new PositionComponent.OnBiomeChangeListener() {
            private Set<Integer> biomes = new HashSet<>();

            @Override
            public void onBiomeChange(int newBiome, int oldBiome) {
                updatePostProcessing(oldBiome, newBiome);

                if (!biomes.contains(newBiome)) {
                    List<String> messages = Statics.message.getEnterMessageForBiome(newBiome);
                    Statics.sound.playSound(Sounds.BIOM_ENTER);
                    for (String s : messages) {
                        float duration = (s.split(" ").length / 200f) * 60; // 2oo words pro min
                        duration = duration < 5 ? 5 : duration; // min 5 sec.
                        GameOverlay.addTextbox(s, duration);
                        biomes.add(newBiome);
                    }
                }
            }
        };
        player.add(pc);
        player.add(new VelocityComponent(0, 10));

        HealthComponent hc = Statics.ashley.createComponent(HealthComponent.class);
        hc.maxHealth = 100;
        hc.maxHealthUse = 100;
        hc.health = 100;
        hc.maxImmuneTime = 2;
        player.add(hc);

        EnergyComponent ec = Statics.ashley.createComponent(EnergyComponent.class);
        player.add(ec);

        ElementsComponent elc = Statics.ashley.createComponent(ElementsComponent.class);

        // RGBYP- color space
        elc.red = 0.10f;
        elc.green = 0.101f;
        elc.blue = 0.102f;
        elc.yellow = 0.103f;
        elc.purple = 0.104f;


        player.add(elc);

        InventoryComponent inventory = Statics.ashley.createComponent(InventoryComponent.class);
        player.add(inventory);

        AnimationComponent ac = new AnimationComponent(playerAnimations[4]);
        ac.leftAnimation = playerAnimations[2];
        ac.rightAnimation = playerAnimations[3];
        ac.upAnimation = playerAnimations[0];
        ac.downAnimation = playerAnimations[1];
        player.add(ac);


        SoundComponent soundc = Statics.ashley.createComponent(SoundComponent.class);
        soundc.shotSound = Sounds.EYE_SHOT;
        soundc.moveSound = Sounds.SLIME_MOVEMENT;
        soundc.deathSound = Sounds.SLIME_DEATH;
        soundc.hitSound = Sounds.PLAYER_HIT1;
        player.add(soundc);

        Statics.ashley.addEntity(player);

        Entity entity = Statics.ashley.createEntity();
        PositionComponent shipPc = Statics.ashley.createComponent(PositionComponent.class);
        shipPc.x = 300 * 64 * 32 + (30 * 32);
        shipPc.y =  300 * 64 * 32 + (32 * 32);
        entity.add(shipPc);
        SpriteComponent sc = Statics.ashley.createComponent(SpriteComponent.class);
        sc.region = new TextureRegion(Statics.asset.getTexture(Textures.SPACE_SHIP));
        entity.add(sc);
        CollisionComponent shipCc = Statics.ashley.createComponent(CollisionComponent.class);
        shipCc.ghost = false;
        shipCc.projectile = false;
        entity.add(shipCc);
        entity.add(Statics.ashley.createComponent(AvoidGcComponent.class));

        Statics.ashley.addEntity(entity);
    }


    private void updatePostProcessing(int oldBiom, int newBiom) {
        if(fadingDone) {
            intensityOld = 1;
            intensityNew = 0;
        } else {
            float tmp = intensityOld;
            intensityOld = intensityNew;
            intensityNew = tmp;
        }
        postShaderOld = biomShaderPrograms.get(oldBiom);
        postShaderNew = biomShaderPrograms.get(newBiom);
        postProcessingBatch1.setShader(postShaderOld);
        postProcessingBatch2.setShader(postShaderNew);
    }

    private Map<Integer, ShaderProgram> biomShaderPrograms = new HashMap<>();

    private void initShader() {
        cloudShader = Statics.asset.getShader(ShaderPrograms.CLOUD_SHADER);
        cloudBatch.setShader(cloudShader);
        cloudShader.begin();
        cloudShader.setUniformf("resolution", new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        cloudShader.setUniformf("cloudsize", 0.4f);
        cloudShader.end();

        waterShader = Statics.asset.getShader(ShaderPrograms.WATER_SHADER);
        waterBatch.setShader(waterShader);


        biomShaderPrograms.put(ChunkBuffer.PURPLE_BIOM, Statics.asset.getShader(ShaderPrograms.BLOOM_SHADER));
        biomShaderPrograms.put(ChunkBuffer.YELLOW_BIOM, Statics.asset.getShader(ShaderPrograms.PASSTHROUGH_SHADER));
        biomShaderPrograms.put(ChunkBuffer.NORMAL_BIOM, Statics.asset.getShader(ShaderPrograms.PASSTHROUGH_SHADER));
        biomShaderPrograms.put(ChunkBuffer.GREEN_BIOM, Statics.asset.getShader(ShaderPrograms.PASSTHROUGH_SHADER));
        biomShaderPrograms.put(ChunkBuffer.BLUE_BIOM, Statics.asset.getShader(ShaderPrograms.PASSTHROUGH_SHADER));
        biomShaderPrograms.put(ChunkBuffer.RED_BIOM, Statics.asset.getShader(ShaderPrograms.PASSTHROUGH_SHADER));


        postShaderOld = biomShaderPrograms.get(ChunkBuffer.NORMAL_BIOM);
        postShaderNew = biomShaderPrograms.get(ChunkBuffer.NORMAL_BIOM);
        postShaderOld.begin();
        postShaderOld.setUniformf("intensity", 0);
        postShaderOld.end();

        postShaderNew.begin();
        postShaderNew.setUniformf("intensity", 1);
        postShaderNew.end();
        postProcessingBatch1.setShader(postShaderOld);
        postProcessingBatch2.setShader(postShaderNew);
//        quadMesh = Utils.createFullscreenQuad();

    }

    private float angleWaveSpeed = 2.5f;
    private float amplitudeWave = 2;
    private float angleWave = 0;
    private Vector2 windVelocity = new Vector2(0.0005f, 0f);
    private Vector2 windData = new Vector2(0, 0);

    private Batch postProcessingBatch1 = new SpriteBatch();
    private Batch postProcessingBatch2 = new SpriteBatch();


    private float intensityOld = 0;
    private float intensityNew = 0;
    private float fadeSpeed = 1/4f; // 1/1 = 1 second; 1/2 = 2 seconds;

    private void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (!pause) {
                pause = true;
                MainGame.game.setScreen(new PauseScreen(MainGame.game.getScreen()));
            }
        }
    }



    public void render(float delta) {
        if (!pause) {
            updateAmbientSound();
            checkWin();
            update(delta);

            fadingDone = true;
            if(intensityOld > 0) {
                intensityOld -= (delta*fadeSpeed);
                fadingDone = false;
            }

            if(intensityNew < 1) {
                intensityNew += (delta*fadeSpeed);
                fadingDone = false;
            }


            postShaderOld.begin();
            postShaderOld.setUniformf("intensity", intensityOld);
            postShaderOld.end();

            postShaderNew.begin();
            postShaderNew.setUniformf("intensity", intensityNew);
            postShaderNew.end();


            buffer1.begin();
            {
                //Clear buffer1
                Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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


                tiledMapRenderer.setBatch(waterBatch);
                tiledMapRenderer.setView(camera);
                tiledMapRenderer.render(fluidLayer);

                //Render map
                tiledMapRenderer.setBatch(mapBatch);
                tiledMapRenderer.setView(camera);
                tiledMapRenderer.render(foregroundLayers);

                //Process entities
                Statics.ashley.update(Gdx.graphics.getDeltaTime());

                windData.add(windVelocity);

                cloudShader.begin();
                cloudShader.setUniformf("waveData", angleWave, amplitudeWave);
                cloudShader.setUniformf("camPosition", camera.position);
                cloudShader.setUniformf("scroll", windData);
                cloudShader.end();


                cloudBatch.setBlendFunction(Gdx.gl.GL_DST_COLOR, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
                cloudBatch.begin();
                cloudBatch.draw(clouds, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                cloudBatch.end();


                for (AbstractOverlay overlay : overlays) {
                    if((overlay instanceof ParticleOverlay) || (overlay instanceof LaserOverlay)) {
                        if (overlay.isVisible()) {
                            overlay.render();
                        }
                    }
                }
            }
            buffer1.end();

            buffer2.begin();
            {
                postProcessingBatch1.begin();
                postProcessingBatch1.draw(buffer1.getColorBufferTexture(),
                        0, 0,
                        buffer1.getColorBufferTexture().getWidth(), buffer1.getColorBufferTexture().getHeight(),
                        0, 0,
                        buffer1.getColorBufferTexture().getWidth(), buffer1.getColorBufferTexture().getHeight(),
                        false, true);

                postProcessingBatch1.end();
            }
            buffer2.end();

//        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            postProcessingBatch2.begin();
            postProcessingBatch2.draw(buffer2.getColorBufferTexture(),
                    0, 0,
                    buffer2.getColorBufferTexture().getWidth(), buffer2.getColorBufferTexture().getHeight(),
                    0, 0,
                    buffer2.getColorBufferTexture().getWidth(), buffer2.getColorBufferTexture().getHeight(),
                    false, true);

            postProcessingBatch2.end();

            //Render Overlays
            for (AbstractOverlay overlay : overlays) {
                if (overlay.isVisible() && !(overlay instanceof ParticleOverlay) && !(overlay instanceof LaserOverlay)) {
                    overlay.render();
                }
            }
        }

    }

    private void checkWin() {
        if(Statics.stats.bottleBlue > 0 &&
            Statics.stats.bottleGreen > 0 &&
            Statics.stats.bottlePurple > 0 &&
            Statics.stats.bottleRed > 0 &&
            Statics.stats.bottleYellow > 0){

            GameOverlay.addTextbox("Congratulations!", 1.5f);
            GameOverlay.addTextbox("You obtained all needed resources to power your ship again and leave this hostile planet.", 10);
        }
    }

    float lastSound = -20;
    float nextSound = 20;
    private void updateAmbientSound() {
        if(Statics.time - lastSound > nextSound){
            lastSound = Statics.time;
            nextSound = MathUtils.random(20, 40);

            switch(MathUtils.random(0,2)){
                case 0:
                    Statics.sound.playSound(Sounds.AMBIENT);
                    break;
                case 1:
                    Statics.sound.playSound(Sounds.AMBIENT_HORROR);
                    break;
                case 2:
                    Statics.sound.playSound(Sounds.AMBIENT_LAVA);
                    break;
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
        buffer1.dispose();
        buffer2.dispose();
        cloudBatch.dispose();
        waterBatch.dispose();
        postProcessingBatch1.dispose();
        postProcessingBatch2.dispose();
        waterShader.dispose();
//        ambient.stop();
//        ambient.dispose();

        for (AbstractOverlay overlay : overlays) {
            overlay.dispose();
        }
    }

}
