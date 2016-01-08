package de.dogedevs.photoria.content.weapons;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.PositionComponent;

import java.util.List;

/**
 * Created by elektropapst on 08.01.2016.
 */
public class ParticleShooter implements Weapon {

    public Vector2 beginVec, endVec;
    public float rotation;
    private float length = 300;
    AttackManager am = new AttackManager();
    private Entity owner;

    public ParticleShooter() {
        beginVec = new Vector2(100,100);
        endVec = new Vector2(0,0);
    }

    @Override
    public void render(Batch batch, float deltaTime, float z) {
//        ParticleEffect particleEffect = AssetLoader.getParticleEffect(Particles.FLAME_THROWER);
        AttackManager am = new AttackManager();
        Vector2 dir = new Vector2();
        Vector2 target = getEnd();
        dir.set(target).sub(beginVec).nor();
        am.shootParticleBall(owner, dir, null);
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

    public Vector2 getEnd(){
        float rot = rotation;
        endVec.x = beginVec.x + MathUtils.cosDeg(rot) * length;
        endVec.y = beginVec.y + MathUtils.sinDeg(rot) * length;
        return endVec;
    }
}
