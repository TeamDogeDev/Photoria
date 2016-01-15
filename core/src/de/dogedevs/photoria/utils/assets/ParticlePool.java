package de.dogedevs.photoria.utils.assets;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.utils.Array;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.utils.assets.enums.Particles;

/**
 * Created by elektropapst on 06.01.2016.
 */
public class ParticlePool {

//    private ParticleEffect bloodPrototype;
//    private ParticleEffectPool bloodPool;
//    private Array<ParticleEffectPool.PooledEffect> bloodEffects;


    Array<ParticleEffectPool.PooledEffect> effects = new Array<>();

    public void removeEffect(ParticleEffectPool.PooledEffect effect, boolean b) {
        boolean b1 = effects.removeValue(effect, b);
    }

    public enum ParticleType {

        BLOOD(new GameEffect(Statics.asset.getParticleEffect(Particles.BLOOD_PARTICLE), 25, 100)),
        FIRE(new GameEffect(Statics.asset.getParticleEffect(Particles.FIRE_PARTICLE), 25, 100)),
        FLAME_THROWER(new GameEffect(Statics.asset.getParticleEffect(Particles.FLAME_THROWER), 25, 100)),
        ENERGY_BALL(new GameEffect(Statics.asset.getParticleEffect(Particles.ENERGY_BALL), 25, 100)),
        SLIME_SPLASH(new GameEffect(Statics.asset.getParticleEffect(Particles.SLIME_SPLASH), 25, 100));

        GameEffect gameEffect;
        ParticleType(GameEffect gameEffect) {
            this.gameEffect = gameEffect;
        }

        void dispose() {
            gameEffect.dispose();
        }
    }

    public ParticlePool() {
    }

    public int getPoolPeek(ParticleType type) {
        return type.gameEffect.pool.peak;
    }

    public int getPoolMax(ParticleType type) {
        return type.gameEffect.pool.max;
    }

    public void createParticleAt(ParticleType particleType, float x, float y) {
        ParticleEffectPool.PooledEffect effect = particleType.gameEffect.pool.obtain();
        effect.reset();
        effect.setPosition(x, y);
        effects.add(effect);
        effect.start();
    }

    public void createParticleAt(ParticleType particleType, float x, float y, float newAngle, float spr) {
        ParticleEffectPool.PooledEffect effect = particleType.gameEffect.pool.obtain();
        effect.reset();
        effect.setPosition(x, y);
        for(ParticleEmitter emitter : effect.getEmitters()) {
            emitter.getAngle().setHigh(newAngle-spr, newAngle+spr);
            emitter.getAngle().setLow(newAngle-spr, newAngle+spr);
        }
        effects.add(effect);
        effect.start();
    }

    public void createParticleAt(ParticleType particleType, float x, float y, float newAngle) {
        createParticleAt(particleType, x, y, newAngle, 0);
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

        public GameEffect(ParticleEffect prototype, int initialCapacity, int maxCapacity) {
            this.prototype = prototype;
            pool = new ParticleEffectPool(prototype, initialCapacity, maxCapacity);
        }

        public void dispose() {
            prototype.dispose();
        }
    }

}

