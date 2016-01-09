package de.dogedevs.photoria.content.items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.CollisionComponent;
import de.dogedevs.photoria.model.entity.components.InventoryComponent;
import de.dogedevs.photoria.model.entity.components.ItemComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.entity.components.rendering.SpriteComponent;
import de.dogedevs.photoria.model.entity.components.stats.LifetimeComponent;
import de.dogedevs.photoria.screens.GameScreen;
import de.dogedevs.photoria.utils.assets.AssetLoader;
import de.dogedevs.photoria.utils.assets.enums.Textures;

/**
 * Created by Furuha on 09.01.2016.
 */
public class ItemManager {

    public ItemManager() {

    }

    public void populateInventory(Entity entity, int mobType){
        InventoryComponent inventory = ComponentMappers.inventory.get(entity);
        if(inventory != null){

            inventory.slotAttack = generateAttackItem(mobType);
            inventory.slotDefense = generateDefenseItem(mobType);
            inventory.slotRegeneration = generateRegItem(mobType);
            inventory.slotStatsUp = generateStatsItem(mobType);
            inventory.slotOther = generateOtherItem(mobType);
            Entity item = generateUseItem(mobType);
            if(item != null){
                inventory.slotUse.add(item);
            }
        }
    }

    private Entity generateUseItem(int mobType) {
        return null;
    }

    private Entity generateOtherItem(int mobType) {
        return null;
    }

    private Entity generateStatsItem(int mobType) {
        return null;
    }

    private Entity generateDefenseItem(int mobType) {
        return null;
    }

    private Entity generateAttackItem(int mobType) {
        return generateBasicItem("Orb des attackes!", ItemComponent.ItemType.ATTACK);
    }

    private Entity generateRegItem(int mobType) {
        return null;
    }

    private Entity generateBasicItem(String name, ItemComponent.ItemType type){
        Entity entity = GameScreen.getAshley().createEntity();

        SpriteComponent sc = GameScreen.getAshley().createComponent(SpriteComponent.class);
        sc.region = new TextureRegion(AssetLoader.getTexture(Textures.ORE_GREEN));
        entity.add(sc);

        CollisionComponent cc = GameScreen.getAshley().createComponent(CollisionComponent.class);
        cc.ghost = true;
        cc.collisionListener = new CollisionComponent.CollisionListener() {
            @Override
            public boolean onCollision(Entity other, Entity self) {
                if(ComponentMappers.player.has(other)){
                    InventoryComponent inventory = ComponentMappers.inventory.get(other);
                    addItemToInventory(self, inventory);
                    return true;
                }
                return false;
            }
        };
        entity.add(cc);

        ItemComponent ic = GameScreen.getAshley().createComponent(ItemComponent.class);
        ic.name = name;
        ic.type = type;
        entity.add(ic);

        GameScreen.getAshley().addEntity(entity);
        return entity;
    }

    public void addItemToInventory(Entity item, InventoryComponent inventory) {
        if(inventory != null && item != null) {
            ItemComponent itemComponent = ComponentMappers.item.get(item);
            PositionComponent position = ComponentMappers.position.get(item);

            switch (itemComponent.type) {
                case ATTACK:
                    dropItem(inventory.slotAttack, position);
                    inventory.slotAttack = item;
                    break;
                case DEFENSE:
                    dropItem(inventory.slotDefense, position);
                    inventory.slotDefense = item;
                    break;
                case REGENERATION:
                    dropItem(inventory.slotRegeneration, position);
                    inventory.slotRegeneration = item;
                    break;
                case STATSUP:
                    dropItem(inventory.slotStatsUp, position);
                    inventory.slotStatsUp = item;
                    break;
                case OTHER:
                    dropItem(inventory.slotOther, position);
                    inventory.slotOther = item;
                    break;
                case USE:
                    inventory.slotUse.add(item);
                    break;
            }
            LifetimeComponent lc = ComponentMappers.lifetime.get(item);
            if(lc != null){
                item.remove(LifetimeComponent.class);
            }
            item.remove(PositionComponent.class);
        }
    }

    public void dropItem(Entity item, PositionComponent positionComponent) {
        if(item != null){
            LifetimeComponent lc = GameScreen.getAshley().createComponent(LifetimeComponent.class);
            lc.maxTime = 10;
            item.add(lc);
            item.add(new PositionComponent(positionComponent.x+MathUtils.random(-10,10), positionComponent.y+MathUtils.random(-10,10)));
        }
    }
}
