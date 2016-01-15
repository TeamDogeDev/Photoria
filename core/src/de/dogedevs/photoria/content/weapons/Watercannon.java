package de.dogedevs.photoria.content.weapons;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.utils.assets.ParticlePool;

import java.util.List;

/**
 * Created by elektropapst on 08.01.2016.
 */
public class Watercannon implements Weapon {

    public Vector2 beginVec, endVec;
    public float rotation;
    private float length = 300;
    public Watercannon() {
        beginVec = new Vector2(100,100);
        endVec = new Vector2(0,0);
    }

    @Override
    public void render(Batch batch, float deltaTime, float z) {
//        ParticleEffect particleEffect = Statics.asset.getParticleEffect(Particles.FLAME_THROWER);
        Statics.particle.createParticleAt(ParticlePool.ParticleType.WATER_THROWER, beginVec.x, beginVec.y + z, rotation, 5);
    }

    @Override
    public void setBegin(Vector2 begin) {
        this.beginVec = begin;
    }

    @Override
    public void setAngle(Vector2 angle){
        endVec = new Vector2(angle);
        rotation = endVec.sub(beginVec).angle();
    }

    @Override
    public void setRange(float range) {

    }

    @Override
    public void setColors(Color... colors) {

    }

    @Override
    public boolean despawnOnStop() {
        return true;
    }

    @Override
    public void setOwner(Entity owner) {

    }

    @Override
    public void checkCollision(ImmutableArray<Entity> entityList, List<Entity> resultList) {
        Vector2 endVec = getEnd();
        for(Entity entity: entityList){
            PositionComponent pc = ComponentMappers.position.get(entity);
            float dist = Intersector.distanceSegmentPoint(beginVec.x, beginVec.y, endVec.x, endVec.y, pc.x, pc.y);

            if(dist < 24){
                resultList.add(entity);
            }
        }
    }

    @Override
    public void setAdditionalThrust(Vector2 thrust) {

    }

    public Vector2 getEnd(){
        float rot = rotation;
        endVec.x = beginVec.x + MathUtils.cosDeg(rot) * length;
        endVec.y = beginVec.y + MathUtils.sinDeg(rot) * length;
        return endVec;
    }
}
