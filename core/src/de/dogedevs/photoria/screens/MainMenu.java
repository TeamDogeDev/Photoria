package de.dogedevs.photoria.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.dogedevs.photoria.MainGame;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.utils.assets.enums.Musics;
import de.dogedevs.photoria.utils.assets.enums.ShaderPrograms;
import de.dogedevs.photoria.utils.assets.enums.Textures;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class MainMenu implements Screen {

    private SpriteBatch spriteBatch, starfieldBatch;
    private Texture title = Statics.asset.getTexture(Textures.TITLE);

    private Skin uiSkin;
    private Stage stage;
    private Table table;

    private TextButton startBtn, quitBtn;
    private Music music;

    private ShaderProgram shader;

    public MainMenu() {
        Statics.music.playMusic(Musics.TITLE, true);
        Statics.music.stopMusic();
        spriteBatch = new SpriteBatch();
        starfieldBatch = new SpriteBatch();

        shader = Statics.asset.getShader(ShaderPrograms.STARFIELD_SHADER);
        starfieldBatch.setShader(shader);

        shader.begin();
        shader.setUniformf("resolution", new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        shader.end();
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

    float sfTime;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sfTime += delta;
        shader.begin();
//        shader.setUniformf("starRadius", 0.4f);
//        shader.setUniformf("starDensity", 5f);
//        shader.setUniformf("starColor", new Vector3(1, 1, 1));
//        shader.setUniformf("speed", 1f);
        shader.setUniformf("time", sfTime);
        shader.end();

        starfieldBatch.begin();
        starfieldBatch.draw(title, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        starfieldBatch.end();

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
//        stage.dispose();
        spriteBatch.dispose();
        starfieldBatch.dispose();
        title.dispose();
        uiSkin.dispose();
    }
}
