package de.dogedevs.photoria.content.weapons;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.utils.assets.enums.Sounds;
import de.dogedevs.photoria.utils.assets.enums.Textures;

import java.util.List;

/**
 * Created by Furuha on 07.01.2016.
 */
public class Laser implements Weapon{

    private Vector2 begin;
    private Vector2  endVec;

    private float rotation = 270;
    private float length = 700;
    private Color color1;
    private Color color2;
    private long soundId;

    public Laser(){
        begin = new Vector2(620, 380);
        endVec = new Vector2(0, 0);
        rotation = endVec.sub(begin).angle()-90;
        color1 = new Color(0.3f,1f,0.3f,1);
        color2 = Color.WHITE;
    }

    public Vector2 getEnd(){
        float rot = rotation+90;
        endVec.x = begin.x + MathUtils.cosDeg(rot) * length;
        endVec.y = begin.y + MathUtils.sinDeg(rot) * length;
        return endVec;
    }

    @Override
    public void setColors(Color... colors) {
        if(colors.length == 1){
            setColor(colors[0]);
        }
        if(colors.length >= 2){
            setColors(colors[0], colors[1]);
        }
    }

    @Override
    public void setBegin(Vector2 begin){
        this.begin = begin;
    }

    @Override
    public void setAngle(Vector2 angle){
        endVec = new Vector2(angle);
        rotation = endVec.sub(begin).angle()-90;
    }

    @Override
    public void setAdditionalThrust(Vector2 thrust) {

    }

    @Override
    public void setRange(float range) {
        length = range;
    }

    public void setColors(Color color1, Color color2){
        this.color1 = color1;
        this.color2 = color2;
    }

    public void setColor(Color color){
        this.color1 = color;
        this.color2 = Color.WHITE;
    }

    @Override
    public void checkCollision(ImmutableArray<Entity> entityList, List<Entity> resultList) {
        Vector2 end = getEnd();
        for(Entity entity: entityList){
            PositionComponent pc = ComponentMappers.position.get(entity);
            float dist = Intersector.distanceSegmentPoint(begin.x, begin.y, end.x, end.y, pc.x, pc.y);

            if(dist < 24){
                resultList.add(entity);
            }
        }
    }

    @Override
    public boolean despawnOnStop() {
        return false;
    }

    @Override
    public void setOwner(Entity owner) {

    }

    @Override
    public void inactive(float deltaTime) {
        if(soundId != -1){
            Statics.sound.stopSound(Sounds.LASER, soundId);
            soundId = -1;
        }
    }


    public void render(Batch batch, float deltaTime, float z){

        if(soundId == -1){
            soundId = Statics.sound.loopSound(Sounds.LASER);
        }

        Texture start = Statics.asset.getTexture(Textures.LASER_BEGIN);
        Texture mid = Statics.asset.getTexture(Textures.LASER);
        mid.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        Texture end = Statics.asset.getTexture(Textures.LASER_END);

        batch.setBlendFunction(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);
        color1.a = color1.a+MathUtils.random(-0.05f,0.05f);
        color1.a = MathUtils.clamp(color1.a, 0.7f, 1);
        color2.a = color1.a+MathUtils.random(-0.05f,0.05f);
        color2.a = MathUtils.clamp(color1.a, 0.7f, 1);
        batch.setColor(color1);

        batch.draw(start,
                begin.x-start.getWidth()/2, begin.y+z, //start
                start.getWidth()/2, 0,//begin.x, begin.y, //rotation origin
                start.getWidth(), start.getHeight(),
                1, 1, //Scale
                rotation, //Rotation
                0, 0, start.getWidth(), start.getHeight(), //Texture being/size
                false,false); //flip

        batch.draw(mid,
                begin.x-start.getWidth()/2, begin.y+z+start.getHeight(), //start
                start.getWidth()/2, 0-start.getHeight(),//begin.x, begin.y, //rotation origin
                mid.getWidth(), length-start.getHeight()-end.getHeight(),
                1, 1, //Scale
                rotation, //Rotation
                0, 0, mid.getWidth(), mid.getHeight(), //Texture being/size
                false,false); //flip

        batch.draw(end,
                begin.x-start.getWidth()/2, begin.y+z+length-end.getHeight(), //start
                start.getWidth()/2, 0-length+end.getHeight(),//begin.x, begin.y, //rotation origin
                end.getWidth(), end.getHeight(),
                1, 1, //Scale
                rotation, //Rotation
                0, 0, mid.getWidth(), mid.getHeight(), //Texture being/size
                false,false); //flip


        Texture startOverlay = Statics.asset.getTexture(Textures.LASER_BEGIN_OVERLAY);
        Texture midOverlay = Statics.asset.getTexture(Textures.LASER_OVERLAY);
        midOverlay.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        Texture endOverlay = Statics.asset.getTexture(Textures.LASER_END_OVERLAY);

        batch.setBlendFunction(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE);
        batch.setColor(color2);

        batch.draw(startOverlay,
                begin.x-start.getWidth()/2, begin.y+z, //start
                startOverlay.getWidth()/2, 0,//begin.x, begin.y, //rotation origin
                startOverlay.getWidth(), startOverlay.getHeight(),
                1, 1, //Scale
                rotation, //Rotation
                0, 0, startOverlay.getWidth(), startOverlay.getHeight(), //Texture being/size
                false,false); //flip

        batch.draw(midOverlay,
                begin.x-startOverlay.getWidth()/2, begin.y+z+startOverlay.getHeight(), //start
                startOverlay.getWidth()/2, 0-startOverlay.getHeight(),//begin.x, begin.y, //rotation origin
                midOverlay.getWidth(), length-startOverlay.getHeight()-endOverlay.getHeight(),
                1, 1, //Scale
                rotation, //Rotation
                0, 0, midOverlay.getWidth(), midOverlay.getHeight(), //Texture being/size
                false,false); //flip

        batch.draw(endOverlay,
                begin.x-startOverlay.getWidth()/2, begin.y+z+length-endOverlay.getHeight(), //start
                startOverlay.getWidth()/2, 0-length+endOverlay.getHeight(),//begin.x, begin.y, //rotation origin
                endOverlay.getWidth(), endOverlay.getHeight(),
                1, 1, //Scale
                rotation, //Rotation
                0, 0, midOverlay.getWidth(), midOverlay.getHeight(), //Texture being/size
                false,false); //flip

    }


}
