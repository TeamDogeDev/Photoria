package de.dogedevs.photoria.rendering.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class BasicTile extends Tile {


    public BasicTile(TextureRegion textureRegion, boolean opaque) {
        super(textureRegion);
        if(opaque){
            this.setBlendMode(BlendMode.NONE);
        }
    }

    public BasicTile(StaticTiledMapTile copy) {
        super(copy);
    }

}
