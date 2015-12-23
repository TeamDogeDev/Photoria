package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.entity.components.VelocityComponent;
import de.dogedevs.photoria.rendering.CustomTiledMapRenderer;

/**
 * Created by Furuha on 21.12.2015.
 */
public class FixFloatSystem extends EntitySystem {


    public static int offsetX;
    public static int offsetY;

    private final OrthographicCamera camera;
    private final CustomTiledMapRenderer renderer;
    private ImmutableArray<Entity> entities;

    public FixFloatSystem(OrthographicCamera camera, CustomTiledMapRenderer renderer) {
        this.camera = camera;
        this.renderer = renderer;
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
//        MainGame.log("update: "+entities.size());
        PositionComponent position;

        if(camera.position.x >= 10000 || camera.position.y >= 10000){
            for (int i = 0; i < entities.size(); ++i) {
                Entity e = entities.get(i);
                position = ComponentMappers.position.get(e);
                if(camera.position.x > 10000 ){
                    position.x -= 5000;
                }
                if(camera.position.y > 10000 ){
                    position.y -= 5000;
                }
            }
            if(camera.position.x >= 10000 ){
                renderer.offsetX -= 5000;
                camera.position.x -= 5000;
                offsetX -= 5000;
            }
            if(camera.position.y >= 10000 ){
                renderer.offsetY -= 5000;
                camera.position.y -= 5000;
                offsetY -= 5000;
            }
        }

        if(camera.position.x <= 0 || camera.position.y <= 0){
            for (int i = 0; i < entities.size(); ++i) {
                Entity e = entities.get(i);
                position = ComponentMappers.position.get(e);
                if(camera.position.x > 10000 ){
                    position.x += 5000;
                }
                if(camera.position.y > 10000 ){
                    position.y += 5000;
                }
            }
            if(camera.position.x <= 0 ){
                renderer.offsetX += 5000;
                camera.position.x += 5000;
                offsetX += 5000;
            }
            if(camera.position.y <= 0 ){
                renderer.offsetY += 5000;
                camera.position.y += 5000;
                offsetY += 5000;
            }
        }


    }
}
