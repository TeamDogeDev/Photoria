package de.dogedevs.photoria.model.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.content.ai.EscapeOnDamageAi;
import de.dogedevs.photoria.content.ai.FollowAi;
import de.dogedevs.photoria.content.mob.MobAttribute;
import de.dogedevs.photoria.content.mob.MobTemplate;
import de.dogedevs.photoria.content.mob.MobType;
import de.dogedevs.photoria.content.weapons.*;
import de.dogedevs.photoria.model.entity.components.*;
import de.dogedevs.photoria.model.entity.components.rendering.AnimationComponent;
import de.dogedevs.photoria.model.entity.components.rendering.RenderAsTileComponent;
import de.dogedevs.photoria.model.entity.components.rendering.SpriteComponent;
import de.dogedevs.photoria.model.entity.components.stats.ElementsComponent;
import de.dogedevs.photoria.model.entity.components.stats.EnergyComponent;
import de.dogedevs.photoria.model.entity.components.stats.HealthComponent;
import de.dogedevs.photoria.model.map.ChunkBuffer;
import de.dogedevs.photoria.model.map.ChunkCell;
import de.dogedevs.photoria.rendering.tiles.Tile;
import de.dogedevs.photoria.rendering.tiles.TileCollisionMapper;
import de.dogedevs.photoria.rendering.tiles.TileMapper;
import de.dogedevs.photoria.utils.assets.ParticlePool;
import de.dogedevs.photoria.utils.assets.enums.Sounds;
import de.dogedevs.photoria.utils.assets.enums.Textures;

/**
 * Created by Furuha on 02.01.2016.
 */
public class EntityLoader {

    PooledEngine ashley = Statics.ashley;
    public void createChunkEntities(int chunkX, int chunkY, long seed, ChunkBuffer buffer){
//        long start = System.currentTimeMillis();
        int numEntities = 100;
        ashley = Statics.ashley;

        for (int i = 0; i < numEntities; i++) {
            createRandomEntity(MathUtils.random(chunkX * 64 * 32, chunkX * 64 * 32 + 2048), MathUtils.random(chunkY * 64 * 32, chunkY * 64 * 32 + 2048), buffer);
        }

        RandomXS128 rnd = new RandomXS128(seed+chunkX+chunkY);
        for (int i = 0; i < 50; i++) {
            float x = ((chunkX * 64 * 32) + (int)(rnd.nextFloat()*64)*32);
            float y = ((chunkY * 64 * 32) + (int)(rnd.nextFloat()*64)*32);
            createRandomDecoEntity(x, y, buffer, rnd);
        }
//        MainGame.log("ec time: "+(System.currentTimeMillis()-start));
    }

    private void createRandomEntity(float x, float y, ChunkBuffer buffer){
        ChunkCell biomCell = buffer.getCellLazy((int)x/32, (int)y/32, ChunkBuffer.BIOME);
        ChunkCell collisionCell = buffer.getCellLazy((int)x/32, (int)y/32, ChunkBuffer.COLLISION);
        if(biomCell == null){
            return;
        }
        if(collisionCell.value == TileCollisionMapper.GROUND || collisionCell.value == TileCollisionMapper.HIGH_GROUND) {
//                createSlime(Textures.SLIME_BLUE, x, y);
            MobTemplate mob = Statics.mob.getRandomTemplateForBiome(biomCell.value);
            createMob(mob, x, y);
        }
    }

    private void createRandomDecoEntity(float x, float y, ChunkBuffer buffer, RandomXS128 rnd){
        ChunkCell cell = buffer.getCellLazy((int)x/32, (int)y/32, ChunkBuffer.COLLISION);
        if(cell == null){
            return;
        }
        if(cell.value == TileCollisionMapper.HIGH_GROUND){
            createLavaDeco(x, y, buffer, rnd);
        } else if(cell.value == TileCollisionMapper.GROUND){
            createStoneDeco(x, y, buffer, rnd);
        } else if(cell.value == TileCollisionMapper.HIGH_GROUND_FLUID){
            if(rnd.nextInt(10) == 0 ){
                createLavaAmbientSound(x, y);
            }
        } else if(cell.value == TileCollisionMapper.FLUID){

        }
    }

