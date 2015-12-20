package de.dogedevs.photoria.rendering.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class BasicTile extends Tile {

    private boolean solid;

    public BasicTile(TextureRegion textureRegion, boolean solid) {
        super(textureRegion);
        this.solid = solid;
    }

    public BasicTile(StaticTiledMapTile copy) {
        super(copy);
    }

    public boolean isSolid() {
        return solid;
    }
}
