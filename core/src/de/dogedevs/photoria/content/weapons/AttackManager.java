package de.dogedevs.photoria.content.weapons;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.dogedevs.photoria.MainGame;
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

import static de.dogedevs.photoria.model.entity.ComponentMappers.*;

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
        WeaponType type = WeaponType.NEUTRAL;
        if(weapon instanceof Laser){
            type = WeaponType.LASER;
        }
        else if(weapon instanceof ParticleShooter){
            type = WeaponType.ENERGYGUN;
        }
        else if(weapon instanceof Shooter){
            type = WeaponType.LASER;
        }
        else if(weapon instanceof AcidShooter){
            type = WeaponType.SLIMEBALLS;
        }
        else if(weapon instanceof Flamethrower){
            type = WeaponType.FLAMETHROWER;
        }
        else if(weapon instanceof Watercannon){
            type = WeaponType.WATERTHROWER;
        }

        attackComponent.listener = createOnHitListener(type);
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

    public AttackComponent.OnHitListener createOnHitListener(final WeaponType type){
        return new AttackComponent.OnHitListener() {
            @Override
            public void onEnemyHit(Entity target, Entity attack, Entity parent) {
                CollisionComponent cC = collision.get(target);
                if (target == parent || target == attack || cC== null || cC.ghost || cC.projectile) {
                    return;
                }
//                Statics.sound.playSound(Sounds.MOB_HIT);
                if(sound.has(target)){
                    SoundComponent sc = sound.get(target);
                    if(Statics.time - sc.lastHitSound > 1){
                        if(player.has(target)){
                            switch(MathUtils.random(0,2)){
                                case 0:
                                    Statics.sound.playSound(Sounds.PLAYER_HIT1);
                                    break;
                                case 1:
                                    Statics.sound.playSound(Sounds.PLAYER_HIT2);
                                    break;
                                case 2:
                                    Statics.sound.playSound(Sounds.PLAYER_HIT3);
                                    break;
                            }
                        } else {
                            Statics.sound.playSound(sc.hitSound);
                        }
                        sc.lastHitSound = Statics.time;
                    }
                }

                HealthComponent hc = health.get(target);
                if(hc != null){
                    float damage = calculateDamage(parent, target, type);
                    hc.health -= (damage * Statics.settings.damageMultiplicator); // TODO DAMAGE FACTOR from constants = tmp 20!!
                    hc.health = MathUtils.clamp(hc.health, 0, hc.maxHealthUse);
                    if(hc.health == 0){
                        die(target, parent);
                    }
                }
                return;
            }

            private void die(Entity target, Entity parent) {
                ElementsComponent ec = elements.get(target);
                InventoryComponent ic = inventory.get(target);
                PositionComponent pc = position.get(target);

                if(ec != null){
                    Statics.item.createGemDrop(ec, pc);
                    target.remove(ElementsComponent.class);
                }

                if(ic != null){
                    Statics.item.dropItem(ic.slotAttack, pc, ItemComponent.ItemType.ATTACK);
                    Statics.item.dropItem(ic.slotDefense, pc, ItemComponent.ItemType.DEFENSE);
                    Statics.item.dropItem(ic.slotOther, pc, ItemComponent.ItemType.OTHER);
                    Statics.item.dropItem(ic.slotRegeneration, pc, ItemComponent.ItemType.REGENERATION);
                    Statics.item.dropItem(ic.slotStatsUp, pc, ItemComponent.ItemType.STATS_UP);

                    for(Entity item: ic.slotUse){
                        Statics.item.dropItem(item, pc, ItemComponent.ItemType.USE);
                    }
                }
            }


        };
    }

    public void shootParticleBall(Entity self, Vector2 direction, CollisionComponent.CollisionListener listener){
        if(!position.has(self)){
            return;
        }
        PooledEngine ashley = Statics.ashley;
        Entity shot = ashley.createEntity();

        shot.add(ashley.createComponent(ParticleComponent.class));

        CollisionComponent cc = ashley.createComponent(CollisionComponent.class);
        cc.ghost = true;
        cc.projectile = true;
        if(listener == null){
            cc.collisionListener = createNormalListener(self, createOnHitListener(WeaponType.ENERGYGUN));
        } else {
            cc.collisionListener = listener;
        }

        LifetimeComponent lc = ashley.createComponent(LifetimeComponent.class);
        lc.maxTime = 1;
        PositionComponent pc = ashley.createComponent(PositionComponent.class);
        PositionComponent position = ComponentMappers.position.get(self); // npe. fixed
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
            cc.collisionListener = createNormalListener(self, createOnHitListener(WeaponType.NEUTRAL));
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

    public float calculateDamage(Entity attacker, Entity defender, WeaponType weaponType) {
        MainGame.log("Weapon "+weaponType);
        float baseDamage = 1;  // TODO MAPPING!
        float baseDefense = 1;

        ElementsComponent attackerElements = elements.get(attacker);
        ElementsComponent defenderElements = elements.get(defender);
        InventoryComponent inventoryComponentAttacker = inventory.get(attacker);
        InventoryComponent inventoryComponentDefender = inventory.get(defender);
        ItemComponent attackAttacker = null;
        ItemComponent defenseDefender = null;

        if(inventoryComponentAttacker != null && inventoryComponentDefender != null) {
            if(inventoryComponentAttacker.slotAttack != null) {
                attackAttacker = item.get(inventoryComponentAttacker.slotAttack);
            }
            if(inventoryComponentDefender.slotDefense != null) {
                defenseDefender = item.get(inventoryComponentDefender.slotDefense);
            }
        }


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

            if(attackAttacker != null) {
                redDamage += attackAttacker.dmgElementRed;
                blueDamage += attackAttacker.dmgElementBlue;
                yellowDamage += attackAttacker.dmgElementYellow;
                purpleDamage += attackAttacker.dmgElementPurple;
                greenDamage += attackAttacker.dmgElementGreen;
            }

            if(defenseDefender != null) {
                redDefense += defenseDefender.defElementRed;
                blueDefense += defenseDefender.defElementBlue;
                yellowDefense += defenseDefender.defElementYellow;
                purpleDefense += defenseDefender.defElementPurple;
                greenDefense += defenseDefender.defElementGreen;
            }

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
                CollisionComponent cC = collision.get(other);
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
        ElementsComponent elementsComponent = elements.get(entity);
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
        } else {
            return;
        }
        Statics.attack.createAttack(entity, weapon);
    }
}
