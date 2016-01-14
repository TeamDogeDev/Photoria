package de.dogedevs.photoria;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.dogedevs.photoria.screens.GameScreen;
import de.dogedevs.photoria.screens.MainMenuScreen;

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

    private int y = 40;
    private float a = 1;

    public MainGame() {
    }

    @Override
    public void create() {
        game = this;
        logs = new LinkedList<>();
        batch = new SpriteBatch();
        Statics.initCat();

        random = new Random();
        GAME_SEED = random.nextLong();

        currentScreen = new GameScreen();
        currentScreen = new MainMenuScreen();
//        currentScreen = new MainMenu();
        this.setScreen(currentScreen);
        font = new BitmapFont();
        Gdx.input.setCursorCatched(true);

    }

    @Override
    public void render() {
        super.render(); //important!
        batch.begin();
        font.setColor(1,1,1,1);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20); // in debug overlay (!)
        y = 40;
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

    @Override
    public void dispose() {
        super.dispose();
        currentScreen.dispose();
    }
}
