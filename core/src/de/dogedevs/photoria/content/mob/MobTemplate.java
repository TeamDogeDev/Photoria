package de.dogedevs.photoria.content.mob;

import de.dogedevs.photoria.content.ai.AiType;
import de.dogedevs.photoria.utils.assets.enums.Sounds;
import de.dogedevs.photoria.utils.assets.enums.Textures;

import java.util.List;

/**
 * Created by Furuha on 09.01.2016.
 */
public class MobTemplate {

    public int id;
    public String name;

    public Textures texture;
    public Sounds movementSound;
    public Sounds deathSound;

    public float green;
    public float red;
    public float blue;
    public float yellow;
    public float purple;

    public MobType type;
    public List<MobAttribute> attributes;

    public List<Integer> biome;

    public AiType ai;
    public int weapon;

    public int speed;
    public int maxHealth;
    public int baseDamage;
    public int baseDefense;



}
