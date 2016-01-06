package de.dogedevs.photoria.rendering.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import de.dogedevs.photoria.model.map.ChunkBuffer;
import de.dogedevs.photoria.utils.assets.AssetLoader;
import de.dogedevs.photoria.utils.assets.enums.Textures;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by elektropapst on 20.12.2015.
 */
public abstract class Tile extends StaticTiledMapTile {

//    private static final String TILESET_PATH ="./tilesets/tileset.png";
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;

    protected static Map<Integer, Texture> tileTexture = new HashMap<>();


    protected static Map<Integer, TextureRegion[][]> tiles = new HashMap<>();
    // Map<Biome, Map<TileMapperTile, Tile>>

    protected static Map<Integer, Map<Integer, Tile>> tiles2 = new HashMap<>();

    static {
        tileTexture.put(ChunkBuffer.TUNDRA, AssetLoader.getTexture(Textures.TUNDRA_TILESET));
        tiles.put(ChunkBuffer.TUNDRA, TextureRegion.split(tileTexture.get(ChunkBuffer.TUNDRA), TILE_WIDTH, TILE_HEIGHT));

        tileTexture.put(ChunkBuffer.GRASS_DESERT_BIOM, AssetLoader.getTexture(Textures.GRASS_DESERT_TILESET));
        tiles.put(ChunkBuffer.GRASS_DESERT_BIOM, TextureRegion.split(tileTexture.get(ChunkBuffer.GRASS_DESERT_BIOM), TILE_WIDTH, TILE_HEIGHT));

        tileTexture.put(ChunkBuffer.DESERT_BIOM, AssetLoader.getTexture(Textures.DESERT_TILESET));
        tiles.put(ChunkBuffer.DESERT_BIOM, TextureRegion.split(tileTexture.get(ChunkBuffer.DESERT_BIOM), TILE_WIDTH, TILE_HEIGHT));

        tileTexture.put(ChunkBuffer.TAIGA_BIOM, AssetLoader.getTexture(Textures.TAIGA_TILESET));
        tiles.put(ChunkBuffer.TAIGA_BIOM, TextureRegion.split(tileTexture.get(ChunkBuffer.TAIGA_BIOM), TILE_WIDTH, TILE_HEIGHT));

        tileTexture.put(ChunkBuffer.WOODS_BIOM, AssetLoader.getTexture(Textures.WOODS_TILESET));
        tiles.put(ChunkBuffer.WOODS_BIOM, TextureRegion.split(tileTexture.get(ChunkBuffer.WOODS_BIOM), TILE_WIDTH, TILE_HEIGHT));

        tileTexture.put(ChunkBuffer.SAVANNA_BIOM, AssetLoader.getTexture(Textures.SAVANNA_TILESET));
        tiles.put(ChunkBuffer.SAVANNA_BIOM, TextureRegion.split(tileTexture.get(ChunkBuffer.SAVANNA_BIOM), TILE_WIDTH, TILE_HEIGHT));

        tileTexture.put(ChunkBuffer.FOREST_BIOM, AssetLoader.getTexture(Textures.FOREST_TILESET));
        tiles.put(ChunkBuffer.FOREST_BIOM, TextureRegion.split(tileTexture.get(ChunkBuffer.FOREST_BIOM), TILE_WIDTH, TILE_HEIGHT));

        tileTexture.put(ChunkBuffer.SEASONAL_FOREST_BIOM, AssetLoader.getTexture(Textures.SEASONAL_FOREST_TILESET));
        tiles.put(ChunkBuffer.SEASONAL_FOREST_BIOM, TextureRegion.split(tileTexture.get(ChunkBuffer.SEASONAL_FOREST_BIOM), TILE_WIDTH, TILE_HEIGHT));

        tileTexture.put(ChunkBuffer.SWAMP_BIOM, AssetLoader.getTexture(Textures.SWAMP_TILESET));
        tiles.put(ChunkBuffer.SWAMP_BIOM, TextureRegion.split(tileTexture.get(ChunkBuffer.SWAMP_BIOM), TILE_WIDTH, TILE_HEIGHT));

        tileTexture.put(ChunkBuffer.RAIN_FOREST_BIOM, AssetLoader.getTexture(Textures.RAIN_FOREST_TILESET));
        tiles.put(ChunkBuffer.RAIN_FOREST_BIOM, TextureRegion.split(tileTexture.get(ChunkBuffer.RAIN_FOREST_BIOM), TILE_WIDTH, TILE_HEIGHT));



        // TODO MIN MAX ALL
        for (int i = 0; i < 9+1; i++) {

            Map<Integer, Tile> biomTiles = new HashMap<>();
            TextureRegion[][] localTiles = tiles.get(i);

            biomTiles.put(TileMapper.VOID, new BasicTile(localTiles[0][0], false));
            biomTiles.put(TileMapper.GROUND, new BasicTile(localTiles[4][1], true));
            biomTiles.put(TileMapper.GROUND2, new BasicTile(localTiles[6][0], true));
            biomTiles.put(TileMapper.GROUND3, new BasicTile(localTiles[6][1], true));
            biomTiles.put(TileMapper.GROUND4, new BasicTile(localTiles[6][2], true));

            biomTiles.put(TileMapper.WATER, new BasicTile(localTiles[4][7], true));
            biomTiles.put(TileMapper.WATER2, new BasicTile(localTiles[6][6], true));
            biomTiles.put(TileMapper.WATER3, new BasicTile(localTiles[6][7], true));
            biomTiles.put(TileMapper.WATER4, new BasicTile(localTiles[6][8], true));
            biomTiles.put(TileMapper.WATER_TOP_LEFT, new BasicTile(localTiles[3][6], false));
            biomTiles.put(TileMapper.WATER_TOP_MIDDLE, new BasicTile(localTiles[3][7], false));
            biomTiles.put(TileMapper.WATER_TOP_RIGHT, new BasicTile(localTiles[3][8], false));

            biomTiles.put(TileMapper.WATER_MIDDLE_LEFT, new BasicTile(localTiles[4][6], false));
            biomTiles.put(TileMapper.WATER_MIDDLE_RIGHT, new BasicTile(localTiles[4][8], false));

            biomTiles.put(TileMapper.WATER_BOTTOM_LEFT, new BasicTile(localTiles[5][6], false));
            biomTiles.put(TileMapper.WATER_BOTTOM_MIDDLE, new BasicTile(localTiles[5][7], false));
            biomTiles.put(TileMapper.WATER_BOTTOM_RIGHT, new BasicTile(localTiles[5][8], false));

            biomTiles.put(TileMapper.WATER_TOP_LEFT_INNER, new BasicTile(localTiles[2][8], false));
            biomTiles.put(TileMapper.WATER_TOP_RIGHT_INNER, new BasicTile(localTiles[2][7], false));
            biomTiles.put(TileMapper.WATER_BOTTOM_LEFT_INNER, new BasicTile(localTiles[1][8], false));
            biomTiles.put(TileMapper.WATER_BOTTOM_RIGHT_INNER, new BasicTile(localTiles[1][7], false));

            biomTiles.put(TileMapper.LAVA, new BasicTile(localTiles[4][4], true));
            biomTiles.put(TileMapper.LAVA2, new BasicTile(localTiles[6][3], true));
            biomTiles.put(TileMapper.LAVA3, new BasicTile(localTiles[6][4], true));
            biomTiles.put(TileMapper.LAVA4, new BasicTile(localTiles[6][5], true));
            biomTiles.put(TileMapper.LAVA_TOP_LEFT, new BasicTile(localTiles[3][3], false));
            biomTiles.put(TileMapper.LAVA_TOP_MIDDLE, new BasicTile(localTiles[3][4], false));
            biomTiles.put(TileMapper.LAVA_TOP_RIGHT, new BasicTile(localTiles[3][5], false));

            biomTiles.put(TileMapper.LAVA_MIDDLE_LEFT, new BasicTile(localTiles[4][3], false));
            biomTiles.put(TileMapper.LAVA_MIDDLE_RIGHT, new BasicTile(localTiles[4][5], false));

            biomTiles.put(TileMapper.LAVA_BOTTOM_LEFT, new BasicTile(localTiles[5][3], false));
            biomTiles.put(TileMapper.LAVA_BOTTOM_MIDDLE, new BasicTile(localTiles[5][4], false));
            biomTiles.put(TileMapper.LAVA_BOTTOM_RIGHT, new BasicTile(localTiles[5][5], false));

            biomTiles.put(TileMapper.LAVA_TOP_LEFT_INNER, new BasicTile(localTiles[2][5], false));
            biomTiles.put(TileMapper.LAVA_TOP_RIGHT_INNER, new BasicTile(localTiles[2][4], false));
            biomTiles.put(TileMapper.LAVA_BOTTOM_LEFT_INNER, new BasicTile(localTiles[1][5], false));
            biomTiles.put(TileMapper.LAVA_BOTTOM_RIGHT_INNER, new BasicTile(localTiles[1][4], false));


            biomTiles.put(TileMapper.LAVA_STONE, new BasicTile(localTiles[4][10], true));
            biomTiles.put(TileMapper.LAVA_STONE2, new BasicTile(localTiles[6][9], true));
            biomTiles.put(TileMapper.LAVA_STONE3, new BasicTile(localTiles[6][10], true));
            biomTiles.put(TileMapper.LAVA_STONE4, new BasicTile(localTiles[6][11], true));
            biomTiles.put(TileMapper.LAVA_STONE_TOP_LEFT, new BasicTile(localTiles[7][5], false));
            biomTiles.put(TileMapper.LAVA_STONE_TOP_MIDDLE, new BasicTile(localTiles[7][6], false));
            biomTiles.put(TileMapper.LAVA_STONE_TOP_RIGHT, new BasicTile(localTiles[7][7], false));

            biomTiles.put(TileMapper.LAVA_STONE_MIDDLE_LEFT, new BasicTile(localTiles[8][5], false));
            biomTiles.put(TileMapper.LAVA_STONE_MIDDLE_RIGHT, new BasicTile(localTiles[8][7], false));

            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_LEFT_0, new BasicTile(localTiles[9][5], false));
            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_LEFT_1, new BasicTile(localTiles[10][5], false));
            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_LEFT_2, new BasicTile(localTiles[11][5], false));

            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_MIDDLE_0, new BasicTile(localTiles[9][6], false));
            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_MIDDLE_1, new BasicTile(localTiles[10][6], false));
            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_MIDDLE_2, new BasicTile(localTiles[11][6], false));

            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_RIGHT_0, new BasicTile(localTiles[9][7], false));
            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_RIGHT_1, new BasicTile(localTiles[10][7], false));
            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_RIGHT_2, new BasicTile(localTiles[11][7], false));

            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_LEFT_WALL_0, new BasicTile(localTiles[7][13], false));
            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_LEFT_WALL_1, new BasicTile(localTiles[8][13], false));

            biomTiles.put(TileMapper.LAVA_STONE_MIDDLE_LEFT_WALL, new BasicTile(localTiles[9][8], false));
            biomTiles.put(TileMapper.LAVA_STONE_MIDDLE_LEFT_WALL_CORNER, new BasicTile(localTiles[9][13], false));
            biomTiles.put(TileMapper.LAVA_STONE_MIDDLE_LEFT_WALL_STRAIGHT, new BasicTile(localTiles[10][8], false));

            biomTiles.put(TileMapper.LAVA_STONE_TOP_LEFT_INNER, new BasicTile(localTiles[7][8], false));
            biomTiles.put(TileMapper.LAVA_STONE_TOP_RIGHT_INNER, new BasicTile(localTiles[7][9], false));
            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_LEFT_INNER, new BasicTile(localTiles[8][8], false));
            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_RIGHT_INNER, new BasicTile(localTiles[8][9], false));

            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_RIGHT_WALL_0, new BasicTile(localTiles[7][12], false));
            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_RIGHT_WALL_1, new BasicTile(localTiles[8][12], false));

            biomTiles.put(TileMapper.LAVA_STONE_MIDDLE_RIGHT_WALL, new BasicTile(localTiles[9][9], false));
            biomTiles.put(TileMapper.LAVA_STONE_MIDDLE_RIGHT_WALL_CORNER, new BasicTile(localTiles[9][12], false));
            biomTiles.put(TileMapper.LAVA_STONE_MIDDLE_RIGHT_WALL_STRAIGHT, new BasicTile(localTiles[10][9], false));

            biomTiles.put(TileMapper.LAVA_DECO_1, new BasicTile(localTiles[2][3], false));


            biomTiles.put(TileMapper.DEBUG, new BasicTile(localTiles[31][31], false));

            tiles2.put(i, biomTiles);
        }
    }


