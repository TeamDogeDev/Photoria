package de.dogedevs.photoria.model.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.model.entity.ai.DefaultMovingAi;
import de.dogedevs.photoria.model.entity.ai.SlimeAi;
import de.dogedevs.photoria.model.entity.components.*;
import de.dogedevs.photoria.model.map.Chunk;
import de.dogedevs.photoria.model.map.ChunkBuffer;
import de.dogedevs.photoria.rendering.sprites.AnimationLoader;
import de.dogedevs.photoria.rendering.tiles.TileCollisionMapper;
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
            AiComponent aiComponent = ashley.createComponent(AiComponent.class);
            aiComponent.ai = new SlimeAi();
            slime.add(aiComponent);
            VelocityComponent vc = ashley.createComponent(VelocityComponent.class);
            vc.direction = MathUtils.random(0, 7);
            vc.speed = 20;
            slime.add(vc);
            ashley.addEntity(slime);
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
