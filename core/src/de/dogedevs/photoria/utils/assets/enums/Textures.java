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


    // SPACE SHIP
    SPACE_SHIP("mobs/spaceship.png"),

    // MOBS
    EYE("mobs/eyeball.png"),
    SLIME_BLUE("mobs/slime_blue.png"),
    SLIME_GREEN("mobs/slime_green.png"),
    SLIME_PURPLE("mobs/slime_purple.png"),
    SLIME_RED("mobs/slime_red.png"),
    SLIME_YELLOW("mobs/slime_yellow.png"),
    PLAYER("mobs/player_demo.png"),
    BEE("mobs/bee.png"),

    // OTHER ENTITIES
    BULLET("weapons/bullet.png"),
    BULLET_SPLASH("weapons/splash_base_big.png"),

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
    HUD_ITEM_DESCRIPTION("hud/itemDescription.png"),
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

    ITEM_BOTTLE_BLUE("items/items/bottle_blue.png"),
    ITEM_BOTTLE_RED("items/items/bottle_red.png"),
    ITEM_BOTTLE_YELLOW("items/items/bottle_yellow.png"),
    ITEM_BOTTLE_GREEN("items/items/bottle_green.png"),
    ITEM_BOTTLE_PURPLE("items/items/bottle_purple.png"),

    ITEM_SWORD_BLUE("items/items/sword_blue.png"),
    ITEM_SWORD_RED("items/items/sword_red.png"),
    ITEM_SWORD_YELLOW("items/items/sword_yellow.png"),
    ITEM_SWORD_GREEN("items/items/sword_green.png"),
    ITEM_SWORD_PURPLE("items/items/sword_purple.png"),

    ITEM_RESISTANCE_BLUE("items/items/water.png"),
    ITEM_RESISTANCE_RED("items/items/fire.png"),
    ITEM_RESISTANCE_YELLOW("items/items/light.png"),
    ITEM_RESISTANCE_GREEN("items/items/acid.png"),
    ITEM_RESISTANCE_PURPLE("items/items/darkenergy.png"),

    ITEM_BOOK_LIFE("items/items/life_book.png"),
    ITEM_BOOK_ENERGY("items/items/energy_book.png"),

    ITEM_SWEET("items/items/sweet.png"),
    ITEM_TENTACLE("items/items/tentacle.png"),
    ITEM_SHOE_YELLOW("items/items/shoe.png"),
    ITEM_SHOE_GREEN("items/items/shoe_green.png"),
    ITEM_LIFE("items/items/life.png"),
    ITEM_ENERGY("items/items/energy.png"),
    ITEM_TERRAFORMIN("items/items/terraforming.png"),

    // MENU
    MENU_BOX("menu/menuBox.png"),
    MENU_BOX_SMALL("menu/menuBoxSmall.png"),
    MENU_VOLUME_BOX("menu/volumeBox.png"),
    MENU_VOLUME_BOX_BAR("menu/volumeBoxBar.png"),
    MENU_LOGO_INNER("menu/logo_inner.png"),
    MENU_LOGO_OUTER("menu/logo_outer.png"),
    MENU_INPUT("menu/input.png");



    public String name;
    Textures(String name) {
        this.name = name;
    }

}
