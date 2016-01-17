package de.dogedevs.photoria.content.items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.content.mob.MobType;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.CollisionComponent;
import de.dogedevs.photoria.model.entity.components.InventoryComponent;
import de.dogedevs.photoria.model.entity.components.ItemComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.entity.components.rendering.SpriteComponent;
import de.dogedevs.photoria.model.entity.components.stats.ElementsComponent;
import de.dogedevs.photoria.model.entity.components.stats.EnergyComponent;
import de.dogedevs.photoria.model.entity.components.stats.HealthComponent;
import de.dogedevs.photoria.model.entity.components.stats.LifetimeComponent;
import de.dogedevs.photoria.model.map.ChunkBuffer;
import de.dogedevs.photoria.utils.assets.enums.Textures;

import java.text.DecimalFormat;
import java.util.Random;

import static de.dogedevs.photoria.utils.assets.enums.Textures.*;

/**
 * Created by Furuha on 09.01.2016.
 */
public class ItemManager {

    private Random rand;
    private DecimalFormat df = new DecimalFormat("#.#");

    public ItemManager() {
        rand = new Random();
    }

    public void populateInventory(Entity entity, MobType mobType) {
        InventoryComponent inventory = ComponentMappers.inventory.get(entity);

        if (inventory != null) {
            inventory.slotAttack = generateItem(entity, mobType, ItemComponent.ItemType.ATTACK);
            inventory.slotDefense = generateItem(entity, mobType, ItemComponent.ItemType.DEFENSE);
            inventory.slotRegeneration = generateItem(entity, mobType, ItemComponent.ItemType.REGENERATION);
            inventory.slotStatsUp = generateItem(entity, mobType, ItemComponent.ItemType.STATS_UP);
            inventory.slotOther = generateItem(entity, mobType, ItemComponent.ItemType.OTHER);
        }
    }

    private Entity generateItem(Entity owner, MobType mobType, ItemComponent.ItemType itemType) {
        // Get maxElement
        ElementsComponent ec = ComponentMappers.elements.get(owner);
        if (ec != null) {
            float biggest = ec.blue;
            int maxElemEABiom = ChunkBuffer.BLUE_BIOM;
            if (ec.yellow >= biggest) {
                biggest = ec.yellow;
                maxElemEABiom = ChunkBuffer.YELLOW_BIOM;
            }
            if (ec.red >= biggest) {
                biggest = ec.red;
                maxElemEABiom = ChunkBuffer.RED_BIOM;
            }
            if (ec.purple >= biggest) {
                biggest = ec.purple;
                maxElemEABiom = ChunkBuffer.PURPLE_BIOM;
            }
            if (ec.green >= biggest) {
                maxElemEABiom = ChunkBuffer.GREEN_BIOM;
            }

            switch (mobType) {
                case LOW:
                    switch (itemType) {
                        case ATTACK:
                            return createItem(0.25, 0.01f, 0.05f, maxElemEABiom, ItemComponent.ItemType.ATTACK);
                        case DEFENSE:
                            return createItem(0.25, 0.01f, 0.05f, maxElemEABiom, ItemComponent.ItemType.DEFENSE);
                        case REGENERATION:
                            return createItem(0.15, 0.01f, 0.04f, maxElemEABiom, ItemComponent.ItemType.REGENERATION);
                        case STATS_UP:
                            return createItem(0.1, 0.01f, 0.05f, maxElemEABiom, ItemComponent.ItemType.STATS_UP);
                        case OTHER:
                            return createItem(0.1, 0.0f, 0.01f, maxElemEABiom, ItemComponent.ItemType.OTHER);
                    }
                case NORMAL:
                    switch (itemType) {
                        case ATTACK:
                            return createItem(0.15, 0.05f, 0.07f, maxElemEABiom, ItemComponent.ItemType.ATTACK);
                        case DEFENSE:
                            return createItem(0.15, 0.05f, 0.07f, maxElemEABiom, ItemComponent.ItemType.DEFENSE);
                        case REGENERATION:
                            return createItem(0.15, 0.03f, 0.05f, maxElemEABiom, ItemComponent.ItemType.REGENERATION);
                        case STATS_UP:
                            return createItem(0.15, 0.02f, 0.07f, maxElemEABiom, ItemComponent.ItemType.STATS_UP);
                        case OTHER:
                            return createItem(0.075, 0.01f, 0.02f, maxElemEABiom, ItemComponent.ItemType.OTHER);
                    }
                case HIGH:
                    switch (itemType) {
                        case ATTACK:
                            return createItem(0.05, 0.07f, 0.1f, maxElemEABiom, ItemComponent.ItemType.ATTACK);
                        case DEFENSE:
                            return createItem(0.05, 0.07f, 0.1f, maxElemEABiom, ItemComponent.ItemType.DEFENSE);
                        case REGENERATION:
                            return createItem(0.1, 0.03f, 0.1f, maxElemEABiom, ItemComponent.ItemType.REGENERATION);
                        case STATS_UP:
                            return createItem(0.1, 0.05f, 0.1f, maxElemEABiom, ItemComponent.ItemType.STATS_UP);
                        case OTHER:
                            return createItem(0.05, 0.02f, 0.05f, maxElemEABiom, ItemComponent.ItemType.OTHER);
                    }
                case BOSS:
                    break; // Nothing
            }
        }
        return null;
    }

