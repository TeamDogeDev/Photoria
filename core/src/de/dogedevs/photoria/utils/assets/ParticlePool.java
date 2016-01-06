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

    private ParticleEffect bloodPrototype;
    private ParticleEffectPool bloodPool;
    private Array<ParticleEffectPool.PooledEffect> bloodEffects;


    private static ParticlePool instance;

    public void removeEffect(ParticleEffectPool.PooledEffect effect, boolean b) {
        bloodEffects.removeValue(effect, b);
    }

    public enum ParticleType {
        BLOOD
    }

    public static ParticlePool instance() {
        return (instance = (instance == null ? new ParticlePool() : instance));
    }

    private ParticlePool() {
        initEffects();
    }

    private void initEffects() {
        initBlood();
    }

    private void initBlood() {
        bloodPrototype = new ParticleEffect();
        bloodPrototype.load(Gdx.files.internal("./effects/blood.p"), Gdx.files.internal("./effects/images"));
        bloodPool = new ParticleEffectPool(bloodPrototype, 25, 100);
        bloodEffects = new Array<>();
    }

    public void createParticleAt(ParticleType particleType, float x, float y) {
        switch(particleType) {
            case BLOOD:
                ParticleEffectPool.PooledEffect bloodEffect = bloodPool.obtain();
                bloodEffect.setPosition(x, y);
                bloodEffects.add(bloodEffect);
                bloodEffect.start();
        }
    }

    public Array<ParticleEffectPool.PooledEffect> getEffects() {
        return bloodEffects;
    }

    public void dispose() {
    }

    class GameEffect {
        ParticleEffect prototype;
        ParticleEffectPool pool;
        Array<ParticleEffectPool.PooledEffect> effects;

        public GameEffect() {}
        public GameEffect(FileHandle effectFile, FileHandle imageDir, int initialCapacity, int maxCapacity) {
            prototype = new ParticleEffect();
            prototype.load(effectFile, imageDir);
            pool = new ParticleEffectPool(prototype, initialCapacity, maxCapacity);
            effects = new Array<>();
        }

        public void dispose() {
            prototype.dispose();
        }
    }

}

