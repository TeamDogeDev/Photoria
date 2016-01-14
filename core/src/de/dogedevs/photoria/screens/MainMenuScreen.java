package de.dogedevs.photoria.screens;

import com.badlogic.gdx.Gdx;
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
import de.dogedevs.photoria.screens.actors.MenuItem;
import de.dogedevs.photoria.utils.Utils;
import de.dogedevs.photoria.utils.assets.enums.BitmapFonts;
import de.dogedevs.photoria.utils.assets.enums.Textures;

import static de.dogedevs.photoria.Statics.asset;

/**
 * Created by elektropapst on 11.01.2016.
 */
public class MainMenuScreen implements Screen {

    private Batch mainBatch;
    private BitmapFont menuFont;
    private Texture box, logoInner, logoOuter;
    private float rot, offset;
    private Stage stage;
    private MenuItem start, options, quit;

    public MainMenuScreen() {
        init();
        initUI();
    }

    private void init() {
        mainBatch = new SpriteBatch();
        menuFont = asset.getBitmapFont(BitmapFonts.MENU_FONT, false);
        box = asset.getTexture(Textures.MENU_BOX);
        logoOuter = asset.getTexture(Textures.MENU_LOGO_OUTER);
        logoInner = asset.getTexture(Textures.MENU_LOGO_INNER);
        offset = 50;
    }

    private void initUI() {
        stage = new Stage(new ScreenViewport());

        float spacing = 50;

        float startX = offset;
        float startY = Gdx.graphics.getHeight() - offset - Statics.asset.getTexture(Textures.MENU_BOX).getHeight();

        start = new MenuItem("Start", startX, startY, menuFont, Color.BLACK, Color.DARK_GRAY);
        quit = new MenuItem("Quit", startX, startY - (1 * spacing), menuFont, Color.BLACK, Color.DARK_GRAY);

        stage.addActor(start);
        stage.addActor(quit);
        initListener();
        Gdx.input.setInputProcessor(stage);
    }

    private void initListener() {
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                MainGame.game.setScreen(new GameScreen());
            }
        });

        quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void show() {
    }

    private void update(float delta) {
        Utils.lockMouseToScreen();
        rot += delta * 8;
        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(191 / 255f, 191 / 255f, 191 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        renderLogo(delta, Gdx.graphics.getWidth() - logoOuter.getWidth()-offset, Gdx.graphics.getHeight() - logoOuter.getHeight()-offset);
        renderCursor(delta);
    }

    private void renderLogo(float delta, float logoX, float logoY) {
        mainBatch.begin();
        mainBatch.draw(logoInner, logoX, logoY);
        mainBatch.draw(logoOuter,
                logoX, logoY,
                logoOuter.getWidth() / 2, logoOuter.getHeight() / 2,
                logoOuter.getWidth(), logoOuter.getHeight(),
                1, 1,
                rot,
                0, 0, logoOuter.getWidth(), logoOuter.getHeight(), false, false);
        mainBatch.end();
    }

    private void renderCursor(float delta) {
        Texture cursorTex = asset.getTexture(Textures.MOUSE_CURSOR);
        mainBatch.begin();
        mainBatch.setColor(Color.BLACK);
        mainBatch.draw(cursorTex,
                Gdx.input.getX() - (cursorTex.getWidth() / 2),
                Gdx.graphics.getHeight() - Gdx.input.getY() - (cursorTex.getHeight() / 2));
        mainBatch.end();
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
        box.dispose();
        logoOuter.dispose();
        menuFont.dispose();
        mainBatch.dispose();
        stage.dispose();
    }
}
