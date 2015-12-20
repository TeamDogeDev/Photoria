package de.dogedevs.photoria.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sun.javaws.Main;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class MainMenu implements Screen {

    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private GlyphLayout gl = new GlyphLayout();

    public MainMenu() {
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.YELLOW);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.2f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        String s = "Photoria";
        gl.setText(font, s);
        font.draw(spriteBatch, s, (Gdx.graphics.getWidth()/2)-(gl.width/2), Gdx.graphics.getHeight()-100);

        spriteBatch.end();

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
}
