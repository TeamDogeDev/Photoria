package de.dogedevs.photoria.utils.assets.enums;

/**
 * Created by elektropapst on 06.01.2016.
 */
public enum Particles {
    BLOOD_PARTICLE("effects/blood.p", "effects/images"),
    FIRE_PARTICLE("effects/fire.p", "effects/images"),
    FLAME_THROWER("effects/flameThrower.p", "effects/images"),
    WATER_THROWER("effects/waterThrower.p", "effects/images"),
    ENERGY_BALL("effects/energyBall.p", "effects/images"),
    SLIME_SPLASH("effects/slimeSplash.p", "effects/images"),
    TERRAFORMING("effects/terraforming.p", "effects/images");

    public String effectFile;
    public String imageDir;
    Particles(String effectFile, String imageDir) {
        this.effectFile = effectFile;
        this.imageDir = imageDir;
    }

}