    private Entity createItem(double p, float minVal, float maxVal, int maxElemEABiom, ItemComponent.ItemType itemType) {
        // Check ProB:
        if (rand.nextDouble() >= p) {
            return null;
        }
        float powerValue = rand.nextFloat() * (maxVal - minVal) + minVal;


        Entity itemEntity = Statics.ashley.createEntity();
        CollisionComponent cc = Statics.ashley.createComponent(CollisionComponent.class);
        cc.ghost = true;
        cc.collisionListener = new CollisionComponent.CollisionListener() {
            @Override
            public boolean onCollision(Entity other, Entity self) {
                if (ComponentMappers.player.has(other)) {
                    InventoryComponent inventory = ComponentMappers.inventory.get(other);
                    addItemToInventory(self, other, inventory);
                    return true;
                }
                return false;
            }
        };
        itemEntity.add(cc);

        ItemComponent ic = Statics.ashley.createComponent(ItemComponent.class);
        SpriteComponent sc = null;

        if (itemType == ItemComponent.ItemType.ATTACK) {
            ic = createAttackItem(powerValue, maxElemEABiom);
            sc = createAttackSprite(maxElemEABiom);
        } else if (itemType == ItemComponent.ItemType.DEFENSE) {
            ic = createDefenseItem(powerValue, maxElemEABiom);
            sc = createDefenseSprite(maxElemEABiom);
        } else if (itemType == ItemComponent.ItemType.REGENERATION) {
            if (maxElemEABiom == ChunkBuffer.PURPLE_BIOM || maxElemEABiom == ChunkBuffer.BLUE_BIOM) {
                boolean energy = (maxElemEABiom == ChunkBuffer.PURPLE_BIOM);
                ic = createRegenerationItem(powerValue, energy);
                sc = createRegenerationSprite(energy);
            }
        } else if (itemType == ItemComponent.ItemType.STATS_UP) {
            if (maxElemEABiom == ChunkBuffer.RED_BIOM || maxElemEABiom == ChunkBuffer.PURPLE_BIOM) {
                boolean energy = (maxElemEABiom == ChunkBuffer.PURPLE_BIOM);
                ic = createStatsUpItem(powerValue, energy);
                sc = createStatsUpSprite(energy);
            }
        } else if (itemType == ItemComponent.ItemType.OTHER) {
            if (maxElemEABiom == ChunkBuffer.GREEN_BIOM || maxElemEABiom == ChunkBuffer.YELLOW_BIOM) {
                ic = createOtherItem(powerValue);
                sc = createOtherSprite(maxElemEABiom == ChunkBuffer.GREEN_BIOM);
            }
        }

        if (ic != null && sc != null) {
            ic.type = itemType;
            itemEntity.add(ic);
            itemEntity.add(sc);
            Statics.ashley.addEntity(itemEntity);
        }

        return itemEntity;
    }

