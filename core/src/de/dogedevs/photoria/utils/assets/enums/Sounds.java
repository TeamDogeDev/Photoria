package de.dogedevs.photoria.utils.assets.enums;

/**
 * Created by elektropapst on 06.01.2016.
 */
public enum Sounds {
    MOB_HIT("sounds/mobHit.wav"),
    MOB_DIE("sounds/mobDie.wav"),
    PLAYER_HIT("sounds/hit.wav"),
    PLAYER_DIE("sounds/hit.wav"), // TODO REPLACE SOUND FILES

    LASER_LOOP("sfx/laser00.mp3"),
    BIOM_ENTER("sfx/biomeEnter.mp3"),
    BOSS_SCREAM("sfx/boss.mp3"),
    AMBIENT_BUBBLES("sfx/ambientAcid01.mp3"),
    AMBIENT_HORROR("sfx/horrorAmbience.mp3"),
    AMBIENT_LAVA("sfx/lavaLoop.mp3"),
    AMBIENT("sfx/ambient02.mp3"),
    ENERGYGUN("sfx/energygun.mp3"),
    FLAMETHROWER("sfx/flamethrower_loop.mp3"),
    WATERGUN("sfx/waterCannon.mp3"),
    EYE_SHOT("sfx/eye_shot.mp3"),
    EYE_DEATH("sfx/eye_death.mp3"),
    SLIME_JUMP("sfx/slimeJump.mp3"),
    SLIME_DEATH("sfx/slime_death.mp3"),
    SLIME_MOVEMENT("sfx/slimeAttackMovement.mp3"),
    PLAYER_HIT1("sfx/hit1.mp3"),
    PLAYER_HIT2("sfx/hit2.mp3"),
    PLAYER_HIT3("sfx/hit3.mp3");

    public String name;

    Sounds(String name) {
        this.name = name;
    }

}
