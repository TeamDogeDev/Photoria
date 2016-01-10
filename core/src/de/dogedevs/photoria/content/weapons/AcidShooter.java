package de.dogedevs.photoria.content.weapons;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.dogedevs.photoria.Statics;

import java.util.List;

/**
 * Created by elektropapst on 08.01.2016.
 */
public class AcidShooter implements Weapon {

    public Vector2 beginVec, endVec;
    public float rotation;
    private float length = 300;
    private Entity owner;

    public AcidShooter() {
        beginVec = new Vector2(100,100);
        endVec = new Vector2(0,0);
    }

    float deltaSum = 0;

    @Override
    public void render(Batch batch, float deltaTime, float z) {
//        ParticleEffect particleEffect = Statics.asset.getParticleEffect(Particles.FLAME_THROWER);
        deltaSum -= deltaTime;
        if(deltaSum <= 0) {
            deltaSum = 1f;
            Vector2 dir = new Vector2();
            Vector2 target = getEnd();
            dir.set(target).sub(beginVec).nor();
            Statics.attack.shootNormal(owner, dir, null);
        }
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
        this.owner = owner;
    }

    @Override
    public void setAdditionalThrust(Vector2 thrust) {

    }

    @Override
    public void checkCollision(ImmutableArray<Entity> entityList, List<Entity> resultList) {

    }

    public Vector2 getEnd(){
        float rot = rotation;
        endVec.x = beginVec.x + MathUtils.cosDeg(rot) * length;
        endVec.y = beginVec.y + MathUtils.sinDeg(rot) * length;
        return endVec;
    }
}
