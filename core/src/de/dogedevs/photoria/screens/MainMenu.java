package de.dogedevs.photoria.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.dogedevs.photoria.MainGame;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class MainMenu implements Screen {

    private SpriteBatch spriteBatch;
    private Texture title = new Texture(Gdx.files.internal("./title.png"));

    private Skin uiSkin;
    private Stage stage;
    private Table table;

    private TextButton startBtn, quitBtn;
    private Music music;

    private ShaderProgram shader;

    public MainMenu() {
        music = Gdx.audio.newMusic(Gdx.files.internal("./music/title.mp3"));
        music.setLooping(true);
        music.play();
        spriteBatch = new SpriteBatch();

        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("./shaders/starfield.vsh"), Gdx.files.internal("./shaders/starfield.fsh"));

        System.out.println(shader.isCompiled() ? "Starfield shader compiled" : shader.getLog());
        spriteBatch.setShader(shader);

        uiSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setWidth(2000);
        table.setFillParent(true);
        stage.addActor(table);
        startBtn = new TextButton("Start", uiSkin);
        quitBtn = new TextButton("Quit", uiSkin);

        startBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                MainGame.game.setScreen(new GameScreen());
            }
        });

        quitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.padTop(Gdx.graphics.getHeight() / 2);
        table.add(startBtn).padBottom(30).row();
        table.add(quitBtn).padBottom(30);
        table.setDebug(true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.2f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shader.begin();
        shader.setUniformf("resolution", new Vector2(10, 10));
        shader.setUniformf("starRadius", 0.4f);
        shader.setUniformf("starDensity", 5f);
        shader.setUniformf("starColor", new Vector3(1, 1, 1));
        shader.setUniformf("speed", 1f);
        shader.setUniformf("time", delta);
        shader.end();
        spriteBatch.begin();
        spriteBatch.draw(title, (Gdx.graphics.getWidth() - title.getWidth()) / 2, Gdx.graphics.getHeight() - title.getHeight());
        spriteBatch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        music.dispose();
        stage.dispose();
        spriteBatch.dispose();
        title.dispose();
        uiSkin.dispose();

    }
}
