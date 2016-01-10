package de.dogedevs.photoria.rendering.overlay;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import de.dogedevs.photoria.Statics;
import de.dogedevs.photoria.model.map.ChunkBuffer;
import de.dogedevs.photoria.utils.assets.ParticlePool;

import java.text.DecimalFormat;

/**
 * Created by elektropapst on 27.12.2015.
 */
public class DebugOverlay extends AbstractOverlay {
    private ChunkBuffer chunkBuffer;
    private BitmapFont font;
    private OrthographicCamera camera;
    private DecimalFormat floatFormat = new DecimalFormat("#.##");
    private PooledEngine ashley;

    public DebugOverlay(OrthographicCamera camera, PooledEngine ashley, ChunkBuffer chunkBuffer) {
        init();
        this.chunkBuffer = chunkBuffer;
        this.camera = camera;
        this.ashley = ashley;
    }

    @Override
    public void init() {
        font = new BitmapFont();
    }

    @Override
    public void update(float delta) {
    }

    private static int startY = 200;
    @Override
    public void render() {
        batch.begin();

        font.draw(batch, "chunks="+chunkBuffer.getChunkCount(), 1070, startY, 200, Align.right, false);
        font.draw(batch, "cam x="+floatFormat.format(camera.position.x), 1070, startY-20, 200, Align.right, false);
        font.draw(batch, "cam y="+floatFormat.format(camera.position.y) , 1070, startY-40, 200, Align.right, false);
        font.draw(batch, "entities="+ashley.getEntities().size(), 1070, startY-60, 200, Align.right, false);
        font.draw(batch, "zoom="+camera.zoom, 1070, startY-80, 200, Align.right, false);
        font.draw(batch, "x="+Math.round((camera.position.x)/32), 1070, startY-100, 200, Align.right, false);
        font.draw(batch, "y="+Math.round((camera.position.y)/32) , 1070, startY-120, 200, Align.right, false);
        font.draw(batch, "chunk x="+Math.round((camera.position.x)/32/64), 1070, startY-140, 200, Align.right, false);
        font.draw(batch, "chunk y="+Math.round((camera.position.y)/32/64) , 1070, startY-160, 200, Align.right, false);
        font.draw(batch, "flameThrowerPeek=" + Statics.particle.getPoolPeek(ParticlePool.ParticleType.FLAME_THROWER), 1070, startY-180, 200, Align.right, false);

        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
    }
}
