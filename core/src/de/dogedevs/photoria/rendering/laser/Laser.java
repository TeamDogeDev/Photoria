package de.dogedevs.photoria.rendering.laser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
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

    public Vector2 begin;
    private Vector2  endVec;

    public float rotation = 270;
    public float length = 700;
    private Color color1;
    private Color color2;

    public Laser(){
        begin = new Vector2(620, 380);
        endVec = new Vector2(0, 0);
        rotation = endVec.sub(begin).angle()-90;
        color1 = new Color(0.3f,1f,0.3f,1);
        color2 = Color.WHITE;
    }

    public Vector2 getEndPoint(){
        float rot = rotation+90;
        endVec.x = begin.x + MathUtils.cosDeg(rot) * length;
        endVec.y = begin.y + MathUtils.sinDeg(rot) * length;
        return endVec;
    }

    public void setLength(float length){
        this.length = length;
    }

    public void setBegin(Vector2 begin){
        this.begin = begin;
    }

    public void setAngle(float angle){
        rotation = angle;
    }

    public void setAngle(Vector2 angle){
        endVec = new Vector2(angle);
        rotation = endVec.sub(begin).angle()-90;
    }

    public void setColors(Color color1, Color color2){
        this.color1 = color1;
        this.color2 = color2;
    }

    public void setColor(Color color){
        this.color1 = color;
        this.color2 = Color.WHITE;
    }

    public void render(Batch batch, float deltaTime){

        Texture start = AssetLoader.getTexture(Textures.LASER_BEGIN);
        Texture mid = AssetLoader.getTexture(Textures.LASER);
        mid.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        Texture end = AssetLoader.getTexture(Textures.LASER_END);

        batch.setBlendFunction(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);
        color1.a = color1.a+MathUtils.random(-0.05f,0.05f);
        color1.a = MathUtils.clamp(color1.a, 0.7f, 1);
        color2.a = color1.a+MathUtils.random(-0.05f,0.05f);
        color2.a = MathUtils.clamp(color1.a, 0.7f, 1);
        batch.setColor(color1);

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
        batch.setColor(color2);

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
