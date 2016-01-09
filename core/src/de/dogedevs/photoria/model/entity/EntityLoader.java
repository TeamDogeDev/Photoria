package de.dogedevs.photoria.model.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import de.dogedevs.photoria.content.MobStats;
import de.dogedevs.photoria.content.items.ItemManager;
import de.dogedevs.photoria.model.entity.components.*;
import de.dogedevs.photoria.model.entity.components.rendering.AnimationComponent;
import de.dogedevs.photoria.model.entity.components.rendering.SpriteComponent;
import de.dogedevs.photoria.model.entity.components.stats.ElementsComponent;
import de.dogedevs.photoria.model.entity.components.stats.HealthComponent;
import de.dogedevs.photoria.model.map.ChunkBuffer;
import de.dogedevs.photoria.model.map.ChunkCell;
import de.dogedevs.photoria.rendering.tiles.Tile;
import de.dogedevs.photoria.rendering.tiles.TileCollisionMapper;
import de.dogedevs.photoria.rendering.tiles.TileMapper;
import de.dogedevs.photoria.screens.GameScreen;
import de.dogedevs.photoria.utils.assets.AnimationLoader;
import de.dogedevs.photoria.utils.assets.enums.Textures;

/**
 * Created by Furuha on 02.01.2016.
 */
public class EntityLoader {

    PooledEngine ashley = GameScreen.getAshley();

    public void createChunkEntities(int chunkX, int chunkY, long seed, ChunkBuffer buffer){
//        long start = System.currentTimeMillis();
        int numEntities = 100;
        ashley = GameScreen.getAshley();

        for (int i = 0; i < numEntities; i++) {
            createRandomEntity(MathUtils.random(chunkX * 64 * 32, chunkX * 64 * 32 + 2048), MathUtils.random(chunkY * 64 * 32, chunkY * 64 * 32 + 2048), buffer);
        }

        RandomXS128 rnd = new RandomXS128(seed+chunkX+chunkY);
        for (int i = 0; i < 50; i++) {
            float x = ((chunkX * 64 * 32) + (int)(rnd.nextFloat()*64)*32);
            float y = ((chunkY * 64 * 32) + (int)(rnd.nextFloat()*64)*32);
            createRandomDecoEntity(x, y, buffer);
        }
//        MainGame.log("ec time: "+(System.currentTimeMillis()-start));
    }

    private void createRandomEntity(float x, float y, ChunkBuffer buffer){
        ChunkCell biomCell = buffer.getCellLazy((int)x/32, (int)y/32, ChunkBuffer.BIOME);
        ChunkCell liquidCell = buffer.getCellLazy((int)x/32, (int)y/32, ChunkBuffer.FLUID);
        if(biomCell == null){
            return;
        }
        if(biomCell.value == ChunkBuffer.BLUE_BIOM){
            if(liquidCell.value == TileMapper.WATER
            || liquidCell.value == TileMapper.WATER2
            || liquidCell.value == TileMapper.WATER3
            || liquidCell.value == TileMapper.WATER4) {
                createSlime(Textures.SLIME_BLUE, x, y);
            }
        } else if(biomCell.value == ChunkBuffer.GREEN_BIOM){
            createSlime(Textures.SLIME_GREEN, x,y);
        } else if(biomCell.value == ChunkBuffer.PURPLE_BIOM){
            createSlime(Textures.SLIME_PURPLE, x,y);
        } else if(biomCell.value == ChunkBuffer.RED_BIOM){
            if(liquidCell.value == TileMapper.LAVA
            || liquidCell.value == TileMapper.LAVA2
            || liquidCell.value == TileMapper.LAVA3
            || liquidCell.value == TileMapper.LAVA4) {
                createSlime(Textures.SLIME_RED, x, y);
            }
        } else if(biomCell.value == ChunkBuffer.YELLOW_BIOM){
            createEyeball(x,y);
        }
//        if(biomCell.value == ChunkBuffer.GREEN_BIOM){
//            createSlime(x,y);
//        } else if(biomCell.value == ChunkBuffer.RED_BIOM){
//            createEyeball(x,y);
//        } else if(biomCell.value == TileCollisionMapper.HIGH_GROUND_FLUID){
//
//        } else if(biomCell.value == TileCollisionMapper.FLUID){
//
//        }
    }

    private void createRandomDecoEntity(float x, float y, ChunkBuffer buffer){
        ChunkCell cell = buffer.getCellLazy((int)x/32, (int)y/32, ChunkBuffer.COLLISION);
        if(cell == null){
            return;
        }
        if(cell.value == TileCollisionMapper.HIGH_GROUND){
            createLavaDeco(x, y, buffer);
        } else if(cell.value == TileCollisionMapper.GROUND){

        } else if(cell.value == TileCollisionMapper.HIGH_GROUND_FLUID){

        } else if(cell.value == TileCollisionMapper.FLUID){

        }
    }

