package de.dogedevs.photoria;

import com.badlogic.ashley.core.PooledEngine;
import de.dogedevs.photoria.content.GameMessages;
import de.dogedevs.photoria.content.GameSettings;
import de.dogedevs.photoria.content.items.ItemManager;
import de.dogedevs.photoria.content.mob.MobManager;
import de.dogedevs.photoria.content.weapons.AttackManager;
import de.dogedevs.photoria.utils.assets.*;

/**
 * Created by Furuha on 09.01.2016.
 */
/** Environment class holding references to our stuff. The references are held in public static fields which allows static access to all sub systems. Do not
 * use Graphics in a thread that is not the rendering thread.
 * <p>
 * This is normally a design faux pas but in this case is better than the alternatives.
 * @author of JavDoc mzechner, teamDogeDev
 * @author teamDogeDev */
public class Statics {

    public static float boat; //must be a float otherwise it sinks

    public static AssetLoader asset;
    public static PooledEngine ashley;
    public static MobManager mob;
    public static AnimationManager animation;
    public static ParticlePool particle;
    public static AttackManager attack;
    public static MusicManager music;
    public static SoundManager sound;
    public static ItemManager item;
    public static GameMessages message;
    public static GameSettings settings;

    public static void initCat(){
        ashley = new PooledEngine();
        mob = new MobManager();
        asset = new AssetLoader();
        animation = new AnimationManager();
        attack = new AttackManager();
        music = new MusicManager();
        sound = new SoundManager();
        item = new ItemManager();
        particle = new ParticlePool();
        message = new GameMessages();
        settings = new GameSettings();
    }

}