    private void createLavaAmbientSound(float x, float y) {
        Entity entity = ashley.createEntity();
        PositionComponent pc = ashley.createComponent(PositionComponent.class);
        pc.x = x;
        pc.y = y;
        entity.add(pc);
        SoundComponent sc = ashley.createComponent(SoundComponent.class);
        sc.ambientSound = Sounds.AMBIENT_BUBBLES;
        sc.lastAmbientSoundDif = 10;
        entity.add(sc);
        ashley.addEntity(entity);
    }

    public void createTerraFormingRamp(float x, float y, ChunkBuffer buffer) {
        x = x-(x%32);
        y = y-(y%32);
        float yStart = y;
        //Check valid position here
        boolean valid = false;
        for(;y > yStart-(32*5); y = y-32){
            int value = buffer.getCellLazy((int) x / 32, (int) y / 32, ChunkBuffer.DECO1).value;
            if(value == TileMapper.LAVA_STONE_BOTTOM_MIDDLE_2){
                int value2 = buffer.getCellLazy(((int) x / 32) - 1, (int) y / 32, ChunkBuffer.DECO1).value;
                int value3 = buffer.getCellLazy(((int) x / 32) + 1, (int) y / 32, ChunkBuffer.DECO1).value;
                int value4 = buffer.getCellLazy(((int) x / 32) - 1, (int) y / 32, ChunkBuffer.COLLISION).value;
                int value5 = buffer.getCellLazy(((int) x / 32) + 1, (int) y / 32, ChunkBuffer.COLLISION).value;
                if(value2 == TileMapper.LAVA_STONE_BOTTOM_MIDDLE_2 && value3 == TileMapper.LAVA_STONE_BOTTOM_MIDDLE_2 && value4 == TileCollisionMapper.HIGH_GROUND_BORDER && value5 == TileCollisionMapper.HIGH_GROUND_BORDER){
                    valid = true;
                    break;
                }
            }
        }
        if(!valid){
            return;
        }

        Statics.particle.createParticleAt(ParticlePool.ParticleType.TERRAFORMING, x+16, y+64);

        //MID BOT
        Entity entity = ashley.createEntity();
        PositionComponent pc = ashley.createComponent(PositionComponent.class);
        pc.x = x;
        pc.y = y;
        entity.add(pc);
        SpriteComponent sc = ashley.createComponent(SpriteComponent.class);
        sc.region = Tile.getTileForBiome(TileMapper.TERRA_FORMING_MID_BOT, buffer.getCellLazy((int)pc.x/32, (int)pc.y/32, ChunkBuffer.BIOME).value).getTextureRegion();
        entity.add(sc);
        MapCollisionComponent mc = ashley.createComponent(MapCollisionComponent.class);
        mc.value = TileCollisionMapper.HIGH_GROUND;
        entity.add(mc);
        entity.add(ashley.createComponent(RenderAsTileComponent.class));
        entity.add(ashley.createComponent(AvoidGcComponent.class));
        ashley.addEntity(entity);

        //MID MID
        entity = ashley.createEntity();
        pc = ashley.createComponent(PositionComponent.class);
        pc.x = x;
        pc.y = y+32;
        entity.add(pc);
        sc = ashley.createComponent(SpriteComponent.class);
        sc.region = Tile.getTileForBiome(TileMapper.TERRA_FORMING_MID, buffer.getCellLazy((int)pc.x/32, (int)pc.y/32, ChunkBuffer.BIOME).value).getTextureRegion();
        entity.add(sc);
        mc = ashley.createComponent(MapCollisionComponent.class);
        mc.value = TileCollisionMapper.HIGH_GROUND;
        entity.add(mc);
        entity.add(ashley.createComponent(RenderAsTileComponent.class));
        entity.add(ashley.createComponent(AvoidGcComponent.class));
        ashley.addEntity(entity);

        //MID TOP
        entity = ashley.createEntity();
        pc = ashley.createComponent(PositionComponent.class);
        pc.x = x;
        pc.y = y+64;
        entity.add(pc);
        sc = ashley.createComponent(SpriteComponent.class);
        sc.region = Tile.getTileForBiome(TileMapper.TERRA_FORMING_MID_TOP, buffer.getCellLazy((int)pc.x/32, (int)pc.y/32, ChunkBuffer.BIOME).value).getTextureRegion();
        entity.add(sc);
        mc = ashley.createComponent(MapCollisionComponent.class);
        mc.value = TileCollisionMapper.HIGH_GROUND;
        entity.add(mc);
        entity.add(ashley.createComponent(RenderAsTileComponent.class));
        entity.add(ashley.createComponent(AvoidGcComponent.class));
        ashley.addEntity(entity);

        //LEFT
        float xLeft = x-32;
        //LEFT BOT
        entity = ashley.createEntity();
        pc = ashley.createComponent(PositionComponent.class);
        pc.x = xLeft;
        pc.y = y;
        entity.add(pc);
        sc = ashley.createComponent(SpriteComponent.class);
        sc.region = Tile.getTileForBiome(TileMapper.TERRA_FORMING_LEFT_BOT, buffer.getCellLazy((int)pc.x/32, (int)pc.y/32, ChunkBuffer.BIOME).value).getTextureRegion();
        entity.add(sc);
        mc = ashley.createComponent(MapCollisionComponent.class);
        mc.value = TileCollisionMapper.ENTITY;
        entity.add(mc);
        entity.add(ashley.createComponent(RenderAsTileComponent.class));
        entity.add(ashley.createComponent(AvoidGcComponent.class));
        ashley.addEntity(entity);

        //LEFT MID
        entity = ashley.createEntity();
        pc = ashley.createComponent(PositionComponent.class);
        pc.x = xLeft;
        pc.y = y+32;
        entity.add(pc);
        sc = ashley.createComponent(SpriteComponent.class);
        sc.region = Tile.getTileForBiome(TileMapper.TERRA_FORMING_LEFT, buffer.getCellLazy((int)pc.x/32, (int)pc.y/32, ChunkBuffer.BIOME).value).getTextureRegion();
        entity.add(sc);
        mc = ashley.createComponent(MapCollisionComponent.class);
        mc.value = TileCollisionMapper.ENTITY;
        entity.add(mc);
        entity.add(ashley.createComponent(RenderAsTileComponent.class));
        entity.add(ashley.createComponent(AvoidGcComponent.class));
        ashley.addEntity(entity);

        //LEFT TOP
        entity = ashley.createEntity();
        pc = ashley.createComponent(PositionComponent.class);
        pc.x = xLeft;
        pc.y = y+64;
        entity.add(pc);
        sc = ashley.createComponent(SpriteComponent.class);
        sc.region = Tile.getTileForBiome(TileMapper.TERRA_FORMING_LEFT_TOP, buffer.getCellLazy((int)pc.x/32, (int)pc.y/32, ChunkBuffer.BIOME).value).getTextureRegion();
        entity.add(sc);
        mc = ashley.createComponent(MapCollisionComponent.class);
        mc.value = TileCollisionMapper.ENTITY;
        entity.add(mc);
        entity.add(ashley.createComponent(RenderAsTileComponent.class));
        entity.add(ashley.createComponent(AvoidGcComponent.class));
        ashley.addEntity(entity);

        //RIGHT
        float xRight = x+32;
        //RIGHT BOT
        entity = ashley.createEntity();
        pc = ashley.createComponent(PositionComponent.class);
        pc.x = xRight;
        pc.y = y;
        entity.add(pc);
        sc = ashley.createComponent(SpriteComponent.class);
        sc.region = Tile.getTileForBiome(TileMapper.TERRA_FORMING_RIGHT_BOT, buffer.getCellLazy((int)pc.x/32, (int)pc.y/32, ChunkBuffer.BIOME).value).getTextureRegion();
        entity.add(sc);
        mc = ashley.createComponent(MapCollisionComponent.class);
        mc.value = TileCollisionMapper.ENTITY;
        entity.add(mc);
        entity.add(ashley.createComponent(RenderAsTileComponent.class));
        entity.add(ashley.createComponent(AvoidGcComponent.class));
        ashley.addEntity(entity);

        //RIGHT MID
        entity = ashley.createEntity();
        pc = ashley.createComponent(PositionComponent.class);
        pc.x = xRight;
        pc.y = y+32;
        entity.add(pc);
        sc = ashley.createComponent(SpriteComponent.class);
        sc.region = Tile.getTileForBiome(TileMapper.TERRA_FORMING_RIGHT, buffer.getCellLazy((int)pc.x/32, (int)pc.y/32, ChunkBuffer.BIOME).value).getTextureRegion();
        entity.add(sc);
        mc = ashley.createComponent(MapCollisionComponent.class);
        mc.value = TileCollisionMapper.ENTITY;
        entity.add(mc);
        entity.add(ashley.createComponent(RenderAsTileComponent.class));
        entity.add(ashley.createComponent(AvoidGcComponent.class));
        ashley.addEntity(entity);

        //RIGHT TOP
        entity = ashley.createEntity();
        pc = ashley.createComponent(PositionComponent.class);
        pc.x = xRight;
        pc.y = y+64;
        entity.add(pc);
        sc = ashley.createComponent(SpriteComponent.class);
        sc.region = Tile.getTileForBiome(TileMapper.TERRA_FORMING_RIGHT_TOP, buffer.getCellLazy((int)pc.x/32, (int)pc.y/32, ChunkBuffer.BIOME).value).getTextureRegion();
        entity.add(sc);
        mc = ashley.createComponent(MapCollisionComponent.class);
        mc.value = TileCollisionMapper.ENTITY;
        entity.add(mc);
        entity.add(ashley.createComponent(RenderAsTileComponent.class));
        entity.add(ashley.createComponent(AvoidGcComponent.class));
        ashley.addEntity(entity);
    }

