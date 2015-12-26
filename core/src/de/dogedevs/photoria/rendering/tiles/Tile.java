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

    public static final Tile VOID = new BasicTile(tiles[0][0], false, false);

    public static final Tile GROUND = new BasicTile(tiles[4][1], false, true);
    public static final Tile GROUND2 = new BasicTile(tiles[6][0], false, true);
    public static final Tile GROUND3 = new BasicTile(tiles[6][1], false, true);
    public static final Tile GROUND4 = new BasicTile(tiles[6][2], false, true);

    public static final Tile WATER = new BasicTile(tiles[4][7], false, true);
    public static final Tile WATER2 = new BasicTile(tiles[6][6], false, true);
    public static final Tile WATER3 = new BasicTile(tiles[6][7], false, true);
    public static final Tile WATER4 = new BasicTile(tiles[6][8], false, true);
    public static final Tile WATER_TOP_LEFT = new BasicTile(tiles[3][6], false, false);
    public static final Tile WATER_TOP_MIDDLE = new BasicTile(tiles[3][7], false, false);
    public static final Tile WATER_TOP_RIGHT = new BasicTile(tiles[3][8], false, false);

    public static final Tile WATER_MIDDLE_LEFT = new BasicTile(tiles[4][6], false, false);
    public static final Tile WATER_MIDDLE_RIGHT = new BasicTile(tiles[4][8], false, false);

    public static final Tile WATER_BOTTOM_LEFT = new BasicTile(tiles[5][6], false, false);
    public static final Tile WATER_BOTTOM_MIDDLE = new BasicTile(tiles[5][7], false, false);
    public static final Tile WATER_BOTTOM_RIGHT = new BasicTile(tiles[5][8], false, false);

    public static final Tile WATER_TOP_LEFT_INNER = new BasicTile(tiles[2][8], false, false);
    public static final Tile WATER_TOP_RIGHT_INNER = new BasicTile(tiles[2][7], false, false);
    public static final Tile WATER_BOTTOM_LEFT_INNER = new BasicTile(tiles[1][8], false, false);
    public static final Tile WATER_BOTTOM_RIGHT_INNER = new BasicTile(tiles[1][7], false, false);

    public static final Tile LAVA = new BasicTile(tiles[4][4], false, true);
    public static final Tile LAVA2 = new BasicTile(tiles[6][3], false, true);
    public static final Tile LAVA3 = new BasicTile(tiles[6][4], false, true);
    public static final Tile LAVA4 = new BasicTile(tiles[6][5], false, true);
    public static final Tile LAVA_TOP_LEFT = new BasicTile(tiles[3][3], false, false);
    public static final Tile LAVA_TOP_MIDDLE = new BasicTile(tiles[3][4], false, false);
    public static final Tile LAVA_TOP_RIGHT = new BasicTile(tiles[3][5], false, false);

    public static final Tile LAVA_MIDDLE_LEFT = new BasicTile(tiles[4][3], false, false);
    public static final Tile LAVA_MIDDLE_RIGHT = new BasicTile(tiles[4][5], false, false);

    public static final Tile LAVA_BOTTOM_LEFT = new BasicTile(tiles[5][3], false, false);
    public static final Tile LAVA_BOTTOM_MIDDLE = new BasicTile(tiles[5][4], false, false);
    public static final Tile LAVA_BOTTOM_RIGHT = new BasicTile(tiles[5][5], false, false);

    public static final Tile LAVA_TOP_LEFT_INNER = new BasicTile(tiles[2][5], false, false);
    public static final Tile LAVA_TOP_RIGHT_INNER = new BasicTile(tiles[2][4], false, false);
    public static final Tile LAVA_BOTTOM_LEFT_INNER = new BasicTile(tiles[1][5], false, false);
    public static final Tile LAVA_BOTTOM_RIGHT_INNER = new BasicTile(tiles[1][4], false, false);


    public static final Tile LAVA_STONE = new BasicTile(tiles[4][10], false, true);
    public static final Tile LAVA_STONE2 = new BasicTile(tiles[6][9], false, true);
    public static final Tile LAVA_STONE3 = new BasicTile(tiles[6][10], false, true);
    public static final Tile LAVA_STONE4 = new BasicTile(tiles[6][11], false, true);
    public static final Tile LAVA_STONE_TOP_LEFT = new BasicTile(tiles[7][5], false, false);
    public static final Tile LAVA_STONE_TOP_MIDDLE = new BasicTile(tiles[7][6], false, false);
    public static final Tile LAVA_STONE_TOP_RIGHT = new BasicTile(tiles[7][7], false, false);

    public static final Tile LAVA_STONE_MIDDLE_LEFT = new BasicTile(tiles[8][5], false, false);
    public static final Tile LAVA_STONE_MIDDLE_RIGHT = new BasicTile(tiles[8][7], false, false);

    public static final Tile LAVA_STONE_BOTTOM_LEFT_0 = new BasicTile(tiles[9][5], false, false);
    public static final Tile LAVA_STONE_BOTTOM_LEFT_1 = new BasicTile(tiles[10][5], false, false);
    public static final Tile LAVA_STONE_BOTTOM_LEFT_2 = new BasicTile(tiles[11][5], false, false);

    public static final Tile LAVA_STONE_BOTTOM_MIDDLE_0 = new BasicTile(tiles[9][6], false, false);
    public static final Tile LAVA_STONE_BOTTOM_MIDDLE_1 = new BasicTile(tiles[10][6], false, false);
    public static final Tile LAVA_STONE_BOTTOM_MIDDLE_2 = new BasicTile(tiles[11][6], false, false);

    public static final Tile LAVA_STONE_BOTTOM_RIGHT_0 = new BasicTile(tiles[9][7], false, false);
    public static final Tile LAVA_STONE_BOTTOM_RIGHT_1 = new BasicTile(tiles[10][7], false, false);
    public static final Tile LAVA_STONE_BOTTOM_RIGHT_2 = new BasicTile(tiles[11][7], false, false);

    public static final Tile LAVA_STONE_BOTTOM_LEFT_WALL_0 = new BasicTile(tiles[7][13], false, false);
    public static final Tile LAVA_STONE_BOTTOM_LEFT_WALL_1 = new BasicTile(tiles[8][13], false, false);

    public static final Tile LAVA_STONE_MIDDLE_LEFT_WALL = new BasicTile(tiles[9][8], false, false);
    public static final Tile LAVA_STONE_MIDDLE_LEFT_WALL_CORNER = new BasicTile(tiles[9][13], false, false);
    public static final Tile LAVA_STONE_MIDDLE_LEFT_WALL_STRAIGHT = new BasicTile(tiles[10][8], false, false);

    public static final Tile LAVA_STONE_TOP_LEFT_INNER = new BasicTile(tiles[7][8], false, false);
    public static final Tile LAVA_STONE_TOP_RIGHT_INNER = new BasicTile(tiles[7][9], false, false);
    public static final Tile LAVA_STONE_BOTTOM_LEFT_INNER = new BasicTile(tiles[8][8], false, false);
    public static final Tile LAVA_STONE_BOTTOM_RIGHT_INNER = new BasicTile(tiles[8][9], false, false);

    public static final Tile LAVA_STONE_BOTTOM_RIGHT_WALL_0 = new BasicTile(tiles[7][12], false, false);
    public static final Tile LAVA_STONE_BOTTOM_RIGHT_WALL_1 = new BasicTile(tiles[8][12], false, false);

    public static final Tile LAVA_STONE_MIDDLE_RIGHT_WALL = new BasicTile(tiles[9][9], false, false);
    public static final Tile LAVA_STONE_MIDDLE_RIGHT_WALL_CORNER = new BasicTile(tiles[9][12], false, false);
    public static final Tile LAVA_STONE_MIDDLE_RIGHT_WALL_STRAIGHT = new BasicTile(tiles[10][9], false, false);




    public static final Tile DEBUG = new BasicTile(tiles[31][31], false, false);
    public Tile(TextureRegion textureRegion) {
        super(textureRegion);
    }

    public Tile(StaticTiledMapTile copy) {
        super(copy);
    }
}
