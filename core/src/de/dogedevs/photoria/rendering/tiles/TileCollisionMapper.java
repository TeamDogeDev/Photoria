package de.dogedevs.photoria.rendering.tiles;

/**
 * Created by elektropapst on 21.12.2015.
 */
public class TileCollisionMapper {

    public static final int[] normalBorderCollision = new int[]{TileCollisionMapper.ENTITY, TileCollisionMapper.FLUID_BORDER, TileCollisionMapper.HIGH_GROUND_FLUID_BORDER, TileCollisionMapper.HIGH_GROUND_BORDER};

    public static final int VOID = 0;

    public static final int ENTITY = 1;

    public static final int GROUND = 2;

    public static final int BRIDGE = 3;

    public static final int FLUID = 16;
    public static final int FLUID_BORDER = 17;

    public static final int HIGH_GROUND_FLUID = 32;
    public static final int HIGH_GROUND_FLUID_BORDER = 33;

    public static final int HIGH_GROUND = 64;
    public static final int HIGH_GROUND_BORDER = 65;

}
