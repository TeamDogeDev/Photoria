package de.dogedevs.photoria.utils.assets;

import de.dogedevs.photoria.utils.assets.enums.Sounds;

/**
 * Created by elektropapst on 06.01.2016.
 */
public class SoundManager {


    public static void stopSound(Sounds sound, long id) {
        AssetLoader.getSound(sound).stop(id);
    }

    public static long loopSound(Sounds sound) {
        return loopSound(sound, 1f);
    }

    public static long loopSound(Sounds sound, float volume) {
        return AssetLoader.getSound(sound).loop(volume);
    }

    public static long playSound(Sounds sound) {
        return playSound(sound, 1f);
    }

    public static long playSound(Sounds sound, float volume) {
         return AssetLoader.getSound(sound).play(volume);
    }
}
