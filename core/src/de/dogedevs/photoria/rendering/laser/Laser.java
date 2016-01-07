package de.dogedevs.photoria.rendering.laser;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import de.dogedevs.photoria.utils.assets.AssetLoader;
import de.dogedevs.photoria.utils.assets.enums.Textures;

/**
 * Created by Furuha on 07.01.2016.
 */
public class Laser {

//    Start Cap background; The initial blast coming from the nozzle
//    Middle Section:  The repeatable part of your laser
//    End Cap: The end part of your laser. (E.g. A fadeout)
//    Interference Overlay: A repeatable overlay animation showing “interference” which will give direction and realism.

    Vector2 begin;
    Vector2 end;

    float rotation = 0;
    float length = 1000;

    public Laser(){
        begin = new Vector2(150, 150);
        end = new Vector2(1000, 150);
//        AssetLoader.getTexture(Textures.LASER).setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
    }

    public void render(Batch batch, float deltaTime){

//        rotation += 50*deltaTime;

        batch.draw(AssetLoader.getTexture(Textures.LASER),
                begin.x+AssetLoader.getTexture(Textures.LASER_BEGIN).getHeight(), begin.y, //start
                0+AssetLoader.getTexture(Textures.LASER).getWidth()/2, 0,//begin.x, begin.y, //rotation origin
                AssetLoader.getTexture(Textures.LASER).getWidth(), length-AssetLoader.getTexture(Textures.LASER_BEGIN).getHeight(),
                1, 1, //Scale
                rotation, //Rotation
                0, 0, AssetLoader.getTexture(Textures.LASER).getWidth(), AssetLoader.getTexture(Textures.LASER).getHeight(), //Texture being/size
                false,false); //flip

        batch.draw(AssetLoader.getTexture(Textures.LASER_BEGIN),
                begin.x, begin.y, //start
                0+AssetLoader.getTexture(Textures.LASER_BEGIN).getWidth()/2-begin.x+AssetLoader.getTexture(Textures.LASER_BEGIN).getHeight(), 0,//begin.x, begin.y, //rotation origin
                AssetLoader.getTexture(Textures.LASER_BEGIN).getWidth(), AssetLoader.getTexture(Textures.LASER_BEGIN).getHeight(),
                1, 1, //Scale
                rotation, //Rotation
                0, 0, AssetLoader.getTexture(Textures.LASER_BEGIN).getWidth(), AssetLoader.getTexture(Textures.LASER_BEGIN).getHeight(), //Texture being/size
                false,false); //flip
    }

}
