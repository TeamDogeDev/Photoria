package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.PlayerComponent;
import de.dogedevs.photoria.model.entity.components.VelocityComponent;

/**
 * Created by Furuha on 21.12.2015.
 */
public class PlayerControllSystem extends EntitySystem {


    private ImmutableArray<Entity> entities;

    public PlayerControllSystem() {
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PlayerComponent.class, VelocityComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
//        MainGame.log("update: "+entities.size());


        if(entities.size() == 0){
            return;
        }

        Entity e = entities.get(0);
        VelocityComponent velocity = ComponentMappers.velocity.get(e);

        velocity.speed = 0;

        if( Gdx.input.isKeyPressed(Input.Keys.A)){
//            camera.translate(-32,0);
            velocity.speed = 128;
            velocity.direction = VelocityComponent.WEST;
            return;
        }
        if( Gdx.input.isKeyPressed(Input.Keys.D)) {
//            camera.translate(32,0);
            velocity.speed = 128;
            velocity.direction = VelocityComponent.EAST;
            return;
        }
        if( Gdx.input.isKeyPressed(Input.Keys.S)){
//            camera.translate(0,-32);
            velocity.speed = 128;
            velocity.direction = VelocityComponent.SOUTH;
            return;
        }
        if( Gdx.input.isKeyPressed(Input.Keys.W)){
//            camera.translate(0,32);
            velocity.speed = 128;
            velocity.direction = VelocityComponent.NORTH;
            return;
        }

//        if( Gdx.input.isKeyPressed(Input.Keys.A))
//            camera.translate(-40*camera.zoom,0);
//        if( Gdx.input.isKeyPressed(Input.Keys.D))
//            camera.translate(40*camera.zoom,0);
//        if( Gdx.input.isKeyPressed(Input.Keys.S))
//            camera.translate(0,-40*camera.zoom);
//        if (Gdx.input.isKeyPressed(Input.Keys.W))
//            camera.translate(0,40*camera.zoom);
//        deltaSum += deltaTime;
//        if(deltaSum > 3){
//            deltaSum = 0;
//            entityNumber = MathUtils.random(entities.size()-1);
//        }


    }
}
