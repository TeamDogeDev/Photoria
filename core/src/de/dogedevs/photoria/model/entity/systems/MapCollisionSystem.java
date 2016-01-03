package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.*;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.MapCollisionComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.map.ChunkBuffer;
import de.dogedevs.photoria.model.map.ChunkCell;

/**
 * Created by Furuha on 21.12.2015.
 */
public class MapCollisionSystem extends EntitySystem implements EntityListener {


    private final ChunkBuffer buffer;

    public MapCollisionSystem(ChunkBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void addedToEngine (Engine engine) {
        engine.addEntityListener(Family.all(PositionComponent.class, MapCollisionComponent.class).get(), this);
    }

    @Override
    public void removedFromEngine (Engine engine) {
        engine.removeEntityListener(this);
    }

    @Override
    public void entityAdded (Entity entity) {
        PositionComponent pc = ComponentMappers.position.get(entity);
        MapCollisionComponent mc = ComponentMappers.mapCollision.get(entity);
        ChunkCell cell = buffer.getCellLazy((int)(pc.x/32), (int)(pc.y/32), ChunkBuffer.COLLISION);
        cell.value = mc.value;
    }

    @Override
    public void entityRemoved (Entity entity) {
    }

    @Override
    public void update (float deltaTime) {

    }
}