    private ItemComponent createOtherItem(float value) {
        ItemComponent ic = Statics.ashley.createComponent(ItemComponent.class);
        ic.name = "Shoes of speed";
        ic.description = "\nYour movement speed is increased by " + df.format((value * 100)) + "%";
        ic.movementSpeed = value;
        return ic;
    }

    private ItemComponent createStatsUpItem(float value, boolean energy) {
        ItemComponent ic = Statics.ashley.createComponent(ItemComponent.class);
        if (energy) {
            ic.name = "Energy increase";
            ic.description = "\nYour maximum energy is increased by " + df.format((value * 100)) + "%";
            ic.maxEnergy = value;
        } else {
            ic.name = "Health increase";
            ic.description = "\nYour health is increased by +" + df.format((value * 100)) + "%";
            ic.maxLife = value;
        }
        return ic;
    }

    private ItemComponent createRegenerationItem(float value, boolean energy) {
        ItemComponent ic = Statics.ashley.createComponent(ItemComponent.class);
        if (energy) {
            ic.name = "Energy regeneration";
            ic.description = "\nYour energy will regenerate faster +" + df.format((value * 100)) + "%";
            ic.energyReg = value;
        } else {
            ic.name = "Health regeneration";
            ic.description = "\nYour health will regenerate faster +" + df.format((value * 100)) + "%";
            ic.lifeReg = value;
        }
        return ic;
    }

    private ItemComponent createDefenseItem(float value, int maxElemEABiom) {
        ItemComponent ic = Statics.ashley.createComponent(ItemComponent.class);
        switch (maxElemEABiom) {
            case ChunkBuffer.BLUE_BIOM:
                ic.name = "Water defense";
                ic.description = "\nWater defense +" + df.format((value * 100)) + "%";
                ic.defElementBlue = value;
                break;
            case ChunkBuffer.RED_BIOM:
                ic.name = "Fire defense";
                ic.description = "\nFire defense +" + df.format((value * 100)) + "%";
                ic.defElementRed = value;
                break;
            case ChunkBuffer.GREEN_BIOM:
                ic.name = "Slime defense";
                ic.description = "\nSlime defense +" + df.format((value * 100)) + "%";
                ic.defElementGreen = value;
                break;
            case ChunkBuffer.YELLOW_BIOM:
                ic.name = "Laser defense";
                ic.description = "\nLaser defense +" + df.format((value * 100)) + "%";
                ic.defElementYellow = value;
                break;
            case ChunkBuffer.PURPLE_BIOM:
                ic.name = "Energy defense";
                ic.description = "\nEnergy defense +" + df.format((value * 100)) + "%";
                ic.defElementPurple = value;
                break;
        }
        return ic;
    }

    private ItemComponent createAttackItem(float value, int maxElemEABiom) {
        ItemComponent ic = Statics.ashley.createComponent(ItemComponent.class);
        switch (maxElemEABiom) {
            case ChunkBuffer.BLUE_BIOM:
                ic.name = "Water sword";
                ic.description = "\nWater damage +" + df.format((value * 100)) + "%";
                ic.dmgElementBlue = value;
                break;
            case ChunkBuffer.RED_BIOM:
                ic.name = "Fire sword";
                ic.description = "\nFire damage +" + df.format((value * 100)) + "%";
                ic.dmgElementRed = value;
                break;
            case ChunkBuffer.GREEN_BIOM:
                ic.name = "Slime sword";
                ic.description = "\nSlime damage +" + df.format((value * 100)) + "%";
                ic.dmgElementGreen = value;
                break;
            case ChunkBuffer.YELLOW_BIOM:
                ic.name = "Laser sword";
                ic.description = "\nLaser damage +" + df.format((value * 100)) + "%";
                ic.dmgElementYellow = value;
                break;
            case ChunkBuffer.PURPLE_BIOM:
                ic.name = "Energy sword";
                ic.description = "\nEnergy damage +" + df.format((value * 100)) + "%";
                ic.dmgElementPurple = value;
                break;
        }
        return ic;
    }

