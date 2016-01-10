package de.dogedevs.photoria.content.mob;

import de.dogedevs.photoria.utils.assets.enums.Textures;

import java.util.List;

/**
 * Created by Furuha on 09.01.2016.
 */
public class MobTemplate {

    public int id;
    public String name;

    public Textures texture;

    public float green;
    public float red;
    public float blue;
    public float yellow;
    public float purple;

    public int speed;

    public int type;
    public List<Integer> biome;

    public int ai; //ENUM
    public int weapon;

    public int maxHealth;

}
