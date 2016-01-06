package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.*;
import de.dogedevs.photoria.model.map.ChunkBuffer;
import de.dogedevs.photoria.model.map.ChunkCell;

import java.util.*;

/**
 * Created by Furuha on 21.12.2015.
 */
public class MovingEntitySystem extends EntitySystem implements EntityListener {


    private final ChunkBuffer buffer;
    private ImmutableArray<Entity> entities;
    private List<Entity> sortedEntities = new ArrayList<>();
    private YComparator comparator = new YComparator();
    public static final float DIAGONAL_CORRECTION = 0.70710678118f;
    private HashSet<String> collisioned = new HashSet<>();

    public MovingEntitySystem(ChunkBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void entityAdded (Entity entity) {
        sortedEntities.add(entity);
    }

    @Override
    public void entityRemoved (Entity entity) {
        sortedEntities.remove(entity);
    }

    private class YComparator implements Comparator<Entity> {
        @Override
        public int compare(Entity e1, Entity e2) {
            return (int)Math.signum(ComponentMappers.position.get(e2).y - ComponentMappers.position.get(e1).y);
        }
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class).get());
        engine.addEntityListener(Family.all(PositionComponent.class).get(), this);
        for(Entity e: entities){
            sortedEntities.add(e);
        }
        Collections.sort(sortedEntities, comparator);
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

//    int checks;

    @Override
    public void update (float deltaTime) {
//        MainGame.log("update: "+entities.size());
        PositionComponent position;
        VelocityComponent velocity;
        CollisionComponent collision;

        float oldX;
        float oldY;
//        checks = 0;
        Collections.sort(sortedEntities, comparator);

        for (int i = 0; i < sortedEntities.size(); ++i) {
            Entity e = sortedEntities.get(i);
            if(ComponentMappers.velocity.get(e) == null){
                continue;
            }

            position = ComponentMappers.position.get(e);
            velocity = ComponentMappers.velocity.get(e);
            collision = ComponentMappers.collision.get(e);

            oldY = position.y;
            oldX = position.x;

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
                case VelocityComponent.NORTH_EAST:
                    position.y += velocity.speed * deltaTime * DIAGONAL_CORRECTION;
                    position.x += velocity.speed * deltaTime * DIAGONAL_CORRECTION;
                    break;
                case VelocityComponent.NORTH_WEST:
                    position.y += velocity.speed * deltaTime * DIAGONAL_CORRECTION;
                    position.x -= velocity.speed * deltaTime * DIAGONAL_CORRECTION;
                    break;
                case VelocityComponent.SOUTH_EAST:
                    position.y -= velocity.speed * deltaTime * DIAGONAL_CORRECTION;
                    position.x += velocity.speed * deltaTime * DIAGONAL_CORRECTION;
                    break;
                case VelocityComponent.SOUTH_WEST:
                    position.y -= velocity.speed * deltaTime * DIAGONAL_CORRECTION;
                    position.x -= velocity.speed * deltaTime * DIAGONAL_CORRECTION;
                    break;
            }
            Entity other = null;
            if(collision != null && (checkMapCollision(position.x, position.y, collision.groundCollision) || (other = checkEntityCollision(position.x, position.y, collision.size, i)) != null)){
                if(other != null){
                    CollisionComponent collisionOther = ComponentMappers.collision.get(other);
                    if(!collision.ghost && !collisionOther.ghost){
                        position.y = oldY;
                        position.x = oldX;
                        velocity.blockedDelta += deltaTime;
                    }
                    if(!collisioned.contains(other.toString()+""+e.toString())){
                        if(collision.collisionListener != null){
                            collision.collisionListener.onCollision(other, e);
                        }
                        if(collisionOther != null && collisionOther.collisionListener != null){
                            collisionOther.collisionListener.onCollision(e, other);
                        }
                        collisioned.add(e.toString()+""+other.toString());
                    }
                } else if(!collision.ghost){
                    position.y = oldY;
                    position.x = oldX;
                    velocity.blockedDelta += deltaTime;
                }
            }

        }
        collisioned.clear();
//        MainGame.log("Checks: "+checks);
//        checks = 0;
    }

    private boolean checkMapCollision(float x, float y, int[] groundCollision) {
        ChunkCell cell = buffer.getCellLazy((int)(x/32), (int)(y/32), ChunkBuffer.COLLISION);
        if(cell == null){
            return true;
        }
        if(groundCollision == null){
            return false;
        }
        return (Arrays.binarySearch(groundCollision, cell.value) >= 0);
    }


    private Entity checkEntityCollision(float x, float y, float ownSize, int startIndex) {
        PositionComponent position;
        CollisionComponent collision;
        for(int i = startIndex; i < sortedEntities.size(); i++){
            if(i == startIndex){
                continue;
            }
            Entity other = sortedEntities.get(i);
            position = ComponentMappers.position.get(other);
            if(position == null){
                continue;
            }
            if((y - position.y) >= 32){
                break;
            }
            collision = ComponentMappers.collision.get(other);
            if(collision == null){
                continue;
            }
            if(rectCollides(x, y, position.x, position.y, collision.size/2+ownSize/2)){
                if(collision.ghost){
                    Entity self = sortedEntities.get(startIndex);
                    CollisionComponent selfCollision = ComponentMappers.collision.get(self);
                    if(!collisioned.contains(other.toString()+""+self.toString())){
                        if(selfCollision.collisionListener != null){
                            selfCollision.collisionListener.onCollision(other, self);
                        }
                        if(collision.collisionListener != null){
                            collision.collisionListener.onCollision(self, other);
                        }
                        collisioned.add(self.toString()+""+other.toString());
                    }
                    continue;
                } else {
                    return sortedEntities.get(i);
                }
            }
        }

        for(int i = startIndex; i >= 0; i--){
            if(i == startIndex){
                continue;
            }
            Entity other = sortedEntities.get(i);
            position = ComponentMappers.position.get(sortedEntities.get(i));
            if(position == null){
                continue;
            }
            if((position.y - y) >= 32){
                break;
            }
            collision = ComponentMappers.collision.get(sortedEntities.get(i));
            if(collision == null){
                continue;
            }
            if(rectCollides(x, y, position.x, position.y, collision.size/2+ownSize/2)){
                if(collision.ghost){
                    Entity self = sortedEntities.get(startIndex);
                    CollisionComponent selfCollision = ComponentMappers.collision.get(self);
                    if(!collisioned.contains(other.toString()+""+self.toString())){
                        if(selfCollision.collisionListener != null){
                            selfCollision.collisionListener.onCollision(other, self);
                        }
                        if(collision.collisionListener != null){
                            collision.collisionListener.onCollision(self, other);
                        }
                        collisioned.add(self.toString()+""+other.toString());
                    }
                    continue;
                } else {
                    return sortedEntities.get(i);
                }
            }
        }
        return null;
    }


    private boolean collides(float x1, float y1, float x2, float y2){
//        checks++;
        float xDif = Math.abs(x2-x1);
        float yDif = Math.abs(y2-y1);
        if((xDif+yDif) < (32)){
            return true;
        }
        return false;
    }

    private boolean rectCollides(float x1, float y1, float x2, float y2, float size){
//        checks++;
        if((x1-size) < x2 && x2 < (x1+size) && Math.abs(y2-y1) < size){
            return true;
        }
        if((y1-size) < y2 && y2 < (y1+size) && Math.abs(x2-x1) < size){
            return true;
        }
        return false;
    }
}
