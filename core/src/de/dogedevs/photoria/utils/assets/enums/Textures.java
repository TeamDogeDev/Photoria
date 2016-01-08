package de.dogedevs.photoria.utils.assets.enums;

/**
 * Created by elektropapst on 03.01.2016.
 */
public enum Textures {
    // TITLESCREEN
    TITLE("title.png"),

    // WORLD
    CLOUD_STUB("clouds.png"),
//    MAIN_TILESET("tilesets/tileset.png"),

//    TUNDRA_TILESET("tilesets/tileset_ice.png"),
//    GRASS_DESERT_TILESET("tilesets/tileset_accid.png"),
//    DESERT_TILESET("tilesets/tileset_ice.png"),
//    TAIGA_TILESET("tilesets/tileset.png"),
//    WOODS_TILESET("tilesets/tileset.png"),
//    SAVANNA_TILESET("tilesets/tileset.png"),
//    FOREST_TILESET("tilesets/tileset.png"),
//    SEASONAL_FOREST_TILESET("tilesets/tileset.png"),
//    SWAMP_TILESET("tilesets/tileset.png"),
//    RAIN_FOREST_TILESET("tilesets/tileset.png"),

    GREEN_TILESET("tilesets/tileset_acid.png"),
    PURPLE_TILESET("tilesets/tileset_tmpPurple.png"),
    YELLOW_TILESET("tilesets/tileset_tmpYellow.png"),
    BLUE_TILESET("tilesets/tileset_ice.png"),
    RED_TILESET("tilesets/tileset_tmpRed.png"),
    NORMAL_TILESET("tilesets/tileset.png"),


    // MOBS
    EYE("mobs/eyeball.png"),
    SLIME_BLUE("mobs/slime_blue.png"),
    SLIME_GREEN("mobs/slime_green.png"),
    SLIME_PURPLE("mobs/slime_purple.png"),
    SLIME_RED("mobs/slime_red.png"),
    SLIME_YELLOW("mobs/slime_yellow.png"),
    PLAYER("mobs/player_demo.png"),

    // OTHER ENTITIES
    BULLET("weapons/bullet.png"),

    // LASER
    LASER_BEGIN("weapons/laser/start.png"),
//    LASER_BEGIN("weapons/laser/laserAnimation.png"),
    LASER_BEGIN_OVERLAY("weapons/laser/startOver.png"),
    LASER("weapons/laser/laser.png"),
    LASER_OVERLAY("weapons/laser/laserOver.png"),
    LASER_ANIMATION_OVERLAY("weapons/laser/laserAnimation.png"),
    LASER_END("weapons/laser/end.png"),
    LASER_END_OVERLAY("weapons/laser/endOver.png"),

    // HUD
    HUD_TEXTBOX("hud/textbox.png"),
    HUD_OK_BUTTON("hud/okButton.png"),
    HUD_BARS("hud/hud.png"),
    HUD_BARS_FILL("hud/hudBars.png"),
    HUD_ITEM_SLOTS("hud/itemSlot.png"),
    HUD_RADAR_CHART("hud/net.png"),
    HUD_MOB_HEALTH("hud/mobHealth.png"),
    HUD_MOB_HEALTH_BAR("hud/mobHealthBar.png"),
//    HUD_ARROW("hud/arrow-sheet.png"),
    MOUSE_CURSOR("hud/cursor.png"),
    // Items
    ORB_BLUE("items/orbs/orbBlue.png"),
    ORB_GREEN("items/orbs/orbGreen.png"),
    ORB_GREEN_PURPLE("items/orbs/orbGreenShiny.png"),
    ORB_PINK("items/orbs/orbPink.png"),
    ORB_PURPLE("items/orbs/orbPurple.png"),
    ORB_RED("items/orbs/orbRed.png"),
    ORB_YELLOW("items/orbs/orbYellow.png"),
    ORB_BLUE_PINK("items/orbs/orbShiny.png");



    public String name;
    Textures(String name) {
        this.name = name;
    }

}
