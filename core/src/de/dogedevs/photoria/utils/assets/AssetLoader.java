package de.dogedevs.photoria.utils.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import de.dogedevs.photoria.MainGame;
import de.dogedevs.photoria.utils.assets.enums.Particles;
import de.dogedevs.photoria.utils.assets.enums.ShaderPrograms;
import de.dogedevs.photoria.utils.assets.enums.Textures;

/**
 * Created by elektropapst on 03.01.2016.
 */
public class AssetLoader {

    static AssetManager manager = new AssetManager();

    public AssetLoader() {
        loadTextures();
        loadParticles();
        manager.finishLoading();
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

    public static ParticleEffect getParticleEffect(Particles effect) {
        return manager.get(effect.effectFile, ParticleEffect.class);
    }

    public static Texture getTexture(Textures texture) {
        return manager.get(texture.name, Texture.class);
    }

    public static ShaderProgram getShader(ShaderPrograms shaderProgram) {
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