    private void createLavaDeco(float x, float y, ChunkBuffer buffer, RandomXS128 rnd) {
        Entity entity = ashley.createEntity();
        PositionComponent pc = ashley.createComponent(PositionComponent.class);
        pc.x = x;
        pc.y = y;
        entity.add(pc);
        SpriteComponent sc = ashley.createComponent(SpriteComponent.class);
//        sc.region = Tile.LAVA_DECO_1.getTextureRegion();
        sc.region = Tile.getTileForBiome(rnd.nextBoolean() ? TileMapper.LAVA_DECO_1 : TileMapper.LAVA_DECO_2, buffer.getCellLazy((int)x/32, (int)y/32, ChunkBuffer.BIOME).value).getTextureRegion();
        entity.add(sc);
        MapCollisionComponent mc = ashley.createComponent(MapCollisionComponent.class);
        mc.value = TileCollisionMapper.ENTITY;
        entity.add(mc);
        ashley.addEntity(entity);
    }

    private void createStoneDeco(float x, float y, ChunkBuffer buffer, RandomXS128 rnd) {
        Entity entity = ashley.createEntity();
        PositionComponent pc = ashley.createComponent(PositionComponent.class);
        pc.x = x;
        pc.y = y;
        entity.add(pc);
        SpriteComponent sc = ashley.createComponent(SpriteComponent.class);
        sc.region = Tile.getTileForBiome(rnd.nextBoolean() ? TileMapper.STONE_DECO_0 : TileMapper.STONE_DECO_1, buffer.getCellLazy((int) x/32, (int) y/32, ChunkBuffer.BIOME).value).getTextureRegion();
        entity.add(sc);
        CollisionComponent cc = ashley.createComponent(CollisionComponent.class);
        cc.ghost = false;
        cc.projectile = false;
        entity.add(cc);
        MapCollisionComponent mc = ashley.createComponent(MapCollisionComponent.class);
        mc.value = TileCollisionMapper.ENTITY;
        entity.add(mc);
        ashley.addEntity(entity);
    }