    private void createLavaDeco(float x, float y, ChunkBuffer buffer) {
        Entity entity = ashley.createEntity();
        PositionComponent pc = ashley.createComponent(PositionComponent.class);
        pc.x = x;
        pc.y = y;
        entity.add(pc);
        SpriteComponent sc = ashley.createComponent(SpriteComponent.class);
//        sc.region = Tile.LAVA_DECO_1.getTextureRegion();
        sc.region = Tile.getTileForBiome(TileMapper.LAVA_DECO_1, buffer.getCellLazy((int)x/32, (int)y/32, ChunkBuffer.BIOME).value).getTextureRegion();
        entity.add(sc);
        MapCollisionComponent mc = ashley.createComponent(MapCollisionComponent.class);
        mc.value = TileCollisionMapper.ENTITY;
        entity.add(mc);
        ashley.addEntity(entity);
    }

    private void createEyeball(float x, float y){
        Animation[] animations = AnimationLoader.getMovementAnimations(Textures.EYE, true, 4, 3);
        Animation walkAnimationU = animations[0];
        Animation walkAnimationD = animations[1];
        Animation walkAnimationL = animations[2];
        Animation walkAnimationR = animations[3];

        Entity entity = ashley.createEntity();
        PositionComponent pc = ashley.createComponent(PositionComponent.class);
        pc.x = x;
        pc.y = y;
        entity.add(pc);
        AnimationComponent ac = ashley.createComponent(AnimationComponent.class);
        ac.idleAnimation = walkAnimationD;
        ac.leftAnimation = walkAnimationL;
        ac.rightAnimation = walkAnimationR;
        ac.upAnimation = walkAnimationU;
        ac.downAnimation = walkAnimationD;
        entity.add(ac);
        CollisionComponent cc = ashley.createComponent(CollisionComponent.class);
        cc.groundCollision = TileCollisionMapper.normalBorderCollision;
        entity.add(cc);
        ElementsComponent ec = ashley.createComponent(ElementsComponent.class);
        ec.blue = MathUtils.random(-2f, 2f);
        ec.red = MathUtils.random(-2f, 2f);
        ec.green = MathUtils.random(-2f, 2f);
        ec.purple = MathUtils.random(-2f, 2f);
        ec.yellow = MathUtils.random(-2f, 2f);
        entity.add(ec);
        HealthComponent hc = ashley.createComponent(HealthComponent.class);
        hc.maxHealth = MobStats.EYE_HEALTH;
        hc.health = MobStats.EYE_HEALTH;
        entity.add(hc);
        AiComponent aiComponent = ashley.createComponent(AiComponent.class);
        aiComponent.ai = MobStats.EYE_AI;
        entity.add(aiComponent);
        VelocityComponent vc = ashley.createComponent(VelocityComponent.class);
        vc.direction = MathUtils.random(0, 7);
        vc.speed = 20;
        entity.add(vc);
        ashley.addEntity(entity);
    }
//    private void createMob(MobTemplate template, float x, float y)
    private void createSlime(Textures texture, float x, float y){
        Animation[] animations = AnimationLoader.getMovementAnimations(texture, true, 4, 3);
        Animation walkAnimationU = animations[0];
        Animation walkAnimationD = animations[1];
        Animation walkAnimationL = animations[2];
        Animation walkAnimationR = animations[3];

        Entity entity = ashley.createEntity();
        PositionComponent pc = ashley.createComponent(PositionComponent.class);
        pc.x = x;
        pc.y = y;
        entity.add(pc);
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
                        hc.health -= 5;
                        hc.health = MathUtils.clamp(hc.health, 0, hc.maxHealth);
                        hc.immuneTime = hc.maxImmuneTime;
                    }
                }
                return false;
            }
        };
        entity.add(cc);
        ElementsComponent ec = ashley.createComponent(ElementsComponent.class);
        ec.blue = MathUtils.random(-2f, 2f);
        ec.red = MathUtils.random(-2f, 2f);
        ec.green = MathUtils.random(-2f, 2f);
        ec.purple = MathUtils.random(-2f, 2f);
        ec.yellow = MathUtils.random(-2f, 2f);
        entity.add(ec);
        AiComponent aiComponent = ashley.createComponent(AiComponent.class);
        aiComponent.ai = MobStats.SLIME_AI;
        entity.add(aiComponent);
        InventoryComponent ic = ashley.createComponent(InventoryComponent.class);
        ItemManager im = new ItemManager();
        entity.add(ic);
        im.populateInventory(entity, 1);
        HealthComponent hc = ashley.createComponent(HealthComponent.class);
        hc.maxHealth = MobStats.SLIME_HEALTH;
        hc.health = MobStats.SLIME_HEALTH;
        entity.add(hc);
        VelocityComponent vc = ashley.createComponent(VelocityComponent.class);
        vc.direction = MathUtils.random(0, 7);
        vc.speed = 20;
        entity.add(vc);
        ashley.addEntity(entity);
    }



//    public void onChunkPurge(Chunk chunk) {
//        PooledEngine ashley = GameScreen.getAshley();
//        ImmutableArray<Entity> entities = ashley.getEntitiesFor(Family.all(PositionComponent.class).get());
//        int minX = chunk.x * 64 * 32;
//        int minY = chunk.y * 64 * 32;
//        int maxX = minX + 2048;
//        int maxY = minY + 2048;
//        for(Entity entity: entities){
//            PositionComponent position = ComponentMappers.position.get(entity);
//            if((position.x >= minX && position.x < maxX) && (position.y >= minY && position.y < maxY)){
////                MainGame.log("removeEntity " + entity);
//                ashley.removeEntity(entity);
//            }
//        }
//    }
}