    private SpriteComponent createAttackSprite(int maxElemEABiom) {
        SpriteComponent sc = Statics.ashley.createComponent(SpriteComponent.class);
        switch (maxElemEABiom) {
            case ChunkBuffer.BLUE_BIOM:
                sc.region = new TextureRegion(Statics.asset.getTexture(Textures.ITEM_SWORD_BLUE));
                break;
            case ChunkBuffer.RED_BIOM:
                sc.region = new TextureRegion(Statics.asset.getTexture(Textures.ITEM_SWORD_RED));
                break;
            case ChunkBuffer.GREEN_BIOM:
                sc.region = new TextureRegion(Statics.asset.getTexture(Textures.ITEM_SWORD_GREEN));
                break;
            case ChunkBuffer.YELLOW_BIOM:
                sc.region = new TextureRegion(Statics.asset.getTexture(Textures.ITEM_SWORD_YELLOW));
                break;
            case ChunkBuffer.PURPLE_BIOM:
                sc.region = new TextureRegion(Statics.asset.getTexture(Textures.ITEM_SWORD_PURPLE));
                break;
        }

        return sc;
    }

    private SpriteComponent createDefenseSprite(int maxElemEABiom) {
        SpriteComponent sc = Statics.ashley.createComponent(SpriteComponent.class);
        switch (maxElemEABiom) {
            case ChunkBuffer.BLUE_BIOM:
                sc.region = new TextureRegion(Statics.asset.getTexture(Textures.ITEM_RESISTANCE_BLUE));
                break;
            case ChunkBuffer.RED_BIOM:
                sc.region = new TextureRegion(Statics.asset.getTexture(Textures.ITEM_RESISTANCE_RED));
                break;
            case ChunkBuffer.GREEN_BIOM:
                sc.region = new TextureRegion(Statics.asset.getTexture(Textures.ITEM_RESISTANCE_GREEN));
                break;
            case ChunkBuffer.YELLOW_BIOM:
                sc.region = new TextureRegion(Statics.asset.getTexture(Textures.ITEM_RESISTANCE_YELLOW));
                break;
            case ChunkBuffer.PURPLE_BIOM:
                sc.region = new TextureRegion(Statics.asset.getTexture(Textures.ITEM_RESISTANCE_PURPLE));
                break;
        }
        return sc;

    }

    private SpriteComponent createRegenerationSprite(boolean energy) {
        SpriteComponent sc = Statics.ashley.createComponent(SpriteComponent.class);
        if (energy) {
            sc.region = new TextureRegion(Statics.asset.getTexture(Textures.ITEM_ENERGY));
        } else {
            sc.region = new TextureRegion(Statics.asset.getTexture(Textures.ITEM_LIFE));
        }
        return sc;
    }

    private SpriteComponent createStatsUpSprite(boolean energy) {
        SpriteComponent sc = Statics.ashley.createComponent(SpriteComponent.class);
        if (energy) {
            sc.region = new TextureRegion(Statics.asset.getTexture(Textures.ITEM_BOOK_ENERGY));
        } else {
            sc.region = new TextureRegion(Statics.asset.getTexture(Textures.ITEM_BOOK_LIFE));
        }
        return sc;
    }

    private SpriteComponent createOtherSprite(boolean green) {
        SpriteComponent sc = Statics.ashley.createComponent(SpriteComponent.class);
        if (green) {
            sc.region = new TextureRegion(Statics.asset.getTexture(Textures.ITEM_SHOE_GREEN));
        } else {
            sc.region = new TextureRegion(Statics.asset.getTexture(Textures.ITEM_SHOE_YELLOW));
        }
        return sc;
    }


