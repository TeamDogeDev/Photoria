package de.dogedevs.photoria.utils.assets;

import com.badlogic.gdx.audio.Music;
import de.dogedevs.photoria.utils.assets.enums.Musics;

/**
 * Created by elektropapst on 06.01.2016.
 */
public class MusicManager {

    private static Music currentMusic = null;

    private static void loadMusic(Musics music) {
        currentMusic = AssetLoader.getMusic(music);
    }

    public static void pauseMusic() {
        if(currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.pause();
        }
    }

    public static void resumeMusic() {
        if(currentMusic != null && !currentMusic.isPlaying()) {
            currentMusic.play();
        }
    }

    public static void stopMusic() {
        if(currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.stop();
            currentMusic = null;
        }
    }

    public static void setMusicVolume(float volume) {
        if(currentMusic != null) {
            currentMusic.setVolume(volume);
        }
    }

    public static void playMusic(Musics music, boolean looping) {
        if(currentMusic != null && currentMusic.isPlaying()) {
            stopMusic();
        }
        loadMusic(music);
        currentMusic.setLooping(looping);
        currentMusic.play();
    }

}
