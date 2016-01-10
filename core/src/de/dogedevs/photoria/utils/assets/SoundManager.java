package de.dogedevs.photoria.utils.assets;

import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.utils.assets.enums.Sounds;

/**
 * Created by elektropapst on 06.01.2016.
 */
public class SoundManager {


    public void stopSound(Sounds sound, long id) {
        Statics.asset.getSound(sound).stop(id);
    }

    public long loopSound(Sounds sound) {
        return loopSound(sound, 1f);
    }

    public long loopSound(Sounds sound, float volume) {
        return Statics.asset.getSound(sound).loop(volume);
    }

    public long playSound(Sounds sound) {
        return playSound(sound, 1f);
    }

    public long playSound(Sounds sound, float volume) {
         return Statics.asset.getSound(sound).play(volume);
    }
}
