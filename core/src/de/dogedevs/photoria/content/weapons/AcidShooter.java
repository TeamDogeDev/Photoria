package de.dogedevs.photoria.content.weapons;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.CollisionComponent;
import de.dogedevs.photoria.model.entity.components.DecreaseZComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.entity.components.VelocityComponent;
import de.dogedevs.photoria.model.entity.components.rendering.SpriteComponent;
import de.dogedevs.photoria.model.entity.components.stats.EnergyComponent;
import de.dogedevs.photoria.model.entity.components.stats.LifetimeComponent;
import de.dogedevs.photoria.utils.assets.ParticlePool;
import de.dogedevs.photoria.utils.assets.enums.Sounds;
import de.dogedevs.photoria.utils.assets.enums.Textures;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elektropapst on 08.01.2016.
 */
public class AcidShooter implements Weapon {

    public Vector2 beginVec, endVec;
    public float rotation;
    private float length = 300;
    private Entity owner;

    private List<Vector2> checkList;

    public AcidShooter() {
        beginVec = new Vector2(100,100);
        endVec = new Vector2(0,0);
        checkList =  new ArrayList<>();
    }

    float lastShot = 0;

    @Override
    public void updateActive(Batch batch, float deltaTime, float z) {
        if(!ComponentMappers.position.has(owner)){
            return;
        }
        if(Statics.time-lastShot > 1){
            if(ComponentMappers.energy.has(owner)){
                EnergyComponent ec = ComponentMappers.energy.get(owner);
                if(ec.energy >= 10){
                    ec.energy -= 10;
                } else {
                    return;
                }
            }
            if(ComponentMappers.sound.has(owner)){
                Statics.sound.playSound(ComponentMappers.sound.get(owner).shotSound);
            }
            lastShot = Statics.time;
            Vector2 dir = new Vector2();
            Vector2 target = getEnd();
            dir.set(target).sub(beginVec).nor();
            final PooledEngine ashley = Statics.ashley;
            Entity shot = ashley.createEntity();
            SpriteComponent sc = ashley.createComponent(SpriteComponent.class);
            sc.region = new TextureRegion(Statics.asset.getTexture(Textures.BULLET_SPLASH));

            CollisionComponent cc = ashley.createComponent(CollisionComponent.class);
            cc.ghost = true;
            cc.projectile = true;
            cc.collisionListener = new CollisionComponent.CollisionListener() {

                    @Override
                    public boolean onCollision(Entity other, Entity self) {
                        PositionComponent positionComponent = ComponentMappers.position.get(self);
                        CollisionComponent cC = ComponentMappers.collision.get(other);
                        if (other == owner || cC.ghost || cC.projectile || positionComponent == null) {
                            return false;
                        }
                        Statics.sound.playSound(Sounds.MOB_HIT);

                        checkList.add(new Vector2(positionComponent.x, positionComponent.y));
                        Statics.particle.createParticleAt(ParticlePool.ParticleType.SLIME_SPLASH, positionComponent.x, positionComponent.y+positionComponent.z);
                        ashley.removeEntity(self);
                        return true;
                    }
            };

            LifetimeComponent lc = ashley.createComponent(LifetimeComponent.class);
            lc.maxTime = 1;
            PositionComponent pc = ashley.createComponent(PositionComponent.class);
            PositionComponent position = ComponentMappers.position.get(owner);
            pc.x = position.x;
            pc.y = position.y;
            pc.z = 26;
            VelocityComponent vc = ashley.createComponent(VelocityComponent.class);
            vc.speed = 512;
            vc.vectorDirection = dir;
            DecreaseZComponent zc = ashley.createComponent(DecreaseZComponent.class);
            zc.rate = 0.7f;
            zc.listener = new DecreaseZComponent.OnDecreaseListener() {
                @Override
                public void onDecrease(float newZValue, float x, float y, Entity entity) {
                    if(newZValue <= 0){
                        checkList.add(new Vector2(x, y));
                        Statics.particle.createParticleAt(ParticlePool.ParticleType.SLIME_SPLASH, x, y);
                        ashley.removeEntity(entity);
                    }
                }
            };
            shot.add(zc);
            shot.add(pc);
            shot.add(sc);
            shot.add(vc);
            shot.add(cc);
//        shot.add(lc);

            ashley.addEntity(shot);
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
    public void updateInactive(Batch batch, float deltaTime, float z) {

    }

    @Override
    public void checkCollision(ImmutableArray<Entity> entityList, List<Entity> resultList) {
        for(Vector2 pos: checkList){
            for(Entity entity: entityList){
                PositionComponent pc = ComponentMappers.position.get(entity);
                float dist = Vector2.dst(pos.x, pos.y, pc.x, pc.y);
                if(dist < 70){
                    resultList.add(entity);
                }
            }
        }
        checkList.clear();
    }

    public Vector2 getEnd(){
        float rot = rotation;
        endVec.x = beginVec.x + MathUtils.cosDeg(rot) * length;
        endVec.y = beginVec.y + MathUtils.sinDeg(rot) * length;
        return endVec;
    }
}
