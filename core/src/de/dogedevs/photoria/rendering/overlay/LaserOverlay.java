package de.dogedevs.photoria.rendering.overlay;

import com.badlogic.gdx.graphics.OrthographicCamera;
import de.dogedevs.photoria.rendering.laser.Laser;

/**
 * Created by elektropapst on 27.12.2015.
 */
public class LaserOverlay extends AbstractOverlay {
    private OrthographicCamera camera;
    private Laser laser;

    public LaserOverlay(OrthographicCamera camera) {
        init();
        this.camera = camera;
    }

    @Override
    public void init() {
        laser = new Laser();
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render() {
//        batch.setProjectionMatrix(camera.combined);
        batch.begin();
//        laser.render(batch, Gdx.graphics.getDeltaTime());
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
