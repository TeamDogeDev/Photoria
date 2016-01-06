package de.dogedevs.photoria.utils.assets;

/**
 * Created by elektropapst on 03.01.2016.
 */
public enum Textures {
    // TITLESCREEN
    TITLE("title.png"),

    // WORLD
    CLOUD_STUB("clouds.png"),
    MAIN_TILESET("tilesets/tileset.png"),

    // MOBS
    EYE("mobs/eyeball.png"),
    SLIME("mobs/slime.png"),
    PLAYER("mobs/player_demo.png"),

    // OTHER ENTITIES
    BULLET("weapons/bullet.png"),

    // HUD
    HUD_TEXTBOX("hud/textbox.png"),
    HUD_OK_BUTTON("hud/okButton.png"),
    HUD_BARS("hud/hud.png"),
    HUD_BARS_FILL("hud/hudBars.png"),
    HUD_ITEM_SLOTS("hud/itemSlot.png"),
    HUD_RADAR_CHART("hud/net.png");



    public String name;
    Textures(String name) {
        this.name = name;
    }

}
