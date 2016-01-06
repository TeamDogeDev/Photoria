package de.dogedevs.photoria.utils.assets.enums;

/**
 * Created by elektropapst on 06.01.2016.
 */
public enum Particles {
    BLOOD_PARTICLE("effects/blood.p", "effects/images"),
    FIRE_PARTICLE("effects/fire.p", "effects/images");

    public String effectFile;
    public String imageDir;
    Particles(String effectFile, String imageDir) {
        this.effectFile = effectFile;
        this.imageDir = imageDir;
    }

}