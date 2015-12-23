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
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import de.dogedevs.photoria.MainGame;
import de.dogedevs.photoria.model.entity.components.AnimationComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.entity.components.VelocityComponent;
import de.dogedevs.photoria.model.entity.systems.*;
import de.dogedevs.photoria.model.map.OffsetHolder;
import de.dogedevs.photoria.rendering.CustomTiledMapRenderer;
import de.dogedevs.photoria.rendering.MapBuilder;

import java.text.DecimalFormat;

/**
 * Created by Furuha on 20.12.2015.
 */
public class MainScreen implements Screen {

    static private PooledEngine ashley;

    Batch batch, waterBatch, mapBatch;
    MapBuilder mapBuilder;
    CustomTiledMapRenderer tiledMapRenderer;
    OrthographicCamera camera;
    private BitmapFont font;
    private DecimalFormat floatFormat = new DecimalFormat("#.##");

    private ShaderProgram shader;


    int[] fluidLayer = { 0 }; // don't allocate every frame!
    int[] foregroundLayers = { 1,2,3 };    // don't allocate every frame!

    public void show() {



        initCamera();
        getAshley(); //init ashley

        font = new BitmapFont();

        mapBuilder = new MapBuilder();
        tiledMapRenderer = new CustomTiledMapRenderer(mapBuilder.getTiledMap());
        mapBatch = tiledMapRenderer.getBatch();
        batch = new SpriteBatch();
        waterBatch = new SpriteBatch();

        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("./shaders/red.vsh"), Gdx.files.internal("./shaders/red.fsh"));
        System.out.println(shader.isCompiled() ? "Shader compiled" : shader.getLog());
        waterBatch.setShader(shader);

        tiledMapRenderer.setBatch(waterBatch);

        initTestEntitis();

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
                    mapBuilder.getTiledMap().getLayers().get(0).setVisible(!mapBuilder.getTiledMap().getLayers().get(1).isVisible());
                }
                if(keycode == Input.Keys.NUM_2){
                    mapBuilder.getTiledMap().getLayers().get(1).setVisible(!mapBuilder.getTiledMap().getLayers().get(2).isVisible());
                }
                if(keycode == Input.Keys.NUM_3){
                    mapBuilder.getTiledMap().getLayers().get(2).setVisible(!mapBuilder.getTiledMap().getLayers().get(3).isVisible());
                }
                return super.keyDown(keycode);
            }
        });
    }

    private void initCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.translate(300*64*32, 300*64*32);
        camera.zoom = 1;

        camera.update();
    }

    private void initTestEntitis() {
//        Texture img = new Texture("badlogic.jpg");
//        TextureRegion testRegion = new TextureRegion(img);
        getAshley().addSystem(new EntityDrawSystem(camera));
        getAshley().addSystem(new AnimatedEntityDrawSystem(camera));
        getAshley().addSystem(new MovingEntitySystem());
//        getAshley().addSystem(new CameraSystem(camera));
//        getAshley().addSystem(new FixFloatSystem(camera));


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

//        Entity eyeball = getAshley().createEntity();
//        eyeball.add(new PositionComponent(300 * 64 * 32, 300 * 64 * 32));
//        eyeball.add(new AnimationComponent(walkAnimationU));
//        eyeball.add(new VelocityComponent(0, 1));
//        getAshley().addEntity(eyeball);

        int max = 301;
        int min = 299;
        for (int i = 0; i < 400; i++) {
            Entity eyeball = getAshley().createEntity();
            eyeball.add(new PositionComponent(MathUtils.random(min*64*32, max*64*32), MathUtils.random(min*64*32, max*64*32)));
            AnimationComponent ac = new AnimationComponent(walkAnimationU);
            ac.leftAnimation = walkAnimationL;
            ac.rightAnimation = walkAnimationR;
            ac.upAnimation = walkAnimationU;
            ac.downAnimation = walkAnimationD;
            eyeball.add(ac);
            eyeball.add(new VelocityComponent(0, 20));
            getAshley().addEntity(eyeball);
        }

//        for (int i = 0; i < 30; i++) {
//            Entity coin = getAshley().createEntity();
//            coin.add(new PositionComponent(MathUtils.random(295*64*32, 305*64*32), MathUtils.random(295*64*32, 305*64*32)));
//            coin.add(new SpriteComponent(testRegion));
//            getAshley().addEntity(coin);
//        }
    }

    private float angleWaveSpeed = 5;
    private float amplitudeWave = 10;
    private float angleWave = 0;

    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batch.begin();
//        batch.draw(img, 0, 0);
//        batch.end();

        angleWave += delta * angleWaveSpeed;
        while(angleWave > Math.PI*2)
            angleWave -= Math.PI*2;

        shader.begin();
        shader.setUniformf("waveData", angleWave, amplitudeWave);
        shader.end();


        input();
        camera.update();
//        tiledMapRenderer.setView(camera);
//        tiledMapRenderer.render();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.setBatch(waterBatch);

//        waterBatch.begin();
        tiledMapRenderer.render(fluidLayer);
//        waterBatch.end();

//        tiledMapRenderer.setBatch(mapBatch);
//        mapBatch.begin();
        tiledMapRenderer.render(foregroundLayers);
//        mapBatch.end();

        ashley.update(Gdx.graphics.getDeltaTime());

        batch.begin();
        font.draw(batch, "cam x="+floatFormat.format(camera.position.x), 1070, 160, 200, Align.right, false);
        font.draw(batch, "cam y="+floatFormat.format(camera.position.y) , 1070, 140, 200, Align.right, false);
        font.draw(batch, "entities="+ashley.getEntities().size(), 1070, 120, 200, Align.right, false);
        font.draw(batch, "zoom="+camera.zoom, 1070, 100, 200, Align.right, false);
        font.draw(batch, "x="+Math.round((camera.position.x-OffsetHolder.offsetX)/32), 1070, 80, 200, Align.right, false);
        font.draw(batch, "y="+Math.round((camera.position.y-OffsetHolder.offsetY)/32) , 1070, 60, 200, Align.right, false);
        font.draw(batch, "chunk x="+Math.round((camera.position.x-OffsetHolder.offsetX)/32/64), 1070, 40, 200, Align.right, false);
        font.draw(batch, "chunk y="+Math.round((camera.position.y-OffsetHolder.offsetY)/32/64) , 1070, 20, 200, Align.right, false);
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

    public int getIdAtMouse() {
//        ((ChunkTileLayer)mapBuilder.getTiledMap().getLayers().get(1)).getCell(Gdx.input.getX(), Gdx.input.getY()).getTile().getId();
        try {
//            return ((ChunkTileLayer)mapBuilder.getTiledMap().getLayers().get(1)).getCell(Gdx.input.getX()/32, Gdx.input.getY()/32).getTile().toString();
            int x, y;
            if(Gdx.input.getY() < 720/2){
                y = (int) (((camera.position.y-720/2)+(Gdx.input.getY()))/32);
            } else {
                y = (int) (((camera.position.y-720/2)+(Gdx.input.getY()))/32);
            }

            if(Gdx.input.getX() < 1280/2){
                x = (int) (((camera.position.x-1280/2)+(Gdx.input.getX()))/32);
            } else {
                x = (int) (((camera.position.x-1280/2)+(Gdx.input.getX()))/32);
            }

            MainGame.log(x + " | " + y);
            return mapBuilder.getBuffer().getCell(x, y, 2).value;
        } catch (Exception e){
            return -1;
        }

    }
}
