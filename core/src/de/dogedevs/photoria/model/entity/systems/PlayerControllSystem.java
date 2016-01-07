package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.dogedevs.photoria.content.AttackManager;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.*;
import de.dogedevs.photoria.rendering.laser.Laser;
import de.dogedevs.photoria.rendering.overlay.GameOverlay;
import de.dogedevs.photoria.utils.assets.MusicManager;
import de.dogedevs.photoria.utils.assets.SoundManager;
import de.dogedevs.photoria.utils.assets.enums.Musics;
import de.dogedevs.photoria.utils.assets.enums.Sounds;

import java.util.UUID;

/**
 * Created by Furuha on 21.12.2015.
 */
public class PlayerControllSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    public static final int SPEED = 128*4;

    public PlayerControllSystem() {
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PlayerComponent.class, VelocityComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }


    boolean wasActive = false;

    @Override
    public void update (float deltaTime) {
//        MainGame.log("update: "+entities.size());


        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            GameOverlay.addTextbox(UUID.randomUUID().toString() + " [#f0f00f]Yay22[]", 3);
            GameOverlay.addTextbox(UUID.randomUUID().toString() + " [#f0f00f]Yay22[]");
            GameOverlay.addTextbox(UUID.randomUUID().toString() + " [#f0f00f]Yay22[]", 1);
            GameOverlay.addTextbox(UUID.randomUUID().toString() + " [#f0f00f]Yay22[]", 1);
            GameOverlay.addTextbox(UUID.randomUUID().toString() + " [#f0f00f]Yay22[]");
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_0)) {
            MusicManager.playMusic(Musics.TITLE, false);

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
            MusicManager.playMusic(Musics.AMBIENT, false);
        }

        if (entities.size() == 0) {
            return;
        }
        final Entity e = entities.get(0);

        EnergyComponent energyComponent = ComponentMappers.energy.get(e);
        PositionComponent positionComponent = ComponentMappers.position.get(e);
        energyComponent.energy += 2*deltaTime;
        energyComponent.energy = MathUtils.clamp(energyComponent.energy, 0f, energyComponent.maxEnergy);

        if(!Gdx.input.isTouched()){
            if(wasActive){
                wasActive = false;
                e.remove(AttackComponent.class);
            }
        } else {
            if(!wasActive){
                wasActive = true;
                AttackComponent ac = ComponentMappers.attack.get(e);
                if(ac == null){
                    ac = ((PooledEngine) getEngine()).createComponent(AttackComponent.class);
                    ac.laser = new Laser();
                    ac.laser.length = 400;
                    ac.listener = new CollisionComponent.CollisionListener() {
                        @Override
                        public boolean onCollision(Entity other, Entity self) {
                            ItemComponent itemC = ComponentMappers.item.get(other);
                            CollisionComponent cC = ComponentMappers.collision.get(other);
                            if (other == e || itemC != null && cC.ghost || cC.projectile) {
                                return false;
                            }
                            SoundManager.playSound(Sounds.MOB_HIT);

                            HealthComponent hc = ComponentMappers.health.get(other);
                            if(hc != null){
                                hc.health -= 10*Gdx.graphics.getDeltaTime();
                                hc.health = MathUtils.clamp(hc.health, 0, hc.maxHealth);
                                if(hc.health == 0){
                                    die(other, self);
                                }
                            }
                            return true;
                        }

                        private void die(Entity other, Entity self) {
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
                                    PositionComponent newPc = ((PooledEngine)getEngine()).createComponent(PositionComponent.class);
                                    newPc.x = pc.x;
                                    newPc.y = pc.y;
                                    item.add(newPc);
                                    LifetimeComponent lc = ((PooledEngine)getEngine()).createComponent(LifetimeComponent.class);
                                    lc.maxTime = 10;
                                    item.add(lc);
                                }
                            }
                        }
                    };

                    e.add(ac);
                }
            } else {
                Vector2 dir = new Vector2();
                dir.set(Gdx.input.getX() - Gdx.graphics.getWidth() / 2 + positionComponent.x, (Gdx.graphics.getHeight() - Gdx.input.getY()) - Gdx.graphics.getHeight() / 2 +positionComponent.y);
                AttackComponent ac = ComponentMappers.attack.get(e);
                ac.laser.setBegin(new Vector2(positionComponent.x, positionComponent.y));
                ac.laser.setAngle(dir);
            }
        }


        VelocityComponent velocity = ComponentMappers.velocity.get(e);
        velocity.speed = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){

            if(energyComponent.energy >= 1) {
                energyComponent.energy--;
                energyComponent.energy = MathUtils.clamp(energyComponent.energy, 0f, energyComponent.maxEnergy);
                AttackManager am = new AttackManager();
                Vector2 dir = new Vector2();

                if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    dir.set(-1, 1);
                } else if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    dir.set(1, 1);
                } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    dir.set(1, -1);
                } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    dir.set(-1, -1);
                } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    dir.set(0, 1);
                } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    dir.set(-1, 0);
                } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    dir.set(0, -1);
                } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    dir.set(1, 0);
                } else if (Gdx.input.isTouched()) {
                    dir.set(Gdx.input.getX() - Gdx.graphics.getWidth() / 2 + positionComponent.x, (Gdx.graphics.getHeight() - Gdx.input.getY()) - Gdx.graphics.getHeight() / 2 +positionComponent.y);
                }
                //                am.shootNormal(e, dir, null);
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
