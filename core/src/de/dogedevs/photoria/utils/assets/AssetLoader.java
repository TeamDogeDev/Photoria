package de.dogedevs.photoria.utils.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import de.dogedevs.photoria.MainGame;
import de.dogedevs.photoria.utils.assets.enums.*;

/**
 * Created by elektropapst on 03.01.2016.
 */
public class AssetLoader {

    private AssetManager manager = new AssetManager();

    public AssetLoader() {
        loadTextures();
        loadParticles();
        loadMusics();
        loadSounds();
        loadBitmapFonts();

//        while(!manager.update()) {
//            System.out.println("Loading: " + manager.getProgress());
//        }
//        manager.finishLoading();
//        MainGame.log("Loaded Assets: " + manager.getLoadedAssets());
    }

    public boolean load() {
        return manager.update();
    }

    public float progress() {
        return manager.getProgress();
    }

    private void loadParticles() {
        for(Particles particle : Particles.values()) {
            ParticleEffectLoader.ParticleEffectParameter params = new ParticleEffectLoader.ParticleEffectParameter();
            params.imagesDir = Gdx.files.internal(particle.imageDir);
            manager.load(particle.effectFile, ParticleEffect.class, params);
        }
    }

    private void loadTextures() {
        for(Textures tex : Textures.values()) {
            manager.load(tex.name, Texture.class);
        }
    }

    private void loadMusics() {
        for(Musics music : Musics.values()) {
            manager.load(music.name, Music.class);
        }
    }

    private void loadSounds() {
        for(Sounds sound : Sounds.values()) {
            manager.load(sound.name, Sound.class);
        }
    }

    private void loadBitmapFonts() {
        for(BitmapFonts font : BitmapFonts.values()) {
            manager.load(font.name, BitmapFont.class);
        }
    }

    public BitmapFont getBitmapFont(BitmapFonts font, boolean markupEnabled) {
        BitmapFont bitmapFont = manager.get(font.name, BitmapFont.class);
        bitmapFont.getData().markupEnabled = markupEnabled;
        return bitmapFont;
    }

    public ParticleEffect getParticleEffect(Particles effect) {
        return manager.get(effect.effectFile, ParticleEffect.class);
    }

    public Texture getTexture(Textures texture) {
        return manager.get(texture.name, Texture.class);
    }

    public Music getMusic(Musics music) {
        return manager.get(music.name, Music.class);
    }

    public Sound getSound(Sounds sound) {
        return manager.get(sound.name, Sound.class);
    }

    public ShaderProgram getShader(ShaderPrograms shaderProgram) {
        ShaderProgram.pedantic = false;
        ShaderProgram retVal = new ShaderProgram(Gdx.files.internal(shaderProgram.vertexShader),
                                                 Gdx.files.internal(shaderProgram.fragmentShader));
        MainGame.log(retVal.isCompiled() ? shaderProgram.name() + " compiled." : retVal.getLog());
        return retVal;
    }

    public void dispose() {
        manager.clear();
    }

}
