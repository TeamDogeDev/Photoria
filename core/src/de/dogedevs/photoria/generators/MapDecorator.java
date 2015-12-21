package de.dogedevs.photoria.generators;

import com.badlogic.gdx.utils.GdxRuntimeException;
import de.dogedevs.photoria.rendering.tiles.Tile;
import de.dogedevs.photoria.rendering.tiles.TileMapper;

import java.util.Arrays;
import java.util.Random;

import static de.dogedevs.photoria.rendering.tiles.TileMapper.*;

/**
 * Created by elektropapst on 21.12.2015.
 */
public class MapDecorator extends AbstractMapDecorator {

    private Random random = new Random(31337);
    private int tileId = VOID;

    @Override
    public int[][] decorate(int[][] ground) {
        if (chunk == null) {
            chunk = new int[ground.length][ground.length];
        }
        for (int x = 1; x < ground.length - 1; x++) {
            for (int y = 1; y < ground[x].length - 1; y++) {
                tileId = VOID;

                int TL = ground[x - 1][y + 1];
                int TM = ground[x][y + 1];
                int TR = ground[x + 1][y + 1];

                int ML = ground[x - 1][y];
                int MM = ground[x][y];
                int MR = ground[x + 1][y];

                int BL = ground[x - 1][y - 1];
                int BM = ground[x][y - 1];
                int BR = ground[x + 1][y - 1];

//                checkWater(TL, TM, TR, ML, MM, MR, BL, BM, BR);
                check(TL, TM, TR, ML, MM, MR, BL, BM, BR, WATER, GROUND);
                check(TL, TM, TR, ML, MM, MR, BL, BM, BR, LAVA, LAVA_STONE);

                chunk[x][y] = tileId;
            }
        }

        return chunk;
    }

    private void check(int TL, int TM, int TR, int ML, int MM, int MR, int BL, int BM, int BR, int processTile, int midTile) {
        if (MM == midTile) {

            if (TL == midTile && ML == midTile && BL == midTile
                    && TM == midTile && BM == midTile
                    && MR == processTile) {
                tileId = processTile+4;
            } else
            if (TL == midTile && ML == midTile && BL == midTile
                    && TM == midTile && BM == midTile
                    && TR == midTile && MR == midTile && BR == processTile) {
                tileId = processTile+1;
            } else
            if (TL == midTile && ML == midTile
                    && TM == midTile && BM == processTile
                    && MR == processTile && BR == processTile) {
                tileId = processTile+9;
            } else
            if (TL == midTile && TM == midTile && TR == midTile
                    && ML == midTile && MR == midTile
                    && BM == processTile) {
                tileId = processTile+2;
            } else
            if (TL == midTile && ML == midTile && BL == processTile
                    && TM == midTile && BM == midTile
                    && TR == midTile && MR == midTile && BR == midTile) {
                tileId = processTile+3;
            } else
            if (ML == processTile && BL == processTile
                    && TM == midTile && BM == processTile
                    && TR == midTile && MR == midTile) {
                tileId = processTile+10;
            } else
            if (ML == processTile
                    && TM == midTile && BM == midTile
                    && TR == midTile && MR == midTile && BR == midTile) {
                tileId = processTile+5;
            } else
            if (TL == processTile && ML == midTile && BL == midTile
                    && TM == midTile && BM == midTile
                    && TR == midTile && MR == midTile && BR == midTile) {
                tileId = processTile+8;
            } else
            if (TL == processTile && ML == processTile
                    && TM == processTile && BM == midTile
                    && MR == midTile && BR == midTile) {
                tileId = processTile+12;
            } else
            if (ML == midTile && BL == midTile
                    && TM == processTile && BM == midTile
                    && MR == midTile && BR == midTile) {
                tileId = processTile+7;
            } else
            if (TL == midTile && ML == midTile && BL == midTile
                    && TM == midTile && BM == midTile
                    && TR == processTile && MR == midTile && BR == midTile) {
                tileId = processTile+6;
            } else
            if (ML == midTile && BL == midTile
                    && TM == processTile && BM == midTile
                    && TR == processTile && MR == processTile) {
                tileId = processTile+11;
            }
        }
    }

//    private void checkWater(int TL, int TM, int TR, int ML, int MM, int MR, int BL, int BM, int BR) {
//        if (MM == GROUND) {
//            // Water
//            // LEFT
//            if (TL == GROUND && ML == GROUND && BL == GROUND
//                    && TM == GROUND && BM == GROUND
//                    && MR == WATER) {
//                tileId = WATER_MIDDLE_LEFT;
//            }
//            if (TL == GROUND && ML == GROUND && BL == GROUND
//                    && TM == GROUND && BM == GROUND
//                    && TR == GROUND && MR == GROUND && BR == WATER) {
//                tileId = WATER_TOP_LEFT;
//            }
//
//            if (TL == GROUND && ML == GROUND
//                    && TM == GROUND && BM == WATER
//                    && MR == WATER && BR == WATER) {
//                tileId = WATER_TOP_LEFT_INNER;
//            }
//
//            if (TL == GROUND && TM == GROUND && TR == GROUND
//                    && ML == GROUND && MR == GROUND
//                    && BM == WATER) {
//                tileId = WATER_TOP_MIDDLE;
//            }
//
//            if (TL == GROUND && ML == GROUND && BL == GROUND
//                    && TM == GROUND && BM == GROUND
//                    && MR == WATER && BR == WATER) {
//                tileId = WATER_MIDDLE_LEFT;
//            }
//
//            if (TL == GROUND && ML == GROUND && BL == WATER
//                    && TM == GROUND && BM == GROUND
//                    && TR == GROUND && MR == GROUND && BR == GROUND) {
//                tileId = WATER_TOP_RIGHT;
//            }
//
//            if (ML == WATER && BL == WATER
//                    && TM == GROUND && BM == WATER
//                    && TR == GROUND && MR == GROUND) {
//                tileId = WATER_TOP_RIGHT_INNER;
//            }
//
//            if (ML == WATER
//                    && TM == GROUND && BM == GROUND
//                    && TR == GROUND && MR == GROUND && BR == GROUND) {
//                tileId = WATER_MIDDLE_RIGHT;
//            }
//
//            if (TL == WATER && ML == GROUND && BL == GROUND
//                    && TM == GROUND && BM == GROUND
//                    && TR == GROUND && MR == GROUND && BR == GROUND) {
//                tileId = WATER_BOTTOM_RIGHT;
//            }
//
//            if (TL == WATER && ML == WATER
//                    && TM == WATER && BM == GROUND
//                    && MR == GROUND && BR == GROUND) {
//                tileId = WATER_BOTTOM_RIGHT_INNER;
//            }
//
//            if (ML == GROUND && BL == GROUND
//                    && TM == WATER && BM == GROUND
//                    && MR == GROUND && BR == GROUND) {
//                tileId = WATER_BOTTOM_MIDDLE;
//            }
//
//            if (TL == GROUND && ML == GROUND && BL == GROUND
//                    && TM == GROUND && BM == GROUND
//                    && TR == WATER && MR == GROUND && BR == GROUND) {
//                tileId = WATER_BOTTOM_LEFT;
//            }
//
//            if (ML == GROUND && BL == GROUND
//                    && TM == WATER && BM == GROUND
//                    && TR == WATER && MR == WATER) {
//                tileId = WATER_BOTTOM_LEFT_INNER;
//            }
//        }
//    }
}
