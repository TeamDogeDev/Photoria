package de.dogedevs.photoria.content.weapons;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.*;
import de.dogedevs.photoria.model.entity.components.rendering.ParticleComponent;
import de.dogedevs.photoria.model.entity.components.rendering.SpriteComponent;
import de.dogedevs.photoria.model.entity.components.stats.ElementsComponent;
import de.dogedevs.photoria.model.entity.components.stats.HealthComponent;
import de.dogedevs.photoria.model.entity.components.stats.LifetimeComponent;
import de.dogedevs.photoria.utils.assets.enums.Sounds;
import de.dogedevs.photoria.utils.assets.enums.Textures;

/**
 * Created by Furuha on 06.01.2016.
 */
public class AttackManager {

    public Entity createAttack(Entity parent, Weapon weapon){
        PooledEngine ashley = Statics.ashley;
        Entity attack = Statics.ashley.createEntity();

        TargetComponent tc = ashley.createComponent(TargetComponent.class);
        attack.add(tc);
        ParentComponent parentComponent = ashley.createComponent(ParentComponent.class);
        parentComponent.parent = parent;
        attack.add(parentComponent);
        AttackComponent attackComponent = ashley.createComponent(AttackComponent.class);
//        attackComponent.listener = new AttackComponent.OnHitListener() {
//            @Override
//            public void onEnemyHit(Entity target, Entity attack, Entity parent) {
//                if(target == parent || target == attack){
//                    return;
//                }
//                SoundManager.playSound(Sounds.MOB_HIT);
//            }
//        };
        attackComponent.listener = createOnHitListener();
        attackComponent.weapon = weapon;
        attack.add(attackComponent);

        weapon.setOwner(parent);

        ashley.addEntity(attack);

        return attack;
    }

    public void deleteWeaponsFrom(Entity parent){
        ImmutableArray<Entity> entities = Statics.ashley.getEntitiesFor(Family.all(AttackComponent.class).get());
        for(Entity entity: entities){
            ParentComponent pc = ComponentMappers.parent.get(entity);
            if(pc != null && pc.parent == parent){
                Statics.ashley.removeEntity(entity);
            }
        }
    }

    public AttackComponent.OnHitListener createOnHitListener(){
        return new AttackComponent.OnHitListener() {
            @Override
            public void onEnemyHit(Entity target, Entity attack, Entity parent) {
                CollisionComponent cC = ComponentMappers.collision.get(target);
                if (target == parent || target == attack || cC== null || cC.ghost || cC.projectile) {
                    return;
                }
                Statics.sound.playSound(Sounds.MOB_HIT);

                HealthComponent hc = ComponentMappers.health.get(target);
                if(hc != null){
                    float damage = calculateDamage(parent, target);
                    hc.health -= (damage * 20); // TODO DAMAGE FACTOR from constants = tmp 20!!
                    hc.health = MathUtils.clamp(hc.health, 0, hc.maxHealth);
                    if(hc.health == 0){
                        die(target, parent);
                    }
                }
                return;
            }

            private void die(Entity target, Entity parent) {
                ElementsComponent ec = ComponentMappers.elements.get(target);
                InventoryComponent ic = ComponentMappers.inventory.get(target);
                PositionComponent pc = ComponentMappers.position.get(target);

                if(ec != null){
                    Statics.item.createGemDrop(ec, pc);
                    target.remove(ElementsComponent.class);
                }

                if(ic != null){
                    Statics.item.dropItem(ic.slotAttack, pc);
                    Statics.item.dropItem(ic.slotDefense, pc);
                    Statics.item.dropItem(ic.slotOther, pc);
                    Statics.item.dropItem(ic.slotRegeneration, pc);
                    Statics.item.dropItem(ic.slotStatsUp, pc);

                    for(Entity item: ic.slotUse){
                        Statics.item.dropItem(item, pc);
                    }
                }
            }


        };
    }

