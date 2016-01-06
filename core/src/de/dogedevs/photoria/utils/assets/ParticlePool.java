package de.dogedevs.photoria.utils.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.Array;

/**
 * Created by elektropapst on 06.01.2016.
 */
public class ParticlePool {

//    private ParticleEffect bloodPrototype;
//    private ParticleEffectPool bloodPool;
//    private Array<ParticleEffectPool.PooledEffect> bloodEffects;


    Array<ParticleEffectPool.PooledEffect> effects = new Array<>();

    private static ParticlePool instance;

    public void removeEffect(ParticleEffectPool.PooledEffect effect, boolean b) {
        boolean b1 = effects.removeValue(effect, b);
    }

    public enum ParticleType {

        BLOOD(new GameEffect(Gdx.files.internal("./effects/blood.p"), Gdx.files.internal("./effects/images"), 25, 100)),
        FIRE(new GameEffect(Gdx.files.internal("./effects/fire.p"), Gdx.files.internal("./effects/images"), 25, 100));

        GameEffect gameEffect;
        ParticleType(GameEffect gameEffect) {
            this.gameEffect = gameEffect;
        }

        void dispose() {
            gameEffect.dispose();
        }
    }

    public static ParticlePool instance() {
        return (instance = (instance == null ? new ParticlePool() : instance));
    }

    private ParticlePool() {
    }

    public void createParticleAt(ParticleType particleType, float x, float y) {
        ParticleEffectPool.PooledEffect effect = particleType.gameEffect.pool.obtain();
        effect.reset();
        effect.setPosition(x, y);
        effects.add(effect);
        effect.start();
    }

    public Array<ParticleEffectPool.PooledEffect> getEffects() {
        return effects;
    }

    public void dispose() {
        for(ParticleType type : ParticleType.values()) {
            type.dispose();
        }
    }

    static class GameEffect {
        ParticleEffect prototype;
        ParticleEffectPool pool;

        public GameEffect(FileHandle effectFile, FileHandle imageDir, int initialCapacity, int maxCapacity) {
            prototype = new ParticleEffect();
            prototype.load(effectFile, imageDir);
            pool = new ParticleEffectPool(prototype, initialCapacity, maxCapacity);
        }

        public void dispose() {
            prototype.dispose();
        }
    }

}

