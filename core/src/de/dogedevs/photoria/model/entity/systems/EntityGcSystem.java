package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.PlayerComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.map.ChunkBuffer;

/**
 * Created by Furuha on 21.12.2015.
 */
public class EntityGcSystem extends EntitySystem {


    private final OrthographicCamera camera;
    private ImmutableArray<Entity> entities;
    private final ChunkBuffer chunkBuffer;

    public EntityGcSystem(OrthographicCamera camera, ChunkBuffer chunkBuffer) {
        this.chunkBuffer = chunkBuffer;
        this.camera = camera;
    }

    @Override
    public void addedToEngine (Engine engine) {

        entities = engine.getEntitiesFor(Family.all(PositionComponent.class).exclude(PlayerComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
//        MainGame.log("update: "+entities.size());
        PositionComponent position;

//        float minX = camera.position.x - camera.viewportWidth/2;
//        float minY = camera.position.y - camera.viewportHeight/2;
//        float maxX = minX + camera.viewportWidth;
//        float maxY = minY + camera.viewportHeight;

        for(Entity entity: entities){
            position = ComponentMappers.position.get(entity);
            if(chunkBuffer.getCellLazy((int)(position.x/32), (int)(position.y/32), 2) == null){
                getEngine().removeEntity(entity);
            }
//            if(!(position.x >= minX && position.x < maxX) && !(position.y >= minY && position.y < maxY)){
//                MainGame.log("removeEntity " + entity);
//                getEngine().removeEntity(entity);
//            }
        }
    }
}
