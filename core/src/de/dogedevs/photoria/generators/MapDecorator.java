package de.dogedevs.photoria.generators;

import com.badlogic.gdx.utils.GdxRuntimeException;
import de.dogedevs.photoria.rendering.tiles.Tile;
import de.dogedevs.photoria.rendering.tiles.TileMapper;

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

                if (MM == GROUND) {
                    // Water

                    // LEFT
                    if (TL == GROUND && ML == GROUND && BL == GROUND
                            && TM == GROUND && BM == GROUND
                            && MR == WATER) {
                        tileId = WATER_MIDDLE_LEFT;
                    }

                    if (TL == GROUND && ML == GROUND && BL == GROUND
                            && TM == GROUND && BM == GROUND
                            && TR == GROUND && MR == GROUND && BR == WATER) {
                        tileId = WATER_TOP_LEFT;
                    }

                    if (TL == GROUND && ML == GROUND
                            && TM == GROUND && BM == WATER
                            && MR == WATER && BR == WATER) {
                        tileId = WATER_TOP_LEFT_INNER;
                    }

                    if (TL == GROUND && TM == GROUND && TR == GROUND
                            && ML == GROUND && MR == GROUND
                            && BM == WATER) {
                        tileId = WATER_TOP_MIDDLE;
                    }

                    if (TL == GROUND && ML == GROUND && BL == GROUND
                            && TM == GROUND && BM == GROUND
                            && MR == WATER && BR == WATER) {
                        tileId = WATER_MIDDLE_LEFT;
                    }

                    if (TL == GROUND && ML == GROUND && BL == WATER
                            && TM == GROUND && BM == GROUND
                            && TR == GROUND && MR == GROUND && BR == GROUND) {
                        tileId = WATER_TOP_RIGHT;
                    }

                    if (ML == WATER && BL == WATER
                            && TM == GROUND && BM == WATER
                            && TR == GROUND && MR == GROUND) {
                        tileId = WATER_TOP_RIGHT_INNER;
                    }

                    if (ML == WATER
                            && TM == GROUND && BM == GROUND
                            && TR == GROUND && MR == GROUND && BR == GROUND) {
                        tileId = WATER_MIDDLE_RIGHT;
                    }

                    if (TL == WATER && ML == GROUND && BL == GROUND
                            && TM == GROUND && BM == GROUND
                            && TR == GROUND && MR == GROUND && BR == GROUND) {
                        tileId = WATER_BOTTOM_RIGHT;
                    }

                    if (TL == WATER && ML == WATER
                            && TM == WATER && BM == GROUND
                            && MR == GROUND && BR == GROUND) {
                        tileId = WATER_BOTTOM_RIGHT_INNER;
                    }

                    if(ML == GROUND && BL == GROUND
                    && TM == WATER && BM == GROUND
                    && MR == GROUND && BR == GROUND) {
                        tileId = WATER_BOTTOM_MIDDLE;
                    }

                    if(TL == GROUND && ML == GROUND && BL == GROUND
                    && TM == GROUND && BM == GROUND
                    && TR == WATER && MR == GROUND && BR == GROUND) {
                        tileId = WATER_BOTTOM_LEFT;
                    }

                    if(ML == GROUND && BL == GROUND
                    && TM == WATER && BM == GROUND
                    && TR == WATER && MR == WATER){
                        tileId = WATER_BOTTOM_LEFT_INNER;
                    }
                }
//                if(MM == GROUND) {
//                    if(ML == GROUND && MR == WATER && TR == WATER) {
//                        tileId = WATER_MIDDLE_LEFT;
//                    }
//                }

                chunk[x][y] = tileId;
//                chunk[x][y] = random.nextInt(100) > 90 ? 6 : -1000;
            }
        }

        return chunk;
    }


}
