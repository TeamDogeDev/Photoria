package de.dogedevs.photoria;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import de.dogedevs.photoria.rendering.MapBuilder;
import de.dogedevs.photoria.screens.MainScreen;

import java.util.Random;

public class MainGame extends Game {

    Screen currentScreen;

    private Random random;
    public static long GAME_SEED;

    @Override
    public void create() {
        random = new Random();
        GAME_SEED = random.nextLong();
        currentScreen = new MainScreen();
        this.setScreen(currentScreen);
    }

    @Override
    public void render() {
        super.render(); //important!
    }

}
