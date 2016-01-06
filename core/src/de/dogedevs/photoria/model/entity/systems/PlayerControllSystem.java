package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.*;
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
            GameOverlay.addTextbox(UUID.randomUUID().toString() + " Yay22", 5);
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

                Entity shot = ((PooledEngine) getEngine()).createEntity();
                SpriteComponent sc = ((PooledEngine) getEngine()).createComponent(SpriteComponent.class);
                sc.region = new TextureRegion(new Texture("bullet.png"));
                PositionComponent pc = ((PooledEngine) getEngine()).createComponent(PositionComponent.class);
                CollisionComponent cc = ((PooledEngine) getEngine()).createComponent(CollisionComponent.class);
                cc.ghost = true;
                cc.projectile = true;
                cc.collisionListener = new CollisionComponent.CollisionListener() {

                    @Override
                    public boolean onCollision(Entity other, Entity self) {
                        ItemComponent itemC = ComponentMappers.item.get(other);
                        CollisionComponent cC = ComponentMappers.collision.get(other);
                        if (other == e || itemC != null && cC.ghost || cC.projectile) {
                            return false;
                        }
                        hit.play();

                        ElementsComponent ec = ComponentMappers.elements.get(other);
                        ElementsComponent playerEc = ComponentMappers.elements.get(e);
                        if(ec != null && playerEc != null){
                            playerEc.blue += ec.blue;
                            playerEc.yellow += ec.yellow;
                            playerEc.red += ec.red;
                            playerEc.purple += ec.purple;
                            playerEc.green += ec.green;

                            playerEc.blue = MathUtils.clamp(playerEc.blue, 0f, 20f);
                            playerEc.yellow = MathUtils.clamp(playerEc.yellow, 0f, 20f);
                            playerEc.red = MathUtils.clamp(playerEc.red, 0f, 20f);
                            playerEc.purple = MathUtils.clamp(playerEc.purple, 0f, 20f);
                            playerEc.green = MathUtils.clamp(playerEc.green, 0f, 20f);
                        }
                        InventoryComponent ic = ComponentMappers.inventory.get(other);
                        PositionComponent pc = ComponentMappers.position.get(other);
                        if(ic != null){
                            for(Entity item: ic.items){
                                PositionComponent newPc = ((PooledEngine) getEngine()).createComponent(PositionComponent.class);
                                newPc.x = pc.x;
                                newPc.y = pc.y;
                                item.add(newPc);
                                LifetimeComponent lc = ((PooledEngine) getEngine()).createComponent(LifetimeComponent.class);
                                lc.maxTime = 10;
                                item.add(lc);
                            }
                        }

                        getEngine().removeEntity(other);
                        getEngine().removeEntity(self);
                        return true;
                    }

                };
                PositionComponent position = ComponentMappers.position.get(e);
                pc.x = position.x;
                pc.y = position.y;
                pc.z = 26;
                VelocityComponent vc = ((PooledEngine) getEngine()).createComponent(VelocityComponent.class);
                vc.speed = 512;
                shot.add(pc);
                shot.add(sc);
                shot.add(vc);
                shot.add(cc);
                getEngine().addEntity(shot);
                if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    vc.direction = VelocityComponent.NORTH_WEST;
                } else if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    vc.direction = VelocityComponent.NORTH_EAST;
                } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    vc.direction = VelocityComponent.SOUTH_EAST;
                } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    vc.direction = VelocityComponent.SOUTH_WEST;
                } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    vc.direction = VelocityComponent.NORTH;
                } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    vc.direction = VelocityComponent.WEST;
                } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    vc.direction = VelocityComponent.SOUTH;
                } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    vc.direction = VelocityComponent.EAST;
                }
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