    private void createMob(final MobTemplate template, float x, float y){
        if(template == null){
            return;
        }
        Entity entity = ashley.createEntity();
        ashley.addEntity(entity);
        PositionComponent pc = ashley.createComponent(PositionComponent.class);
        pc.x = x;
        pc.y = y;
        entity.add(pc);

        Animation[] animations = Statics.animation.getMovementAnimations(template.texture, true, 4, 3);
        Animation walkAnimationU = animations[0];
        Animation walkAnimationD = animations[1];
        Animation walkAnimationL = animations[2];
        Animation walkAnimationR = animations[3];
        AnimationComponent ac = ashley.createComponent(AnimationComponent.class);
        ac.idleAnimation = walkAnimationD;
        ac.leftAnimation = walkAnimationL;
        ac.rightAnimation = walkAnimationR;
        ac.upAnimation = walkAnimationU;
        ac.downAnimation = walkAnimationD;
        entity.add(ac);

        CollisionComponent cc = ashley.createComponent(CollisionComponent.class);
        cc.groundCollision = TileCollisionMapper.normalBorderCollision;
        cc.collisionListener = new CollisionComponent.CollisionListener() {
            @Override
            public boolean onCollision(Entity other, Entity self) {
                if(ComponentMappers.player.has(other)){
                    HealthComponent hc = ComponentMappers.health.get(other);
                    if(hc.immuneTime == 0){
                        hc.health -= template.baseDamage;
                        hc.health = MathUtils.clamp(hc.health, 0, hc.maxHealthUse);
                        hc.immuneTime = hc.maxImmuneTime;
                    }
                }
                return false;
            }
        };
        entity.add(cc);

        ElementsComponent ec = ashley.createComponent(ElementsComponent.class);
        ec.blue = template.blue;
        ec.red = template.red;
        ec.green = template.green;
        ec.purple = template.purple;
        ec.yellow = template.yellow;
        entity.add(ec);

        AiComponent aiComponent = ashley.createComponent(AiComponent.class);
        switch (template.ai){
            case FOLLOW:
                aiComponent.ai = new FollowAi();
                break;
            case ESCAPE:
                aiComponent.ai = new EscapeOnDamageAi();
                break;
        }
        entity.add(aiComponent);

        InventoryComponent ic = ashley.createComponent(InventoryComponent.class);
        entity.add(ic);
        Statics.item.populateInventory(entity, template.type);

//        if(template.type != MobType.LOW && template.attributes.contains(MobAttribute.MULTIPLY)){
//            ic.slotUse.add(createSubMob(template, MobType.NORMAL));
//            ic.slotUse.add(createSubMob(template, MobType.NORMAL));
//            ic.slotUse.add(createSubMob(template, MobType.NORMAL));
//            ic.slotUse.add(createSubMob(template, MobType.NORMAL));
//        }

        HealthComponent hc = ashley.createComponent(HealthComponent.class);
        hc.maxHealth = template.maxHealth;
        hc.maxHealthUse = template.maxHealth;
        hc.health = template.maxHealth;
        entity.add(hc);

        EnergyComponent ecc = ashley.createComponent(EnergyComponent.class);
        ecc.maxEnergy = template.maxHealth;
        ecc.energy = template.maxHealth;
        entity.add(ecc);

        VelocityComponent vc = ashley.createComponent(VelocityComponent.class);
        vc.direction = MathUtils.random(0, 7);
        vc.speed = template.speed;
        entity.add(vc);

        TargetComponent target = Statics.ashley.createComponent(TargetComponent.class);
        entity.add(target);
        Weapon weapon;
        switch (template.weapon){
            case NEUTRAL:
                weapon = new Shooter();
                break;
            case LASER:
                weapon = new Laser();
                break;
            case FLAMETHROWER:
                weapon = new Flamethrower();
                break;
            case WATERTHROWER:
                weapon = new Watercannon();
                break;
            case SLIMEBALLS:
                weapon = new AcidShooter();
                break;
            case ENERGYGUN:
                weapon = new ParticleShooter();
                break;
            default:
                weapon = new Shooter();
                break;
        }
        weapon.setRange(template.range);
        Statics.attack.createAttack(entity, weapon);
        target.isShooting = false;

        SoundComponent sc = Statics.ashley.createComponent(SoundComponent.class);
        sc.shotSound = template.shotSound;
        sc.moveSound = template.movementSound;
        sc.deathSound = template.deathSound;
        sc.hitSound = template.hitSound;
        entity.add(sc);

    }

