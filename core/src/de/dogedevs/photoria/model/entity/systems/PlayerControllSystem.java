package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.dogedevs.photoria.content.weapons.AttackManager;
import de.dogedevs.photoria.content.weapons.Laser;
import de.dogedevs.photoria.content.weapons.Weapon;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.components.PlayerComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.entity.components.TargetComponent;
import de.dogedevs.photoria.model.entity.components.VelocityComponent;
import de.dogedevs.photoria.model.entity.components.stats.EnergyComponent;
import de.dogedevs.photoria.rendering.overlay.GameOverlay;
import de.dogedevs.photoria.screens.GameScreen;
import de.dogedevs.photoria.utils.assets.MusicManager;
import de.dogedevs.photoria.utils.assets.enums.Musics;

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
            TargetComponent target = ComponentMappers.target.get(e);
            if(target != null){
                target.isShooting = false;
            }
        } else {
            TargetComponent target = ComponentMappers.target.get(e);
            if(target == null){
                target = GameScreen.getAshley().createComponent(TargetComponent.class);
                e.add(target);
                AttackManager am = new AttackManager();
                am.deleteWeaponsFrom(e);
                Weapon weapon = new Laser();
                weapon.setRange(350);
                weapon.setColors(Color.RED, Color.WHITE);
                am.createAttack(e, weapon);
            }

//            if(MathUtils.randomBoolean(0.05f)){
//                AttackManager am = new AttackManager();
//                Weapon weapon;
//                int numb = MathUtils.random(0, 3);
//                if(numb == 0){
//                    am.deleteWeaponsFrom(e);
//                    weapon = new Laser();
//                    weapon.setRange(350);
//                    weapon.setColors(Color.RED, Color.WHITE);
//                    am.createAttack(e, weapon);
//                } else if(numb == 1){
//                    am.deleteWeaponsFrom(e);
//                    weapon = new Flamethrower();
//                    am.createAttack(e, weapon);
//                } else if(numb == 2){
//                    am.deleteWeaponsFrom(e);
//                    weapon = new Shooter();
//                    am.createAttack(e, weapon);
//                } else if(numb == 3){
//                    am.deleteWeaponsFrom(e);
//                    weapon = new ParticleShooter();
//                    am.createAttack(e, weapon);
//                }
//            }

            target.x = Gdx.input.getX() - Gdx.graphics.getWidth() / 2 + positionComponent.x;
            target.y = (Gdx.graphics.getHeight() - Gdx.input.getY()) - Gdx.graphics.getHeight() / 2 +positionComponent.y;
            target.isShooting = true;
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
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.speed = SPEED;
            velocity.direction = VelocityComponent.NORTH_WEST;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.speed = SPEED;
            velocity.direction = VelocityComponent.NORTH_EAST;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.speed = SPEED;
            velocity.direction = VelocityComponent.SOUTH_EAST;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.speed = SPEED;
            velocity.direction = VelocityComponent.SOUTH_WEST;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.speed = SPEED;
            velocity.direction = VelocityComponent.NORTH;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.speed = SPEED;
            velocity.direction = VelocityComponent.WEST;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.speed = SPEED;
            velocity.direction = VelocityComponent.SOUTH;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.speed = SPEED;
            velocity.direction = VelocityComponent.EAST;
        }


    }
}
