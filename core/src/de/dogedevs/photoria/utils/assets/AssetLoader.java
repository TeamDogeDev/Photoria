package de.dogedevs.photoria.utils.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by elektropapst on 03.01.2016.
 */
public class AssetLoader {

    static AssetManager manager = new AssetManager();

    public AssetLoader() {
        loadTextures();
        manager.finishLoading();
    }

    private void loadTextures() {
        for(Textures tex : Textures.values()) {
            manager.load(tex.name, Texture.class);
        }
    }

    public static Texture getTexture(Textures texture) {
        return manager.get(texture.name, Texture.class);
    }

    public void dispose() {
        manager.clear();
    }

}
