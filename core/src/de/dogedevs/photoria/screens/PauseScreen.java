package de.dogedevs.photoria.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.dogedevs.photoria.MainGame;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.screens.actors.MenuButton;
import de.dogedevs.photoria.utils.Utils;
import de.dogedevs.photoria.utils.assets.enums.BitmapFonts;
import de.dogedevs.photoria.utils.assets.enums.Textures;

/**
 * Created by elektropapst on 15.01.2016.
 */
public class PauseScreen implements Screen {

    private Screen returnScreen;
    private Batch mainBatch;
    private BitmapFont menuFont;
    private MenuButton resume, quit;
    private Stage stage;
    private float offset, spacing, startX, startY;
    private Texture cursorTex;

    public PauseScreen(Screen screen) {
        returnScreen = screen;
        init();
        initUI();
        initListener();
    }

    private void initListener() {
        resume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                back();
            }
        });
        quit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.exit();
            }
        });
    }

    private void init() {
        mainBatch = new SpriteBatch();
        menuFont = Statics.asset.getBitmapFont(BitmapFonts.MENU_FONT, false);

        cursorTex = Statics.asset.getTexture(Textures.MOUSE_CURSOR);

        offset = 50;
        spacing = 50;
        startX = offset;
        startY = Gdx.graphics.getHeight() - offset - Statics.asset.getTexture(Textures.MENU_BOX).getHeight();
    }

    private void initUI() {
        stage = new Stage(new ScreenViewport());

        resume = new MenuButton("Resume", startX, startY, menuFont, Color.BLACK, Color.WHITE, MenuButton.ButtonType.NORMAL);
        quit = new MenuButton("Quit", startX, startY - (1 * spacing), menuFont, Color.BLACK, Color.WHITE, MenuButton.ButtonType.NORMAL);

        stage.addActor(resume);
        stage.addActor(quit);
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(191/255f, 191/255f, 191/255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        mainBatch.begin();
//        renderStats();
        renderCursor(delta);
        mainBatch.end();
    }

    private void renderStats() {
        menuFont.draw(mainBatch, "Killed enemies: " + Statics.stats.killedEnemies,
        offset, Gdx.graphics.getHeight() - (3 * offset));
    }


    private void renderCursor(float delta) {
        Color oldColor = mainBatch.getColor();
        mainBatch.setColor(Color.BLACK);
        mainBatch.draw(cursorTex,
                Gdx.input.getX() - (cursorTex.getWidth() / 2),
                Gdx.graphics.getHeight() - Gdx.input.getY() - (cursorTex.getHeight() / 2));
        mainBatch.setColor(oldColor);
    }

    private void update(float delta) {
        Utils.lockMouseToScreen();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            back();
        }
        stage.act(delta);
    }

    private void back() {
        MainGame.game.setScreen(returnScreen);
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
        menuFont.dispose();
        stage.dispose();
        mainBatch.dispose();
        cursorTex.dispose();
    }
}
