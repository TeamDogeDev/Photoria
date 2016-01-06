package de.dogedevs.photoria.content;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.*;
import de.dogedevs.photoria.screens.GameScreen;
import de.dogedevs.photoria.utils.assets.ParticlePool;

import java.util.Random;

/**
 * Created by Furuha on 06.01.2016.
 */
public class AttackManager {

    private static Random rand = new Random();
    private final Sound hit = Gdx.audio.newSound(Gdx.files.internal("audio/hit.wav"));

    public void shootNormal(Entity self, int direction, CollisionComponent.CollisionListener listener){
        PooledEngine ashley = GameScreen.getAshley();
        Entity shot = ashley.createEntity();
        SpriteComponent sc = ashley.createComponent(SpriteComponent.class);
        sc.region = new TextureRegion(new Texture("bullet.png"));

        CollisionComponent cc = ashley.createComponent(CollisionComponent.class);
        cc.ghost = true;
        cc.projectile = true;
        if(listener == null){
            cc.collisionListener = createNormalListener(self);
        } else {
            cc.collisionListener = listener;
        }

        PositionComponent pc = ashley.createComponent(PositionComponent.class);
        PositionComponent position = ComponentMappers.position.get(self);
        pc.x = position.x;
        pc.y = position.y;
        pc.z = 26;
        VelocityComponent vc = ashley.createComponent(VelocityComponent.class);
        vc.speed = 512;
        vc.direction = direction;
        shot.add(pc);
        shot.add(sc);
        shot.add(vc);
        shot.add(cc);

        ashley.addEntity(shot);
    }

    private CollisionComponent.CollisionListener createNormalListener(final Entity parent) {
        final PooledEngine ashley = GameScreen.getAshley();
        CollisionComponent.CollisionListener listener = new CollisionComponent.CollisionListener() {

            @Override
            public boolean onCollision(Entity other, Entity self) {
                ItemComponent itemC = ComponentMappers.item.get(other);
                CollisionComponent cC = ComponentMappers.collision.get(other);
                if (other == parent || itemC != null && cC.ghost || cC.projectile) {
                    return false;
                }
                hit.play();

                HealthComponent hc = ComponentMappers.health.get(other);
                if(hc != null){
                    hc.health -= 25;
                    hc.health = MathUtils.clamp(hc.health, 0, hc.maxHealth);
                    if(hc.health == 0){
                        die(other, self);
                    }
                } else {
                    die(other, self);
                }
                ashley.removeEntity(self);
                return true;
            }

            private void die(Entity other, Entity self) {
                ElementsComponent ec = ComponentMappers.elements.get(other);
                ElementsComponent playerEc = ComponentMappers.elements.get(self);
                if(ec != null && playerEc != null){
                    playerEc.blue += ec.blue;
                    playerEc.yellow += ec.yellow;
                    playerEc.red += ec.red;
                    playerEc.purple += ec.purple;
                    playerEc.green += ec.green;

                    playerEc.blue = MathUtils.clamp(playerEc.blue, 0f, 20f);
                    playerEc.yellow = MathUtils.clamp(playerEc.yellow, 0f, 20f);
                    playerEc.red = MathUtils.clamp(playerEc.red, 0f, 20f);
                    playerEc.purple = MathUtils.clamp(playerEc.purple, 0f, 20f);
                    playerEc.green = MathUtils.clamp(playerEc.green, 0f, 20f);
                }
                InventoryComponent ic = ComponentMappers.inventory.get(other);
                PositionComponent pc = ComponentMappers.position.get(other);

//                ParticleEffectPool.PooledEffect effect = ParticlePool.instance().obtain(ParticlePool.ParticleType.BLOOD);
//                effect.setPosition(pc.x, pc.y);
//                effect.start();
                ParticlePool.instance().createParticleAt(rand.nextBoolean() ? ParticlePool.ParticleType.BLOOD : ParticlePool.ParticleType.FIRE, pc.x, pc.y);

                if(ic != null){
                    for(Entity item: ic.items){
                        PositionComponent newPc = ashley.createComponent(PositionComponent.class);
                        newPc.x = pc.x;
                        newPc.y = pc.y;
                        item.add(newPc);
                        LifetimeComponent lc = ashley.createComponent(LifetimeComponent.class);
                        lc.maxTime = 10;
                        item.add(lc);
                    }
                }
                ashley.removeEntity(other);
            }
        };
        return listener;
    }

}
