package de.dogedevs.photoria.rendering.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import de.dogedevs.photoria.model.map.ChunkBuffer;
import de.dogedevs.photoria.Statics;
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
        tileTexture.put(ChunkBuffer.RED_BIOM, Statics.asset.getTexture(Textures.RED_TILESET));
        tiles.put(ChunkBuffer.RED_BIOM, TextureRegion.split(tileTexture.get(ChunkBuffer.RED_BIOM), TILE_WIDTH, TILE_HEIGHT));

        tileTexture.put(ChunkBuffer.NORMAL_BIOM, Statics.asset.getTexture(Textures.NORMAL_TILESET));
        tiles.put(ChunkBuffer.NORMAL_BIOM, TextureRegion.split(tileTexture.get(ChunkBuffer.NORMAL_BIOM), TILE_WIDTH, TILE_HEIGHT));

        tileTexture.put(ChunkBuffer.BLUE_BIOM, Statics.asset.getTexture(Textures.BLUE_TILESET));
        tiles.put(ChunkBuffer.BLUE_BIOM, TextureRegion.split(tileTexture.get(ChunkBuffer.BLUE_BIOM), TILE_WIDTH, TILE_HEIGHT));

        tileTexture.put(ChunkBuffer.YELLOW_BIOM, Statics.asset.getTexture(Textures.YELLOW_TILESET));
        tiles.put(ChunkBuffer.YELLOW_BIOM, TextureRegion.split(tileTexture.get(ChunkBuffer.YELLOW_BIOM), TILE_WIDTH, TILE_HEIGHT));

        tileTexture.put(ChunkBuffer.PURPLE_BIOM, Statics.asset.getTexture(Textures.PURPLE_TILESET));
        tiles.put(ChunkBuffer.PURPLE_BIOM, TextureRegion.split(tileTexture.get(ChunkBuffer.PURPLE_BIOM), TILE_WIDTH, TILE_HEIGHT));

        tileTexture.put(ChunkBuffer.GREEN_BIOM, Statics.asset.getTexture(Textures.GREEN_TILESET));
        tiles.put(ChunkBuffer.GREEN_BIOM, TextureRegion.split(tileTexture.get(ChunkBuffer.GREEN_BIOM), TILE_WIDTH, TILE_HEIGHT));

        // TODO MIN MAX ALL
        for (int i = 0; i < 5+1; i++) {

            Map<Integer, Tile> biomTiles = new HashMap<>();
            TextureRegion[][] localTiles = tiles.get(i);
            System.out.println(i + " : " + localTiles.length + " : " + localTiles[0].length);


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
            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_LEFT_WALL_2, new BasicTile(localTiles[11][5], false));

            biomTiles.put(TileMapper.LAVA_STONE_MIDDLE_LEFT_WALL, new BasicTile(localTiles[9][8], false));
            biomTiles.put(TileMapper.LAVA_STONE_MIDDLE_LEFT_WALL_CORNER, new BasicTile(localTiles[9][13], false));
            biomTiles.put(TileMapper.LAVA_STONE_MIDDLE_LEFT_WALL_STRAIGHT, new BasicTile(localTiles[10][8], false));

            biomTiles.put(TileMapper.LAVA_STONE_TOP_LEFT_INNER, new BasicTile(localTiles[7][8], false));
            biomTiles.put(TileMapper.LAVA_STONE_TOP_RIGHT_INNER, new BasicTile(localTiles[7][9], false));
            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_LEFT_INNER, new BasicTile(localTiles[8][8], false));
            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_RIGHT_INNER, new BasicTile(localTiles[8][9], false));

            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_RIGHT_WALL_0, new BasicTile(localTiles[7][12], false));
            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_RIGHT_WALL_1, new BasicTile(localTiles[8][12], false));
            biomTiles.put(TileMapper.LAVA_STONE_BOTTOM_RIGHT_WALL_2, new BasicTile(localTiles[11][7], false));


            biomTiles.put(TileMapper.LAVA_STONE_MIDDLE_RIGHT_WALL, new BasicTile(localTiles[9][9], false));
            biomTiles.put(TileMapper.LAVA_STONE_MIDDLE_RIGHT_WALL_CORNER, new BasicTile(localTiles[9][12], false));
            biomTiles.put(TileMapper.LAVA_STONE_MIDDLE_RIGHT_WALL_STRAIGHT, new BasicTile(localTiles[10][9], false));


            //Spalte 8 links, 9 rechts, zeile 8-10 ... 10,5(ubergang)
            biomTiles.put(TileMapper.TERRA_FORMING_MID_TOP, new BasicTile(localTiles[4][10], false));
            biomTiles.put(TileMapper.TERRA_FORMING_MID, new BasicTile(localTiles[4][10], false));
            biomTiles.put(TileMapper.TERRA_FORMING_MID_BOT, new BasicTile(localTiles[4][10], false));
            biomTiles.put(TileMapper.TERRA_FORMING_LEFT_TOP, new BasicTile(localTiles[8][8], false));
            biomTiles.put(TileMapper.TERRA_FORMING_LEFT, new BasicTile(localTiles[9][8], false));
            biomTiles.put(TileMapper.TERRA_FORMING_LEFT_BOT, new BasicTile(localTiles[10][8], false));
            biomTiles.put(TileMapper.TERRA_FORMING_RIGHT_TOP, new BasicTile(localTiles[8][9], false));
            biomTiles.put(TileMapper.TERRA_FORMING_RIGHT, new BasicTile(localTiles[9][9], false));
            biomTiles.put(TileMapper.TERRA_FORMING_RIGHT_BOT, new BasicTile(localTiles[10][9], false));

            biomTiles.put(TileMapper.LAVA_DECO_1, new BasicTile(localTiles[2][3], false));
            biomTiles.put(TileMapper.LAVA_DECO_2, new BasicTile(localTiles[1][3], false));
            biomTiles.put(TileMapper.STONE_DECO_0, new BasicTile(localTiles[15][0], false));
            biomTiles.put(TileMapper.STONE_DECO_1, new BasicTile(localTiles[14][10], false));


            biomTiles.put(TileMapper.DEBUG, new BasicTile(localTiles[31][31], false));

            tiles2.put(i, biomTiles);
        }
    }

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
