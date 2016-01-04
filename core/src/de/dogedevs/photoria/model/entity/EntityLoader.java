package de.dogedevs.photoria.model.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import de.dogedevs.photoria.model.entity.ai.DefaultMovingAi;
import de.dogedevs.photoria.model.entity.ai.SlimeAi;
import de.dogedevs.photoria.model.entity.components.*;
import de.dogedevs.photoria.model.map.Chunk;
import de.dogedevs.photoria.model.map.ChunkBuffer;
import de.dogedevs.photoria.model.map.ChunkCell;
import de.dogedevs.photoria.rendering.sprites.AnimationLoader;
import de.dogedevs.photoria.rendering.tiles.Tile;
import de.dogedevs.photoria.rendering.tiles.TileCollisionMapper;
import de.dogedevs.photoria.rendering.tiles.TileMapper;
import de.dogedevs.photoria.screens.GameScreen;

/**
 * Created by Furuha on 02.01.2016.
 */
public class EntityLoader {

    public void createChunkEntities(int chunkX, int chunkY, long seed, ChunkBuffer buffer){
        int numEntities = 50;
        PooledEngine ashley = GameScreen.getAshley();

        Animation[] animations = AnimationLoader.getMovementAnimations("eyeball.png", true, 4, 3);
        Animation walkAnimationU = animations[0];
        Animation walkAnimationD = animations[1];
        Animation walkAnimationL = animations[2];
        Animation walkAnimationR = animations[3];

        for (int i = 0; i < numEntities/2; i++) {
            Entity eyeball = ashley.createEntity();
            PositionComponent pc = ashley.createComponent(PositionComponent.class);
            pc.x = MathUtils.random(chunkX * 64 * 32, chunkX * 64 * 32 + 2048);
            pc.y = MathUtils.random(chunkY * 64 * 32, chunkY * 64 * 32 + 2048);
            ChunkCell cell;
            if((cell = buffer.getCellLazy((int)pc.x/32, (int)pc.y/32, ChunkBuffer.DECO1)) == null || cell.value > 0){
                continue;
            }
            eyeball.add(pc);
            AnimationComponent ac = ashley.createComponent(AnimationComponent.class);
            ac.idleAnimation = walkAnimationD;
            ac.leftAnimation = walkAnimationL;
            ac.rightAnimation = walkAnimationR;
            ac.upAnimation = walkAnimationU;
            ac.downAnimation = walkAnimationD;
            eyeball.add(ac);
            CollisionComponent cc = ashley.createComponent(CollisionComponent.class);
            cc.groundCollision = TileCollisionMapper.normalBorderCollision;
            eyeball.add(cc);
            ElementsComponent ec = ashley.createComponent(ElementsComponent.class);
            ec.blue = MathUtils.random(-2f, 2f);
            ec.red = MathUtils.random(-2f, 2f);
            ec.green = MathUtils.random(-2f, 2f);
            ec.purple = MathUtils.random(-2f, 2f);
            ec.yellow = MathUtils.random(-2f, 2f);
            eyeball.add(ec);
            AiComponent aiComponent = ashley.createComponent(AiComponent.class);
            aiComponent.ai = new DefaultMovingAi();
            eyeball.add(aiComponent);
            VelocityComponent vc = ashley.createComponent(VelocityComponent.class);
            vc.direction = MathUtils.random(0, 7);
            vc.speed = 20;
            eyeball.add(vc);
            ashley.addEntity(eyeball);
        }

        animations = AnimationLoader.getMovementAnimations("slime.png", true, 4, 3);
        walkAnimationU = animations[0];
        walkAnimationD = animations[1];
        walkAnimationL = animations[2];
        walkAnimationR = animations[3];

        for (int i = 0; i < numEntities/2; i++) {
            Entity slime = ashley.createEntity();
            PositionComponent pc = ashley.createComponent(PositionComponent.class);
            pc.x = MathUtils.random(chunkX * 64 * 32, chunkX * 64 * 32 + 2048);
            pc.y = MathUtils.random(chunkY * 64 * 32, chunkY * 64 * 32 + 2048);
            ChunkCell cell;
            if((cell = buffer.getCellLazy((int)pc.x/32, (int)pc.y/32, ChunkBuffer.DECO1)) == null || cell.value > 0){
                continue;
            }
            slime.add(pc);
            AnimationComponent ac = ashley.createComponent(AnimationComponent.class);
            ac.idleAnimation = walkAnimationD;
            ac.leftAnimation = walkAnimationL;
            ac.rightAnimation = walkAnimationR;
            ac.upAnimation = walkAnimationU;
            ac.downAnimation = walkAnimationD;
            slime.add(ac);
            CollisionComponent cc = ashley.createComponent(CollisionComponent.class);
            cc.groundCollision = TileCollisionMapper.normalBorderCollision;
            slime.add(cc);
            ElementsComponent ec = ashley.createComponent(ElementsComponent.class);
            ec.blue = MathUtils.random(-2f, 2f);
            ec.red = MathUtils.random(-2f, 2f);
            ec.green = MathUtils.random(-2f, 2f);
            ec.purple = MathUtils.random(-2f, 2f);
            ec.yellow = MathUtils.random(-2f, 2f);
            slime.add(ec);
            AiComponent aiComponent = ashley.createComponent(AiComponent.class);
            aiComponent.ai = new SlimeAi();
            slime.add(aiComponent);
            VelocityComponent vc = ashley.createComponent(VelocityComponent.class);
            vc.direction = MathUtils.random(0, 7);
            vc.speed = 20;
            slime.add(vc);
            ashley.addEntity(slime);
        }

        RandomXS128 rnd = new RandomXS128(seed+chunkX+chunkY);
        for (int i = 0; i < 50; i++) {
            Entity worm = ashley.createEntity();
            PositionComponent pc = ashley.createComponent(PositionComponent.class);
            pc.x = ((chunkX * 64 * 32) + (int)(rnd.nextFloat()*64)*32);
            pc.y = ((chunkY * 64 * 32) + (int)(rnd.nextFloat()*64)*32);
            ChunkCell cell;
            if((cell = buffer.getCellLazy((int)pc.x/32, (int)pc.y/32, ChunkBuffer.GROUND)) == null || cell.value != TileMapper.LAVA_STONE){
                continue;
            }
            worm.add(pc);
            SpriteComponent sc = ashley.createComponent(SpriteComponent.class);
            sc.region = Tile.LAVA_DECO_1.getTextureRegion();
            worm.add(sc);
            MapCollisionComponent mc = ashley.createComponent(MapCollisionComponent.class);
            mc.value = TileCollisionMapper.ENTITY;
            worm.add(mc);
            ashley.addEntity(worm);
        }
    }

    public void onChunkPurge(Chunk chunk) {
        PooledEngine ashley = GameScreen.getAshley();
        ImmutableArray<Entity> entities = ashley.getEntitiesFor(Family.all(PositionComponent.class).get());
        int minX = chunk.x * 64 * 32;
        int minY = chunk.y * 64 * 32;
        int maxX = minX + 2048;
        int maxY = minY + 2048;
        for(Entity entity: entities){
            PositionComponent position = ComponentMappers.position.get(entity);
            if((position.x >= minX && position.x < maxX) && (position.y >= minY && position.y < maxY)){
//                MainGame.log("removeEntity " + entity);
                ashley.removeEntity(entity);
            }
        }
    }
}