    public void addItemToInventory(Entity item, Entity player, InventoryComponent inventory) {
        if (inventory != null && item != null) {
            ItemComponent itemComponent = ComponentMappers.item.get(item);
            PositionComponent position = ComponentMappers.position.get(item);
            HealthComponent hc = ComponentMappers.health.get(player);
            EnergyComponent ec = ComponentMappers.energy.get(player);
//                    position.y -= 32;
//            position.x = position.x - 10;
            switch (itemComponent.type) {
                case ATTACK:
                    dropItem(inventory.slotAttack, position, ItemComponent.ItemType.ATTACK);
                    inventory.slotAttack = item;
                    break;
                case DEFENSE:
                    dropItem(inventory.slotDefense, position, ItemComponent.ItemType.DEFENSE);
                    inventory.slotDefense = item;
                    break;
                case REGENERATION:
                    dropItem(inventory.slotRegeneration, position, ItemComponent.ItemType.REGENERATION);
                    if(ec != null) {
                        ec.regEnergySecUse = ec.regEnergySec + (itemComponent.energyReg * ec.regEnergySec);
                    }
                    if(hc != null) {
                        hc.regHealthSecUse = hc.regHealthSec + (itemComponent.lifeReg * hc.regHealthSec);
                    }
                    inventory.slotRegeneration = item;
                    break;
                case STATS_UP:
                    dropItem(inventory.slotStatsUp, position, ItemComponent.ItemType.STATS_UP);
                    if (ec != null) {
                        ec.maxEnergyUse = ec.maxEnergy + (itemComponent.maxEnergy * ec.maxEnergy);
                    }

                    if(hc != null) {
                        hc.maxHealthUse = hc.maxHealth + (itemComponent.maxLife * hc.maxHealth);
                    }
                    inventory.slotStatsUp = item;
                    break;
                case OTHER:
                    dropItem(inventory.slotOther, position, ItemComponent.ItemType.OTHER);
                    inventory.slotOther = item;
                    break;
                case USE:
                    inventory.slotUse.add(item);
                    break;
            }
            LifetimeComponent lc = ComponentMappers.lifetime.get(item);
            if (lc != null) {
                item.remove(LifetimeComponent.class);
            }
            item.remove(PositionComponent.class);
        }
    }

    private ElementsComponent createElementsForOre(Textures oreTexture) {
        ElementsComponent ec = Statics.ashley.createComponent(ElementsComponent.class);
        float ndtm = 20; // number of drops until max;
        switch (oreTexture) {
            case ORE_BLUE:
                ec.blue = 1 / ndtm;
                ec.yellow = -(1 / ndtm) / 3f;
                ec.red = -(1 / ndtm) / 3f;
                ec.purple = -(1 / ndtm) / 3f;
                ec.green = -(1 / ndtm) / 3f;
                break;
            case ORE_YELLOW:
                ec.blue = -(1 / ndtm) / 3f;
                ec.yellow = 1 / ndtm;
                ec.red = -(1 / ndtm) / 3f;
                ec.purple = -(1 / ndtm) / 3f;
                ec.green = -(1 / ndtm) / 3f;
                break;
            case ORE_RED:
                ec.blue = -(1 / ndtm) / 3f;
                ec.yellow = -(1 / ndtm) / 3f;
                ec.red = 1 / ndtm;
                ec.purple = -(1 / ndtm) / 3f;
                ec.green = -(1 / ndtm) / 3f;
                break;
            case ORE_PURPLE:
                ec.blue = -(1 / ndtm)/ 2f;
                ec.yellow = -(1 / ndtm)/ 2f;
                ec.red = -(1 / ndtm)/ 2f;
                ec.purple = 1 / ndtm;
                ec.green = -(1 / ndtm)/ 2f;
                break;
            case ORE_GREEN:
                ec.blue = -(1 / ndtm)/ 2f;
                ec.yellow = -(1 / ndtm)/ 2f;
                ec.red = -(1 / ndtm)/ 2f;
                ec.purple = -(1 / ndtm)/ 2f;
                ec.green = 1 / ndtm;
                break;
        }


        return ec;
    }

