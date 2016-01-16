package de.dogedevs.photoria.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.dogedevs.photoria.MainGame;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.screens.actors.MenuButton;
import de.dogedevs.photoria.utils.Utils;
import de.dogedevs.photoria.utils.assets.enums.BitmapFonts;
import de.dogedevs.photoria.utils.assets.enums.Sounds;
import de.dogedevs.photoria.utils.assets.enums.Textures;

import static de.dogedevs.photoria.Statics.asset;
import static de.dogedevs.photoria.Statics.settings;
import static de.dogedevs.photoria.Statics.sound;

/**
 * Created by elektropapst on 11.01.2016.
 */
public class MainMenuScreen implements Screen {

    private Batch mainBatch;
    private BitmapFont menuFont;
    private Texture box, logoInner, logoOuter, volumeBox, volumeBoxBar, cursorTex, input;
    private float rot, offset, spacing, startX, startY, rightAligned;
    private Stage stage;
    private MenuButton start, quit, sndPlus, sndMinus, musPlus, musMinus;
    private int soundRow = 2;
    private int musicRow = 3;
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
        volumeBox = asset.getTexture(Textures.MENU_VOLUME_BOX);
        volumeBoxBar = asset.getTexture(Textures.MENU_VOLUME_BOX_BAR);
        cursorTex = asset.getTexture(Textures.MOUSE_CURSOR);
        input = asset.getTexture(Textures.MENU_INPUT);
        offset = 50;
        spacing = 50;
        startX = offset;
        startY = Gdx.graphics.getHeight() - offset - Statics.asset.getTexture(Textures.MENU_BOX).getHeight();
        rightAligned = Statics.asset.getTexture(Textures.MENU_BOX).getWidth()-Statics.asset.getTexture(Textures.MENU_BOX_SMALL).getWidth();

    }

    private void initUI() {
        stage = new Stage(new ScreenViewport());

        start = new MenuButton("Start", startX, startY, menuFont, Color.BLACK, Color.WHITE, MenuButton.ButtonType.NORMAL);
        quit = new MenuButton("Quit", startX, startY - (1 * spacing), menuFont, Color.BLACK, Color.WHITE, MenuButton.ButtonType.NORMAL);

        sndMinus = new MenuButton("-",
                startX, startY - ((soundRow+1) * spacing),
                menuFont, Color.BLACK, Color.RED, MenuButton.ButtonType.SMALL);

        sndPlus = new MenuButton("+",
                startX + rightAligned, startY - ((soundRow+1) * spacing),
                menuFont, Color.BLACK, Color.LIME, MenuButton.ButtonType.SMALL);

        musMinus = new MenuButton("-",
                startX, startY - ((musicRow+2) * spacing),
                menuFont, Color.BLACK, Color.RED, MenuButton.ButtonType.SMALL);

        musPlus = new MenuButton("+",
                startX + rightAligned, startY - ((musicRow+2) * spacing),
                menuFont, Color.BLACK, Color.LIME, MenuButton.ButtonType.SMALL);

        stage.addActor(start);
        stage.addActor(quit);
        stage.addActor(sndMinus);
        stage.addActor(sndPlus);
        stage.addActor(musMinus);
        stage.addActor(musPlus);

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
        final float sens = .1f;
        sndMinus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settings.soundVolume -= sens;
                settings.soundVolume = MathUtils.clamp(settings.soundVolume, 0, 1);
                sound.playSound(Sounds.MOB_HIT);
            }
        });

        sndPlus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settings.soundVolume += sens;
                settings.soundVolume = MathUtils.clamp(settings.soundVolume, 0, 1);
                sound.playSound(Sounds.MOB_HIT);
            }
        });

        musMinus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settings.musicVolume -= sens;
                settings.musicVolume = MathUtils.clamp(settings.musicVolume, 0, 1);
                sound.playSound(Sounds.MOB_DIE, settings.musicVolume);
            }
        });

        musPlus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settings.musicVolume += sens;
                settings.musicVolume = MathUtils.clamp(settings.musicVolume, 0, 1);
                sound.playSound(Sounds.MOB_DIE, settings.musicVolume);
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
        mainBatch.begin();
        menuFont.draw(mainBatch, "Sound:", startX, startY - (soundRow * spacing) + menuFont.getLineHeight(),
                asset.getTexture(Textures.MENU_BOX).getWidth(), Align.left, false);

        menuFont.draw(mainBatch, "Music:", startX, startY - ((musicRow+1) * spacing) + menuFont.getLineHeight(),
                asset.getTexture(Textures.MENU_BOX).getWidth(), Align.left, false);
        renderVolumeBox(3, Statics.settings.soundVolume);
        renderVolumeBox(5, Statics.settings.musicVolume);
        renderLogo(delta, Gdx.graphics.getWidth() - logoOuter.getWidth() - offset, Gdx.graphics.getHeight() - logoOuter.getHeight() - offset);

        renderInput(delta, startX + (2*spacing) + asset.getTexture(Textures.MENU_BOX).getWidth(), -1); // q'n'd
        renderCursor(delta);
        mainBatch.end();
    }

    private void renderInput(float delta, float x, float row) {
        float y = startY - ((row+1) * spacing) - input.getHeight();
        menuFont.draw(mainBatch, "Controls:", x, startY - ((row+1) * spacing) + menuFont.getLineHeight(),
                asset.getTexture(Textures.MENU_BOX).getWidth(), Align.left, false);
        mainBatch.draw(input, x, y);
        float textX = x + input.getWidth() + (spacing/2);
        menuFont.draw(mainBatch, "- Use", textX, y+input.getHeight()-10);
        menuFont.draw(mainBatch, "- Move", textX, y+(input.getHeight()/2));
        menuFont.draw(mainBatch, "- Shoot", textX, y+menuFont.getLineHeight()-10);
    }

    private void renderVolumeBox(int row, float value) {

        mainBatch.draw(volumeBoxBar,  startX+asset.getTexture(Textures.MENU_BOX_SMALL).getWidth()+12, startY - (row * spacing),
        volumeBox.getWidth()*value, volumeBox.getHeight());

        mainBatch.draw(volumeBox, startX+asset.getTexture(Textures.MENU_BOX_SMALL).getWidth()+12, startY - (row * spacing));

    }

    private void renderLogo(float delta, float logoX, float logoY) {
        mainBatch.draw(logoInner, logoX, logoY);
        mainBatch.draw(logoOuter,
                logoX, logoY,
                logoOuter.getWidth() / 2, logoOuter.getHeight() / 2,
                logoOuter.getWidth(), logoOuter.getHeight(),
                1, 1,
                rot,
                0, 0, logoOuter.getWidth(), logoOuter.getHeight(), false, false);
    }

    private void renderCursor(float delta) {
        Color oldColor = mainBatch.getColor();
        mainBatch.setColor(Color.BLACK);
        mainBatch.draw(cursorTex,
                Gdx.input.getX() - (cursorTex.getWidth() / 2),
                Gdx.graphics.getHeight() - Gdx.input.getY() - (cursorTex.getHeight() / 2));
        mainBatch.setColor(oldColor);
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
        volumeBoxBar.dispose();
        volumeBox.dispose();
        cursorTex.dispose();
        input.dispose();
    }
}