//    public static final Tile VOID = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[0][0], false);

//    public static final Tile GROUND = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[4][1], true);
//    public static final Tile GROUND2 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[6][0], true);
//    public static final Tile GROUND3 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[6][1], true);
//    public static final Tile GROUND4 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[6][2], true);
//
//    public static final Tile WATER = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[4][7], true);
//    public static final Tile WATER2 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[6][6], true);
//    public static final Tile WATER3 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[6][7], true);
//    public static final Tile WATER4 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[6][8], true);
//    public static final Tile WATER_TOP_LEFT = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[3][6], false);
//    public static final Tile WATER_TOP_MIDDLE = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[3][7], false);
//    public static final Tile WATER_TOP_RIGHT = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[3][8], false);
//
//    public static final Tile WATER_MIDDLE_LEFT = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[4][6], false);
//    public static final Tile WATER_MIDDLE_RIGHT = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[4][8], false);
//
//    public static final Tile WATER_BOTTOM_LEFT = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[5][6], false);
//    public static final Tile WATER_BOTTOM_MIDDLE = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[5][7], false);
//    public static final Tile WATER_BOTTOM_RIGHT = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[5][8], false);
//
//    public static final Tile WATER_TOP_LEFT_INNER = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[2][8], false);
//    public static final Tile WATER_TOP_RIGHT_INNER = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[2][7], false);
//    public static final Tile WATER_BOTTOM_LEFT_INNER = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[1][8], false);
//    public static final Tile WATER_BOTTOM_RIGHT_INNER = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[1][7], false);
//
//    public static final Tile LAVA = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[4][4], true);
//    public static final Tile LAVA2 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[6][3], true);
//    public static final Tile LAVA3 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[6][4], true);
//    public static final Tile LAVA4 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[6][5], true);
//    public static final Tile LAVA_TOP_LEFT = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[3][3], false);
//    public static final Tile LAVA_TOP_MIDDLE = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[3][4], false);
//    public static final Tile LAVA_TOP_RIGHT = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[3][5], false);
//
//    public static final Tile LAVA_MIDDLE_LEFT = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[4][3], false);
//    public static final Tile LAVA_MIDDLE_RIGHT = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[4][5], false);
//
//    public static final Tile LAVA_BOTTOM_LEFT = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[5][3], false);
//    public static final Tile LAVA_BOTTOM_MIDDLE = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[5][4], false);
//    public static final Tile LAVA_BOTTOM_RIGHT = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[5][5], false);
//
//    public static final Tile LAVA_TOP_LEFT_INNER = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[2][5], false);
//    public static final Tile LAVA_TOP_RIGHT_INNER = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[2][4], false);
//    public static final Tile LAVA_BOTTOM_LEFT_INNER = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[1][5], false);
//    public static final Tile LAVA_BOTTOM_RIGHT_INNER = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[1][4], false);
//
//
//    public static final Tile LAVA_STONE = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[4][10], true);
//    public static final Tile LAVA_STONE2 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[6][9], true);
//    public static final Tile LAVA_STONE3 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[6][10], true);
//    public static final Tile LAVA_STONE4 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[6][11], true);
//    public static final Tile LAVA_STONE_TOP_LEFT = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[7][5], false);
//    public static final Tile LAVA_STONE_TOP_MIDDLE = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[7][6], false);
//    public static final Tile LAVA_STONE_TOP_RIGHT = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[7][7], false);
//
//    public static final Tile LAVA_STONE_MIDDLE_LEFT = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[8][5], false);
//    public static final Tile LAVA_STONE_MIDDLE_RIGHT = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[8][7], false);
//
//    public static final Tile LAVA_STONE_BOTTOM_LEFT_0 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[9][5], false);
//    public static final Tile LAVA_STONE_BOTTOM_LEFT_1 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[10][5], false);
//    public static final Tile LAVA_STONE_BOTTOM_LEFT_2 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[11][5], false);
//
//    public static final Tile LAVA_STONE_BOTTOM_MIDDLE_0 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[9][6], false);
//    public static final Tile LAVA_STONE_BOTTOM_MIDDLE_1 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[10][6], false);
//    public static final Tile LAVA_STONE_BOTTOM_MIDDLE_2 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[11][6], false);
//
//    public static final Tile LAVA_STONE_BOTTOM_RIGHT_0 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[9][7], false);
//    public static final Tile LAVA_STONE_BOTTOM_RIGHT_1 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[10][7], false);
//    public static final Tile LAVA_STONE_BOTTOM_RIGHT_2 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[11][7], false);
//
//    public static final Tile LAVA_STONE_BOTTOM_LEFT_WALL_0 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[7][13], false);
//    public static final Tile LAVA_STONE_BOTTOM_LEFT_WALL_1 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[8][13], false);
//
//    public static final Tile LAVA_STONE_MIDDLE_LEFT_WALL = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[9][8], false);
//    public static final Tile LAVA_STONE_MIDDLE_LEFT_WALL_CORNER = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[9][13], false);
//    public static final Tile LAVA_STONE_MIDDLE_LEFT_WALL_STRAIGHT = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[10][8], false);
//
//    public static final Tile LAVA_STONE_TOP_LEFT_INNER = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[7][8], false);
//    public static final Tile LAVA_STONE_TOP_RIGHT_INNER = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[7][9], false);
//    public static final Tile LAVA_STONE_BOTTOM_LEFT_INNER = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[8][8], false);
//    public static final Tile LAVA_STONE_BOTTOM_RIGHT_INNER = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[8][9], false);
//
//    public static final Tile LAVA_STONE_BOTTOM_RIGHT_WALL_0 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[7][12], false);
//    public static final Tile LAVA_STONE_BOTTOM_RIGHT_WALL_1 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[8][12], false);
//
//    public static final Tile LAVA_STONE_MIDDLE_RIGHT_WALL = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[9][9], false);
//    public static final Tile LAVA_STONE_MIDDLE_RIGHT_WALL_CORNER = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[9][12], false);
//    public static final Tile LAVA_STONE_MIDDLE_RIGHT_WALL_STRAIGHT = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[10][9], false);
//
//    public static final Tile LAVA_DECO_1 = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[2][3], false);
//
//
//    public static final Tile DEBUG = new BasicTile(tiles.get(ChunkBuffer.DESERT_BIOM)[31][31], false);

    public static Tile getTileForBiome(int tileMapperTile, int biome) {

        return tiles2.get(biome).get(tileMapperTile);
    }

    public Tile(TextureRegion textureRegion) {
        super(textureRegion);
    }

    public Tile(StaticTiledMapTile copy) {
        super(copy);
    }
}