    public void createGemDrop(ElementsComponent baseElements, PositionComponent positionComponent) {
        Entity entity = Statics.ashley.createEntity();

        Textures color;
        float biggest = baseElements.blue;
        color = ORE_BLUE;
        if (baseElements.yellow >= biggest) {
            biggest = baseElements.yellow;
            color = ORE_YELLOW;
        }
        if (baseElements.red >= biggest) {
            biggest = baseElements.red;
            color = ORE_RED;
        }
        if (baseElements.purple >= biggest) {
            biggest = baseElements.purple;
            color = ORE_PURPLE;
        }
        if (baseElements.green >= biggest) {
            color = ORE_GREEN;
        }

        ElementsComponent ec = createElementsForOre(color);

        entity.add(ec);
        SpriteComponent sc = Statics.ashley.createComponent(SpriteComponent.class);
        sc.region = new TextureRegion(Statics.asset.getTexture(color));
        entity.add(sc);


        CollisionComponent cc = Statics.ashley.createComponent(CollisionComponent.class);
        cc.ghost = true;
        cc.size = 25;
        cc.collisionListener = new CollisionComponent.CollisionListener() {
            @Override
            public boolean onCollision(Entity other, Entity self) {
                if (ComponentMappers.player.has(other)) {
                    ElementsComponent playerEc = ComponentMappers.elements.get(other);
                    ElementsComponent ec = ComponentMappers.elements.get(self);
                    if (ec != null && playerEc != null) {
                        playerEc.blue += ec.blue;
                        playerEc.yellow += ec.yellow;
                        playerEc.red += ec.red;
                        playerEc.purple += ec.purple;
                        playerEc.green += ec.green;

                        playerEc.blue = MathUtils.clamp(playerEc.blue, 0.1f, 1f);
                        playerEc.yellow = MathUtils.clamp(playerEc.yellow, 0.1f, 1f);
                        playerEc.red = MathUtils.clamp(playerEc.red, 0.1f, 1f);
                        playerEc.purple = MathUtils.clamp(playerEc.purple, 0.1f, 1f);
                        playerEc.green = MathUtils.clamp(playerEc.green, 0.1f, 1f);
                    }
                    Statics.attack.deleteWeaponsFrom(other);
                    Statics.attack.loadWeapon(other);
                    Statics.ashley.removeEntity(self);
                    return true;
                }
                return false;
            }
        };
        entity.add(cc);

        dropItem(entity, positionComponent, null);

        Statics.ashley.addEntity(entity);
    }

    public void dropItem(Entity item, PositionComponent positionComponent, ItemComponent.ItemType type) {
        if (item != null) {
            LifetimeComponent lc = Statics.ashley.createComponent(LifetimeComponent.class);
            lc.maxTime = 10;
            item.add(lc);
            PositionComponent pc = Statics.ashley.createComponent(PositionComponent.class);
            if (type != null) {
                switch (type) {
                    case ATTACK:
                        positionComponent.x -= 40;
                        positionComponent.y += 40;
                        break;
                    case DEFENSE:
                        positionComponent.y += 40;
                        break;
                    case REGENERATION:
                        positionComponent.x += 40;
                        positionComponent.y += 40;
                        break;
                    case STATS_UP:
                        positionComponent.x -= 40;
                        break;
                    case OTHER:
                        positionComponent.x += 40;
                        break;
                    case USE:
                        positionComponent.y -= 40;
                        break;
                }
            }
            pc.x = positionComponent.x;
            pc.y = positionComponent.y;
            item.add(pc);
        }
    }
}
