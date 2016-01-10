package de.dogedevs.photoria.utils.assets;

import com.badlogic.gdx.audio.Music;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.utils.assets.enums.Musics;

/**
 * Created by elektropapst on 06.01.2016.
 */
public class MusicManager {

    private Music currentMusic = null;

    private void loadMusic(Musics music) {
        currentMusic = Statics.asset.getMusic(music);
    }

    public void pauseMusic() {
        if(currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.pause();
        }
    }

    public void resumeMusic() {
        if(currentMusic != null && !currentMusic.isPlaying()) {
            currentMusic.play();
        }
    }

    public void stopMusic() {
        if(currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.stop();
            currentMusic = null;
        }
    }

    public void setMusicVolume(float volume) {
        if(currentMusic != null) {
            currentMusic.setVolume(volume);
        }
    }

    public void playMusic(Musics music, boolean looping) {
        if(currentMusic != null && currentMusic.isPlaying()) {
            stopMusic();
        }
        loadMusic(music);
        currentMusic.setLooping(looping);
        currentMusic.play();
    }

}