    public void shootParticleBall(Entity self, Vector2 direction, CollisionComponent.CollisionListener listener){
        PooledEngine ashley = Statics.ashley;
        Entity shot = ashley.createEntity();

        shot.add(ashley.createComponent(ParticleComponent.class));

        CollisionComponent cc = ashley.createComponent(CollisionComponent.class);
        cc.ghost = true;
        cc.projectile = true;
        if(listener == null){
            cc.collisionListener = createNormalListener(self, createOnHitListener());
        } else {
            cc.collisionListener = listener;
        }

        LifetimeComponent lc = ashley.createComponent(LifetimeComponent.class);
        lc.maxTime = 1;
        PositionComponent pc = ashley.createComponent(PositionComponent.class);
        PositionComponent position = ComponentMappers.position.get(self); // npe
        pc.x = position.x;
        pc.y = position.y;
        pc.z = 26;
        VelocityComponent vc = ashley.createComponent(VelocityComponent.class);
        vc.speed = 512;
        vc.vectorDirection = direction;
        shot.add(pc);
        shot.add(vc);
        shot.add(cc);
        shot.add(lc);

        ashley.addEntity(shot);
    }

    public void shootNormal(Entity self, Vector2 direction, CollisionComponent.CollisionListener listener){
        PooledEngine ashley = Statics.ashley;
        Entity shot = ashley.createEntity();
        SpriteComponent sc = ashley.createComponent(SpriteComponent.class);
        sc.region = new TextureRegion(Statics.asset.getTexture(Textures.BULLET));

        CollisionComponent cc = ashley.createComponent(CollisionComponent.class);
        cc.ghost = true;
        cc.projectile = true;
        if(listener == null){
            cc.collisionListener = createNormalListener(self, createOnHitListener());
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

    public float calculateDamage(Entity attacker, Entity defender) {
        float baseDamage = 1;  // TODO MAPPING!
        float baseDefense = 1;

        ElementsComponent attackerElements = ComponentMappers.elements.get(attacker);
        ElementsComponent defenderElements = ComponentMappers.elements.get(defender);

        if(attackerElements != null && defenderElements != null) {
            float redDamage = baseDamage * MathUtils.clamp(attackerElements.red, 0, 1);
            float blueDamage = baseDamage * MathUtils.clamp(attackerElements.blue, 0, 1);
            float yellowDamage = baseDamage * MathUtils.clamp(attackerElements.yellow, 0, 1);
            float purpleDamage = baseDamage * MathUtils.clamp(attackerElements.purple, 0, 1);
            float greenDamage = baseDamage * MathUtils.clamp(attackerElements.green, 0, 1);

            float redDefense = baseDefense * defenderElements.red;
            float blueDefense = baseDefense * defenderElements.blue;
            float yellowDefense = baseDefense * defenderElements.yellow;
            float purpleDefense = baseDefense * defenderElements.purple;
            float greenDefense = baseDefense * defenderElements.green;

            float damage = (redDamage * (redDamage - redDefense))
                    + (blueDamage * (blueDamage - blueDefense))
                    + (yellowDamage * (yellowDamage - yellowDefense))
                    + (purpleDamage * (purpleDamage - purpleDefense))
                    + (greenDamage * (greenDamage - greenDefense));

            damage /= 10;
            damage = MathUtils.clamp(damage, .1f, 1f);

            return damage;
        }
        return 0;
    }

    private CollisionComponent.CollisionListener createNormalListener(final Entity parent, final AttackComponent.OnHitListener onHitListener) {
        final PooledEngine ashley = Statics.ashley;
        CollisionComponent.CollisionListener listener = new CollisionComponent.CollisionListener() {

            @Override
            public boolean onCollision(Entity other, Entity self) {
                CollisionComponent cC = ComponentMappers.collision.get(other);
                if (other == parent || cC.ghost || cC.projectile) {
                    return false;
                }

                onHitListener.onEnemyHit(other, self, parent);

                ashley.removeEntity(self);
                return true;
            }

        };
        return listener;
    }

    public void loadWeapon(Entity entity) {
        Weapon weapon = null;
        ElementsComponent elementsComponent = ComponentMappers.elements.get(entity);
        if(elementsComponent != null) {
            float biggest = elementsComponent.blue;
            weapon = new Watercannon();
            if(elementsComponent.yellow >= biggest){
                biggest = elementsComponent.yellow;
                weapon = new Laser();
            }
            if(elementsComponent.red >= biggest){
                biggest = elementsComponent.red;
                weapon = new Flamethrower();
            }
            if(elementsComponent.purple >= biggest){
                biggest = elementsComponent.purple;
                weapon = new ParticleShooter();
            }
            if(elementsComponent.green >= biggest){
                weapon = new AcidShooter();
            }
        }
        Statics.attack.createAttack(entity, weapon);
    }
}
