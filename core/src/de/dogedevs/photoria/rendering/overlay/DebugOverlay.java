package de.dogedevs.photoria.rendering.overlay;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import de.dogedevs.photoria.model.map.ChunkBuffer;
import de.dogedevs.photoria.rendering.laser.Laser;

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
    private Laser laser;

    public DebugOverlay(OrthographicCamera camera, PooledEngine ashley, ChunkBuffer chunkBuffer) {
        init();
        this.chunkBuffer = chunkBuffer;
        this.camera = camera;
        this.ashley = ashley;
    }

    @Override
    public void init() {
        font = new BitmapFont();
        laser = new Laser();
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render() {
        batch.begin();

        laser.render(batch, Gdx.graphics.getDeltaTime());

        font.draw(batch, "chunks="+chunkBuffer.getChunkCount(), 1070, 180, 200, Align.right, false);
        font.draw(batch, "cam x="+floatFormat.format(camera.position.x), 1070, 160, 200, Align.right, false);
        font.draw(batch, "cam y="+floatFormat.format(camera.position.y) , 1070, 140, 200, Align.right, false);
        font.draw(batch, "entities="+ashley.getEntities().size(), 1070, 120, 200, Align.right, false);
        font.draw(batch, "zoom="+camera.zoom, 1070, 100, 200, Align.right, false);
        font.draw(batch, "x="+Math.round((camera.position.x)/32), 1070, 80, 200, Align.right, false);
        font.draw(batch, "y="+Math.round((camera.position.y)/32) , 1070, 60, 200, Align.right, false);
        font.draw(batch, "chunk x="+Math.round((camera.position.x)/32/64), 1070, 40, 200, Align.right, false);
        font.draw(batch, "chunk y="+Math.round((camera.position.y)/32/64) , 1070, 20, 200, Align.right, false);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
    }
}
