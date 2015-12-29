package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.entity.components.VelocityComponent;
import de.dogedevs.photoria.model.map.ChunkBuffer;
import de.dogedevs.photoria.model.map.ChunkCell;

/**
 * Created by Furuha on 21.12.2015.
 */
public class MovingEntitySystem extends EntitySystem {


    private final ChunkBuffer buffer;
    private ImmutableArray<Entity> entities;

    public MovingEntitySystem(ChunkBuffer buffer) {
     this.buffer = buffer;
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
//        MainGame.log("update: "+entities.size());
        PositionComponent position;
        VelocityComponent velocity;

        float oldX;
        float oldY;

        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);

            position = ComponentMappers.position.get(e);
            velocity = ComponentMappers.velocity.get(e);

            oldY = position.y;
            oldX = position.x;

            if(MathUtils.randomBoolean(0.001f)){
                velocity.direction = MathUtils.random(0, 3);
            }
            switch (velocity.direction){
                case VelocityComponent.SOUTH:
                    position.y -= velocity.speed * deltaTime;
                    break;
                case VelocityComponent.NORTH:
                    position.y += velocity.speed * deltaTime;
                    break;
                case VelocityComponent.WEST:
                    position.x -= velocity.speed * deltaTime;
                    break;
                case VelocityComponent.EAST:
                    position.x += velocity.speed * deltaTime;
                    break;
            }

            if(checkCollision(position.x, position.y)){
                position.y = oldY;
                position.x = oldX;
            }

        }
    }

    private boolean checkCollision(float x, float y) {
        ChunkCell cell = buffer.getCell((int)(x/32), (int)(y/32), 2);
        return (cell.collides);
    }
}
