package de.dogedevs.photoria.rendering.laser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

    float rotation = 270;
    float length = 400;

    public Laser(){
        begin = new Vector2(640, 360);
        end = new Vector2(1000, 150);
//        mid.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
    }

    public void render(Batch batch, float deltaTime){

        Texture start = AssetLoader.getTexture(Textures.LASER_BEGIN);
        Texture mid = AssetLoader.getTexture(Textures.LASER);
        mid.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        Texture end = AssetLoader.getTexture(Textures.LASER_END);

        rotation += 50*deltaTime;

        batch.setBlendFunction(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);

        batch.draw(start,
                begin.x-start.getWidth()/2, begin.y, //start
                start.getWidth()/2, 0,//begin.x, begin.y, //rotation origin
                start.getWidth(), start.getHeight(),
                1, 1, //Scale
                rotation, //Rotation
                0, 0, start.getWidth(), start.getHeight(), //Texture being/size
                false,false); //flip

        batch.draw(mid,
                begin.x-start.getWidth()/2, begin.y+start.getHeight(), //start
                start.getWidth()/2, 0-start.getHeight(),//begin.x, begin.y, //rotation origin
                mid.getWidth(), length-start.getHeight()-end.getHeight(),
                1, 1, //Scale
                rotation, //Rotation
                0, 0, mid.getWidth(), mid.getHeight(), //Texture being/size
                false,false); //flip

        batch.draw(end,
                begin.x-start.getWidth()/2, begin.y+length-end.getHeight(), //start
                start.getWidth()/2, 0-length+end.getHeight(),//begin.x, begin.y, //rotation origin
                end.getWidth(), end.getHeight(),
                1, 1, //Scale
                rotation, //Rotation
                0, 0, mid.getWidth(), mid.getHeight(), //Texture being/size
                false,false); //flip


        Texture startOverlay = AssetLoader.getTexture(Textures.LASER_BEGIN_OVERLAY);
        Texture midOverlay = AssetLoader.getTexture(Textures.LASER_OVERLAY);
        midOverlay.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        Texture endOverlay = AssetLoader.getTexture(Textures.LASER_END_OVERLAY);

        batch.setBlendFunction(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE);

        batch.draw(startOverlay,
                begin.x-start.getWidth()/2, begin.y, //start
                startOverlay.getWidth()/2, 0,//begin.x, begin.y, //rotation origin
                startOverlay.getWidth(), startOverlay.getHeight(),
                1, 1, //Scale
                rotation, //Rotation
                0, 0, startOverlay.getWidth(), startOverlay.getHeight(), //Texture being/size
                false,false); //flip

        batch.draw(midOverlay,
                begin.x-startOverlay.getWidth()/2, begin.y+startOverlay.getHeight(), //start
                startOverlay.getWidth()/2, 0-startOverlay.getHeight(),//begin.x, begin.y, //rotation origin
                midOverlay.getWidth(), length-startOverlay.getHeight()-endOverlay.getHeight(),
                1, 1, //Scale
                rotation, //Rotation
                0, 0, midOverlay.getWidth(), midOverlay.getHeight(), //Texture being/size
                false,false); //flip

        batch.draw(endOverlay,
                begin.x-startOverlay.getWidth()/2, begin.y+length-endOverlay.getHeight(), //start
                startOverlay.getWidth()/2, 0-length+endOverlay.getHeight(),//begin.x, begin.y, //rotation origin
                endOverlay.getWidth(), endOverlay.getHeight(),
                1, 1, //Scale
                rotation, //Rotation
                0, 0, midOverlay.getWidth(), midOverlay.getHeight(), //Texture being/size
                false,false); //flip
    }

}