    private Entity createSubMob(final MobTemplate template, MobType type){
        Entity entity = ashley.createEntity();
        ashley.addEntity(entity);
        Textures tex = template.texture;
        switch (tex){
            case SLIME_BLUE:
                break;
            case SLIME_GREEN:
                break;
            case SLIME_PURPLE:
                break;
            case SLIME_RED:
                break;
            case SLIME_YELLOW:
                break;
            case YELLOW_BOSS:
                tex = Textures.SLIME_YELLOW;
                break;
        }
        Animation[] animations = Statics.animation.getMovementAnimations(tex, true, 4, 3);
        Animation walkAnimationU = animations[0];
        Animation walkAnimationD = animations[1];
        Animation walkAnimationL = animations[2];
        Animation walkAnimationR = animations[3];
        AnimationComponent ac = ashley.createComponent(AnimationComponent.class);
        ac.idleAnimation = walkAnimationD;
        ac.leftAnimation = walkAnimationL;
        ac.rightAnimation = walkAnimationR;
        ac.upAnimation = walkAnimationU;
        ac.downAnimation = walkAnimationD;
        entity.add(ac);

        CollisionComponent cc = ashley.createComponent(CollisionComponent.class);
        cc.groundCollision = TileCollisionMapper.normalBorderCollision;
        cc.collisionListener = new CollisionComponent.CollisionListener() {
            @Override
            public boolean onCollision(Entity other, Entity self) {
                if(ComponentMappers.player.has(other)){
                    HealthComponent hc = ComponentMappers.health.get(other);
                    if(hc.immuneTime == 0){
                        hc.health -= template.baseDamage;
                        hc.health = MathUtils.clamp(hc.health, 0, hc.maxHealthUse);
                        hc.immuneTime = hc.maxImmuneTime;
                    }
                }
                return false;
            }
        };
        entity.add(cc);

        ElementsComponent ec = ashley.createComponent(ElementsComponent.class);
        ec.blue = template.blue;
        ec.red = template.red;
        ec.green = template.green;
        ec.purple = template.purple;
        ec.yellow = template.yellow;
        entity.add(ec);

        AiComponent aiComponent = ashley.createComponent(AiComponent.class);
        switch (template.ai){
            case FOLLOW:
                aiComponent.ai = new FollowAi();
                break;
            case ESCAPE:
                aiComponent.ai = new EscapeOnDamageAi();
                break;
        }
        entity.add(aiComponent);

        InventoryComponent ic = ashley.createComponent(InventoryComponent.class);
        entity.add(ic);
        Statics.item.populateInventory(entity, template.type);

        if(type != MobType.LOW && template.attributes.contains(MobAttribute.MULTIPLY)){
            ic.slotUse.add(createSubMob(template, MobType.LOW));
            ic.slotUse.add(createSubMob(template, MobType.LOW));
            ic.slotUse.add(createSubMob(template, MobType.LOW));
            ic.slotUse.add(createSubMob(template, MobType.LOW));
        }

        HealthComponent hc = ashley.createComponent(HealthComponent.class);
        hc.maxHealth = template.maxHealth;
        hc.maxHealthUse = template.maxHealth;
        hc.health = template.maxHealth;
        entity.add(hc);

        VelocityComponent vc = ashley.createComponent(VelocityComponent.class);
        vc.direction = MathUtils.random(0, 7);
        vc.speed = template.speed;
        entity.add(vc);

        TargetComponent target = Statics.ashley.createComponent(TargetComponent.class);
        entity.add(target);
        Weapon weapon;
        switch (template.weapon){
            case NEUTRAL:
                weapon = new Shooter();
                break;
            case LASER:
                weapon = new Laser();
                break;
            case FLAMETHROWER:
                weapon = new Flamethrower();
                break;
            case WATERTHROWER:
                weapon = new Watercannon();
                break;
            case SLIMEBALLS:
                weapon = new AcidShooter();
                break;
            case ENERGYGUN:
                weapon = new ParticleShooter();
                break;
            default:
                weapon = new Shooter();
                break;
        }
        weapon.setRange(template.range);
        Statics.attack.createAttack(entity, weapon);
        target.isShooting = false;

        SoundComponent sc = Statics.ashley.createComponent(SoundComponent.class);
        sc.shotSound = template.shotSound;
        sc.moveSound = template.movementSound;
        sc.deathSound = template.deathSound;
        sc.hitSound = template.hitSound;
        entity.add(sc);


        return  entity;
    }

}
