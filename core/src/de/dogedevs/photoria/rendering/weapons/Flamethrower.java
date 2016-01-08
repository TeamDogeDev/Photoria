package de.dogedevs.photoria.rendering.weapons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.dogedevs.photoria.utils.assets.ParticlePool;

/**
 * Created by elektropapst on 08.01.2016.
 */
public class Flamethrower {

    public Vector2 beginVec, endVec;
    public float rotation;
    private float length = 300;
    public Flamethrower() {
        beginVec = new Vector2(100,100);
        endVec = new Vector2(0,0);
    }


    public void render(Batch batch, float deltaTime, float z) {
//        ParticleEffect particleEffect = AssetLoader.getParticleEffect(Particles.FLAME_THROWER);
        ParticlePool.instance().createParticleAt(ParticlePool.ParticleType.FLAME_THROWER, beginVec.x, beginVec.y+z, rotation, 10);
    }

    public void setBegin(Vector2 begin) {
        this.beginVec = begin;
    }

    public void setAngle(Vector2 angle){
        endVec = new Vector2(angle);
        rotation = endVec.sub(beginVec).angle();
    }

    public Vector2 getEndPoint(){
        float rot = rotation;
        endVec.x = beginVec.x + MathUtils.cosDeg(rot) * length;
        endVec.y = beginVec.y + MathUtils.sinDeg(rot) * length;
        return endVec;
    }
}
