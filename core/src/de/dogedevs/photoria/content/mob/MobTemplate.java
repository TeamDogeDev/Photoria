package de.dogedevs.photoria.content.mob;

import de.dogedevs.photoria.content.ai.AiType;
import de.dogedevs.photoria.content.weapons.WeaponType;
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

    public float green;
    public float red;
    public float blue;
    public float yellow;
    public float purple;

    public MobType type;
    public List<MobAttribute> attributes;

    public List<Integer> biome;

    public AiType ai;
    public WeaponType weapon;

    public int speed;
    public int maxHealth;
    public int maxEnergy;
    public int baseDamage;
    public int baseDefense;
    public int range;

    public float baseEnergyReg;
    public float baseHealthReg;

    public Sounds hitSound;
    public Sounds deathSound;
    public Sounds shotSound;
    public Sounds movementSound;
}
