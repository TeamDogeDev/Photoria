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
import de.dogedevs.photoria.model.entity.components.stats.EnergyComponent;
import de.dogedevs.photoria.utils.assets.ParticlePool;
import de.dogedevs.photoria.utils.assets.enums.Sounds;

import java.util.List;

/**
 * Created by elektropapst on 08.01.2016.
 */
public class Flamethrower implements Weapon {

    public Vector2 beginVec, endVec;
    public float rotation;
    private float length = 300;
    private long soundId = -1;
    private Entity owner;
    private boolean active;

    public Flamethrower() {
        beginVec = new Vector2(100,100);
        endVec = new Vector2(0,0);
    }

    @Override
    public void updateInactive(Batch batch, float deltaTime, float z) {
        if(soundId != -1){
            Statics.sound.stopSound(Sounds.FLAMETHROWER, soundId);
            soundId = -1;
        }
        active = false;
    }

    public void updateActive(Batch batch, float deltaTime, float z){
        if(ComponentMappers.energy.has(owner)){
            EnergyComponent ec = ComponentMappers.energy.get(owner);
            float cons = Statics.settings.fireConsumption * deltaTime;
            if(ec.energy >= cons){
                ec.energy -= cons;
            } else {
                active = false;
                return;
            }
        }
        active = true;
        if(soundId == -1){
            soundId = Statics.sound.loopSound(Sounds.FLAMETHROWER);
        }

//        ParticleEffect particleEffect = Statics.asset.getParticleEffect(Particles.FLAME_THROWER);
        Statics.particle.createParticleAt(ParticlePool.ParticleType.FLAME_THROWER, beginVec.x, beginVec.y + z, rotation, 10);
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
        if(!active){
            return;
        }
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
