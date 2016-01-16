package de.dogedevs.photoria.content.weapons;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

/**
 * Created by Furuha on 08.01.2016.
 */
public interface Weapon {

    public void render(Batch batch, float deltaTime, float z);

    public void inactive(float deltaTime);

    public void setBegin(Vector2 begin);

    public void setAngle(Vector2 angle);

    public void setRange(float range);

    public void setColors(Color... colors);

    public void checkCollision(ImmutableArray<Entity> entityList, List<Entity> resultList); //second parameter to avoid GC

    public boolean despawnOnStop();

    public void setOwner(Entity owner);

    public void setAdditionalThrust(Vector2 thrust);

}
