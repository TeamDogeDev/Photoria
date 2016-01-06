package de.dogedevs.photoria.content;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.*;
import de.dogedevs.photoria.screens.GameScreen;
import de.dogedevs.photoria.utils.assets.AssetLoader;
import de.dogedevs.photoria.utils.assets.SoundManager;
import de.dogedevs.photoria.utils.assets.enums.Sounds;
import de.dogedevs.photoria.utils.assets.enums.Textures;

import java.util.Random;

/**
 * Created by Furuha on 06.01.2016.
 */
public class AttackManager {

    private static Random rand = new Random();

    public void shootNormal(Entity self, Vector2 direction, CollisionComponent.CollisionListener listener){
        PooledEngine ashley = GameScreen.getAshley();
        Entity shot = ashley.createEntity();
        SpriteComponent sc = ashley.createComponent(SpriteComponent.class);
        sc.region = new TextureRegion(AssetLoader.getTexture(Textures.BULLET));

        CollisionComponent cc = ashley.createComponent(CollisionComponent.class);
        cc.ghost = true;
        cc.projectile = true;
        if(listener == null){
            cc.collisionListener = createNormalListener(self);
        } else {
            cc.collisionListener = listener;
        }

        LifetimeComponent lc = ashley.createComponent(LifetimeComponent.class);
        lc.maxTime = 1;
        PositionComponent pc = ashley.createComponent(PositionComponent.class);
        PositionComponent position = ComponentMappers.position.get(self);
        pc.x = position.x;
        pc.y = position.y;
        pc.z = 26;
        VelocityComponent vc = ashley.createComponent(VelocityComponent.class);
        vc.speed = 512;
        vc.vectorDirection = direction;
        shot.add(pc);
        shot.add(sc);
        shot.add(vc);
        shot.add(cc);
        shot.add(lc);

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
                SoundManager.playSound(Sounds.MOB_HIT);

                HealthComponent hc = ComponentMappers.health.get(other);
                if(hc != null){
                    hc.health -= 25;
                    hc.health = MathUtils.clamp(hc.health, 0, hc.maxHealth);
                    if(hc.health == 0){
                        die(other, self);
                    }
                }
                ashley.removeEntity(self);
                return true;
            }

            private void die(Entity other, Entity self) {
                ElementsComponent ec = ComponentMappers.elements.get(other);
                ElementsComponent playerEc = ComponentMappers.elements.get(parent);
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
//                ashley.removeEntity(other);
            }
        };
        return listener;
    }

}
