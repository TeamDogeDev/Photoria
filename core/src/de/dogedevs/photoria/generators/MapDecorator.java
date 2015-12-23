package de.dogedevs.photoria.generators;

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
                if(tileId == VOID) {
                    decorateLiquid(TL, TM, TR, ML, MM, MR, BL, BM, BR, WATER, GROUND);
                }
                if(tileId == VOID) {
                    decorateLiquid(TL, TM, TR, ML, MM, MR, BL, BM, BR, LAVA, LAVA_STONE);
                }
                if(tileId == VOID) {
                    decorateHill(TL, TM, TR, ML, MM, MR, BL, BM, BR, GROUND, LAVA_STONE);
                }
                chunk[x][y] = tileId;
                if(tileId == LAVA_STONE_BOTTOM_LEFT_0 || tileId == LAVA_STONE_BOTTOM_MIDDLE_0) {
                    chunk[x][y - 1] = tileId+1;
                    chunk[x][y - 2] = tileId+2;
                }
            }
        }

        return chunk;
    }

    private void decorateHill(int TL, int TM, int TR, int ML, int MM, int MR, int BL, int BM, int BR, int lowerTile, int upperTile) {
        if(MM == lowerTile && MR == lowerTile
        && BM == lowerTile && BR == upperTile)
        {
            tileId = LAVA_STONE_TOP_LEFT;
        } else
        if(ML == lowerTile && MM == lowerTile && MR == lowerTile
        && BM == upperTile) {
            tileId = LAVA_STONE_TOP_MIDDLE;
        } else
        if(TM == lowerTile
        && ML == lowerTile && MM == lowerTile && MR == upperTile
        && BM == upperTile && BR == upperTile) {
            tileId = LAVA_STONE_BOTTOM_RIGHT_INNER;
        }else
        if(TM == lowerTile
        && MM == lowerTile && MR == upperTile
        && BM == lowerTile && BR == upperTile) {
            tileId = LAVA_STONE_LEFT_MIDDLE;
        }else
        if(TM == lowerTile
        && MM == lowerTile && MR == upperTile
        && BM == lowerTile && BR == lowerTile) {
            tileId = LAVA_STONE_BOTTOM_LEFT_0;
        }else
        if(TM == upperTile && TR == upperTile
        && MM == upperTile && MR == upperTile
        && BL == lowerTile && BM == lowerTile && BR == upperTile) {
            tileId = LAVA_STONE_TOP_RIGHT_INNER;
        }else
        if(TM == upperTile
        && MM == upperTile
        && BL == lowerTile && BM == lowerTile && BR == lowerTile) {
            tileId = LAVA_STONE_BOTTOM_MIDDLE_0;
        }else
        if(TM == upperTile && TR == upperTile
        && ML == lowerTile && MR == upperTile
        && BM == lowerTile && BR == lowerTile) {
            tileId = LAVA_STONE_TOP_RIGHT_INNER_BOTTOM_LEFT_0;
        }else
        if(TM == upperTile && TR == upperTile
        && ML == lowerTile && MR == upperTile
        && BM == lowerTile && BR == upperTile) {
            tileId = LAVA_STONE_TOP_RIGHT_INNER_MIDDLE_LEFT;
        }

    }

    private void decorateLiquid(int TL, int TM, int TR, int ML, int MM, int MR, int BL, int BM, int BR, int processTile, int midTile) {
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
}
