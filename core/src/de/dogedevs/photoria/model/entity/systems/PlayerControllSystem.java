package de.dogedevs.photoria.model.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.content.weapons.Shooter;
import de.dogedevs.photoria.content.weapons.Weapon;
import de.dogedevs.photoria.model.entity.ComponentMappers;
import de.dogedevs.photoria.model.entity.EntityLoader;
import de.dogedevs.photoria.model.entity.components.PlayerComponent;
import de.dogedevs.photoria.model.entity.components.PositionComponent;
import de.dogedevs.photoria.model.entity.components.TargetComponent;
import de.dogedevs.photoria.model.entity.components.VelocityComponent;
import de.dogedevs.photoria.model.map.ChunkBuffer;
import de.dogedevs.photoria.rendering.overlay.GameOverlay;
import de.dogedevs.photoria.utils.assets.enums.Musics;

import java.util.UUID;

/**
 * Created by Furuha on 21.12.2015.
 */
public class PlayerControllSystem extends EntitySystem {

    private final ChunkBuffer buffer;
    private ImmutableArray<Entity> entities;
    public static float speed = 128*2;
    public static float defaultSpeed = 128*2;

    public PlayerControllSystem(ChunkBuffer buffer) {
        this.buffer = buffer;
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
            Statics.music.playMusic(Musics.TITLE, false);

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
            Statics.music.playMusic(Musics.AMBIENT, false);
        }



        if (entities.size() == 0) {
            return;
        }
        final Entity e = entities.get(0);

        PositionComponent positionComponent = ComponentMappers.position.get(e);

        if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            EntityLoader el = new EntityLoader();
            el.createTerraFormingRamp(positionComponent.x, positionComponent.y+32, buffer);
        }

        if(!Gdx.input.isTouched()){
            TargetComponent target = ComponentMappers.target.get(e);
            if(target != null){
                target.isShooting = false;
            }
        } else {
            TargetComponent target = ComponentMappers.target.get(e);
            if(target == null){
                target = Statics.ashley.createComponent(TargetComponent.class);
                e.add(target);
                Statics.attack.deleteWeaponsFrom(e);
                Weapon weapon = new Shooter();
                weapon.setRange(350);
                Statics.attack.createAttack(e, weapon);
            }

            target.x = Gdx.input.getX() - Gdx.graphics.getWidth() / 2 + positionComponent.x;
            target.y = (Gdx.graphics.getHeight() - Gdx.input.getY()) - Gdx.graphics.getHeight() / 2 +positionComponent.y;
            target.isShooting = true;
        }


        VelocityComponent velocity = ComponentMappers.velocity.get(e);
        velocity.speed = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.speed = speed;
            velocity.direction = VelocityComponent.NORTH_WEST;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.speed = speed;
            velocity.direction = VelocityComponent.NORTH_EAST;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.speed = speed;
            velocity.direction = VelocityComponent.SOUTH_EAST;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.speed = speed;
            velocity.direction = VelocityComponent.SOUTH_WEST;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.speed = speed;
            velocity.direction = VelocityComponent.NORTH;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.speed = speed;
            velocity.direction = VelocityComponent.WEST;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.speed = speed;
            velocity.direction = VelocityComponent.SOUTH;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.speed = speed;
            velocity.direction = VelocityComponent.EAST;
        }


    }
}
