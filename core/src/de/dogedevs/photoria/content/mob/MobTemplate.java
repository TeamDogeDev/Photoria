package de.dogedevs.photoria.content.mob;

import de.dogedevs.photoria.utils.assets.enums.Textures;

import java.util.List;

/**
 * Created by Furuha on 09.01.2016.
 */
public class MobTemplate {

    int id;
    String name;

    Textures texture;

    int green;
    int red;
    int blue;
    int yellow;
    int purple;

    int type;
    List<Integer> biome;

    int ai; //ENUM
    int weapon;

    int maxHealth;

}
