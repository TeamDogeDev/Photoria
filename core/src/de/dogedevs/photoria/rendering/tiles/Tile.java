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

    public static final Tile D5 = new BasicTile(tiles[0][0], false);
    public static final Tile D4 = new BasicTile(tiles[0][1], false);
    public static final Tile D3 = new BasicTile(tiles[0][2], false);
    public static final Tile D2 = new BasicTile(tiles[0][3], false);
    public static final Tile D1 = new BasicTile(tiles[0][4], false);
    public static final Tile D0 = new BasicTile(tiles[0][5], false);
    public static final Tile GROUND = new BasicTile(tiles[6][2], false);
    public static final Tile U0 = new BasicTile(tiles[0][7], false);
    public static final Tile U1 = new BasicTile(tiles[0][8], false);
    public static final Tile U2 = new BasicTile(tiles[0][9], false);
    public static final Tile U3 = new BasicTile(tiles[0][10], false);
    public static final Tile U4 = new BasicTile(tiles[0][11], false);
    public static final Tile U5 = new BasicTile(tiles[0][12], false);

    public static final Tile WATER = new BasicTile(tiles[6][7], false);
    public static final Tile LAVA = new BasicTile(tiles[4][4], false);
    public static final Tile LAVA_STONE = new BasicTile(tiles[4][10], false);

    public static final Tile DEBUG = new BasicTile(tiles[31][31], false);
    public Tile(TextureRegion textureRegion) {
        super(textureRegion);
    }

    public Tile(StaticTiledMapTile copy) {
        super(copy);
    }
}
