package de.dogedevs.photoria.rendering.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

/**
 * Created by elektropapst on 20.12.2015.
 */
public abstract class Tile extends StaticTiledMapTile {

    private static final String TILESET_PATH ="./tileset.png";
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;

    protected static Texture tileTexture = new Texture(Gdx.files.internal(TILESET_PATH));
    protected static TextureRegion[][] tiles = TextureRegion.split(tileTexture, TILE_WIDTH, TILE_HEIGHT);

    public static final Tile GROUND = new BasicTile(tiles[6][2], false);
    public static final Tile VOID = new BasicTile(tiles[0][0], false);
    public static final Tile WATER = new BasicTile(tiles[4][7], false);
    public static final Tile LAVA = new BasicTile(tiles[4][4], false);
    public static final Tile LAVA_STONE = new BasicTile(tiles[4][10], false);

    public static final Tile WATER_LEFT = new BasicTile(tiles[4][6], false);

    public static final Tile DEBUG = new BasicTile(tiles[31][31], false);
    public Tile(TextureRegion textureRegion) {
        super(textureRegion);
    }

    public Tile(StaticTiledMapTile copy) {
        super(copy);
    }
}
