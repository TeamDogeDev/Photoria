package de.dogedevs.photoria.model.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import de.dogedevs.photoria.model.entity.ai.SlimeAi;
import de.dogedevs.photoria.model.entity.components.*;
import de.dogedevs.photoria.model.map.ChunkBuffer;
import de.dogedevs.photoria.model.map.ChunkCell;
import de.dogedevs.photoria.rendering.tiles.Tile;
import de.dogedevs.photoria.rendering.tiles.TileCollisionMapper;
import de.dogedevs.photoria.screens.GameScreen;
import de.dogedevs.photoria.utils.assets.AnimationLoader;
import de.dogedevs.photoria.utils.assets.Textures;

import java.util.ArrayList;
import java.util.Collection;

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
        ChunkCell cell = buffer.getCellLazy((int)x/32, (int)y/32, ChunkBuffer.BIOME);
        if(cell == null){
            return;
        }
        if(cell.value == ChunkBuffer.FOREST_BIOM){
            createSlime(x,y);
        } else if(cell.value == ChunkBuffer.GRASS_DESERT_BIOM){
            createEyeball(x,y);
        } else if(cell.value == TileCollisionMapper.LAVA){

        } else if(cell.value == TileCollisionMapper.WATER){

        }
    }

    private void createRandomDecoEntity(float x, float y, ChunkBuffer buffer){
        ChunkCell cell = buffer.getCellLazy((int)x/32, (int)y/32, ChunkBuffer.COLLISION);
        if(cell == null){
            return;
        }
        if(cell.value == TileCollisionMapper.LAVA_STONE){
            createLavaDeco(x, y);
        } else if(cell.value == TileCollisionMapper.GROUND){

        } else if(cell.value == TileCollisionMapper.LAVA){

        } else if(cell.value == TileCollisionMapper.WATER){

        }
    }

    private void createLavaDeco(float x, float y) {
        Entity entity = ashley.createEntity();
        PositionComponent pc = ashley.createComponent(PositionComponent.class);
        pc.x = x;
        pc.y = y;
        entity.add(pc);
        SpriteComponent sc = ashley.createComponent(SpriteComponent.class);
        sc.region = Tile.LAVA_DECO_1.getTextureRegion();
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
        AiComponent aiComponent = ashley.createComponent(AiComponent.class);
        aiComponent.ai = new SlimeAi();
        entity.add(aiComponent);
        VelocityComponent vc = ashley.createComponent(VelocityComponent.class);
        vc.direction = MathUtils.random(0, 7);
        vc.speed = 20;
        entity.add(vc);
        ashley.addEntity(entity);
    }

    private void createSlime(float x, float y){
        Animation[] animations = AnimationLoader.getMovementAnimations(Textures.SLIME, true, 4, 3);
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
        AiComponent aiComponent = ashley.createComponent(AiComponent.class);
        aiComponent.ai = new SlimeAi();
        entity.add(aiComponent);
        InventoryComponent ic = ashley.createComponent(InventoryComponent.class);
        ic.items.addAll(createMobItems(0.5f, 1));
        entity.add(ic);
        VelocityComponent vc = ashley.createComponent(VelocityComponent.class);
        vc.direction = MathUtils.random(0, 7);
        vc.speed = 20;
        entity.add(vc);
        ashley.addEntity(entity);
    }

    private Collection<? extends Entity> createMobItems(float chance, int type) {
        ArrayList<Entity> result = new ArrayList<>();
        if(MathUtils.randomBoolean(chance)){
            Animation[] animations = AnimationLoader.getMovementAnimations(Textures.EYE, true, 4, 3);
            Animation walkAnimationU = animations[0];
            Animation walkAnimationD = animations[1];
            Animation walkAnimationL = animations[2];
            Animation walkAnimationR = animations[3];

            Entity entity = ashley.createEntity();
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
            ItemComponent ic = ashley.createComponent(ItemComponent.class);
            entity.add(ic);
            ashley.addEntity(entity);
            result.add(entity);
        }
        return result;
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
