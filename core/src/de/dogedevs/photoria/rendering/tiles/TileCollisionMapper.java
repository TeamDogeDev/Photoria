package de.dogedevs.photoria.rendering.tiles;

/**
 * Created by elektropapst on 21.12.2015.
 */
public class TileCollisionMapper {

    public static final int[] normalBorderCollision = new int[]{TileCollisionMapper.WATER_BORDER, TileCollisionMapper.LAVA_BORDER, TileCollisionMapper.LAVA_STONE_BORDER};

    public static final int VOID = 0;

    public static final int ENTITY = 0;

    public static final int GROUND = 2;

    public static final int WATER = 16;
    public static final int WATER_BORDER = 17;

    public static final int LAVA = 32;
    public static final int LAVA_BORDER = 33;

    public static final int LAVA_STONE = 64;
    public static final int LAVA_STONE_BORDER = 65;

}
