package de.dogedevs.photoria.utils.assets.enums;

/**
 * Created by elektropapst on 06.01.2016.
 */
public enum Sounds {
    MOB_HIT("sounds/mobHit.wav"),
    MOB_DIE("sounds/mobDie.wav"),
    PLAYER_HIT("sounds/hit.wav"),
    PLAYER_DIE("sounds/hit.wav"), // TODO REPLACE SOUND FILES
    LASER("sfx/laser00.mp3"),
    BIOM_ENTER("sfx/biomeEnter.mp3");

    public String name;

    Sounds(String name) {
        this.name = name;
    }

}
