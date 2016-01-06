package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.content.AttackManager;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.EnergyComponent;
import de.dogedevs.photoria.model.entity.components.PlayerComponent;
import de.dogedevs.photoria.model.entity.components.VelocityComponent;
import de.dogedevs.photoria.rendering.overlay.GameOverlay;

import java.util.UUID;

/**
 * Created by Furuha on 21.12.2015.
 */
public class PlayerControllSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private final Sound hit = Gdx.audio.newSound(Gdx.files.internal("audio/hit.wav"));
    public static final int SPEED = 128*2;

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

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            GameOverlay.addTextbox(UUID.randomUUID().toString() + " Yay22");
        }

        if (entities.size() == 0) {
            return;
        }
        final Entity e = entities.get(0);

        EnergyComponent energyComponent = ComponentMappers.energy.get(e);
        energyComponent.energy += 2*deltaTime;
        energyComponent.energy = MathUtils.clamp(energyComponent.energy, 0f, energyComponent.maxEnergy);


        VelocityComponent velocity = ComponentMappers.velocity.get(e);
        velocity.speed = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){

            if(energyComponent.energy >= 1) {
                energyComponent.energy--;
                energyComponent.energy = MathUtils.clamp(energyComponent.energy, 0f, energyComponent.maxEnergy);
                AttackManager am = new AttackManager();
                int direction = 0;
                if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    direction = VelocityComponent.NORTH_WEST;
                } else if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    direction = VelocityComponent.NORTH_EAST;
                } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    direction = VelocityComponent.SOUTH_EAST;
                } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    direction = VelocityComponent.SOUTH_WEST;
                } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    direction = VelocityComponent.NORTH;
                } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    direction = VelocityComponent.WEST;
                } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    direction = VelocityComponent.SOUTH;
                } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    direction = VelocityComponent.EAST;
                }
                am.shootNormal(e, direction, null);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
//            camera.translate(0,32);
            velocity.speed = SPEED;
            velocity.direction = VelocityComponent.NORTH_WEST;
            return;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
//            camera.translate(0,32);
            velocity.speed = SPEED;
            velocity.direction = VelocityComponent.NORTH_EAST;
            return;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
//            camera.translate(0,32);
            velocity.speed = SPEED;
            velocity.direction = VelocityComponent.SOUTH_EAST;
            return;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.S)) {
//            camera.translate(0,32);
            velocity.speed = SPEED;
            velocity.direction = VelocityComponent.SOUTH_WEST;
            return;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//            camera.translate(0,32);
            velocity.speed = SPEED;
            velocity.direction = VelocityComponent.NORTH;
            return;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//            camera.translate(0,32);
            velocity.speed = SPEED;
            velocity.direction = VelocityComponent.WEST;
            return;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            camera.translate(0,32);
            velocity.speed = SPEED;
            velocity.direction = VelocityComponent.SOUTH;
            return;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//            camera.translate(0,32);
            velocity.speed = SPEED;
            velocity.direction = VelocityComponent.EAST;
            return;
        }


//
//        if( Gdx.input.isKeyPressed(Input.Keys.A)){
////            camera.translate(-32,0);
//            velocity.speed = SPEED;
//            velocity.direction = VelocityComponent.WEST;
//            return;
//        }
//        if( Gdx.input.isKeyPressed(Input.Keys.D)) {
////            camera.translate(32,0);
//            velocity.speed = SPEED;
//            velocity.direction = VelocityComponent.EAST;
//            return;
//        }
//        if( Gdx.input.isKeyPressed(Input.Keys.S)){
////            camera.translate(0,-32);
//            velocity.speed = SPEED;
//            velocity.direction = VelocityComponent.SOUTH;
//            return;
//        }

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
