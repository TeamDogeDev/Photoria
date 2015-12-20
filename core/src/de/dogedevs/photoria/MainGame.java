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


import java.util.LinkedList;
import java.util.Random;


public class MainGame extends Game {

    static public MainGame game;
    private SpriteBatch batch;

    private Screen currentScreen;
    private BitmapFont font;

    private LinkedList<String> logs;

    private Random random;
    public static long GAME_SEED;

    @Override
    public void create() {

        game = this;
        logs = new LinkedList<>();
        batch = new SpriteBatch();

        random = new Random();
        GAME_SEED = random.nextLong();

        currentScreen = new MainScreen();
        this.setScreen(currentScreen);
        font = new BitmapFont();
    }
    int y = 40;
    float a = 1;
    @Override
    public void render() {
        super.render(); //important!
        batch.begin();
        font.setColor(1,1,1,0);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        y = 20;
        a = 1;
        for(String text: logs){
            font.draw(batch, text, 10, y);
            font.setColor(1,1,1,a);
            y += 20;
            a -= 0.14f;
        }
        batch.end();
    }

    private void logString(String text){
        logs.addFirst(text);
        if(logs.size() >  9){
            logs.removeLast();
        }
    }

    public static void log(String text){
        System.out.println(text);
        game.logString(text);
    }
}
