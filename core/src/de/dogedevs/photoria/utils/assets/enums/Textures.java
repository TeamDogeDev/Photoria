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
    HUD_PLAYER_GREEN("hud/player_green.png"),
    HUD_PLAYER_PURPLE("hud/player_purple.png"),
    HUD_PLAYER_RED("hud/player_red.png"),
    HUD_PLAYER_YELLOW("hud/player_yellow.png"),
    HUD_PLAYER_BLUE("hud/player_blue.png"),
    MOUSE_CURSOR("hud/cursor.png"),
    // Items
    ORB_BLUE("items/orbs/orbBlue.png"),
    ORB_GREEN("items/orbs/orbGreen.png"),
    ORB_GREEN_PURPLE("items/orbs/orbGreenShiny.png"),
    ORB_PINK("items/orbs/orbPink.png"),
    ORB_PURPLE("items/orbs/orbPurple.png"),
    ORB_RED("items/orbs/orbRed.png"),
    ORB_YELLOW("items/orbs/orbYellow.png"),
    ORB_BLUE_PINK("items/orbs/orbShiny.png"),

    ORE_PURPLE("items/ore/orePurple.png"),
    ORE_RED("items/ore/oreRed.png"),
    ORE_YELLOW("items/ore/oreYellow.png"),
    ORE_BLUE("items/ore/oreBlue.png"),
    ORE_GREEN("items/ore/oreGreen.png"),

    // MENU
    MENU_BOX("menu/menuBox.png"),
    MENU_BOX_SMALL("menu/menuBoxSmall.png"),
    MENU_VOLUME_BOX("menu/volumeBox.png"),
    MENU_VOLUME_BOX_BAR("menu/volumeBoxBar.png"),
    MENU_LOGO_INNER("menu/logo_inner.png"),
    MENU_LOGO_OUTER("menu/logo_outer.png");



    public String name;
    Textures(String name) {
        this.name = name;
    }

}
