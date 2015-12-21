package de.dogedevs.photoria.generators;

import de.dogedevs.photoria.rendering.tiles.TileMapper;

import java.util.Random;

/**
 * Created by elektropapst on 21.12.2015.
 */
public class WaterDecorator extends AbstractMapDecorator {

    private Random random = new Random(31337);

    @Override
    public int[][] decorate(int[][] ground) {
        if(chunk == null) {
            chunk = new int[ground.length][ground.length];
        }
        for (int x = 1; x < ground.length-1; x++) {
            for (int y = 1; y < ground[x].length-1; y++) {

                int TL = ground[x-1][y+1];
                int TM = ground[x  ][y+1];
                int TR = ground[x+1][y+1];

                int ML = ground[x-1][y];
                int MM = ground[x][y];
                int MR = ground[x+1][y];

                int BL = ground[x-1][y-1];
                int BM = ground[x  ][y-1];
                int BR = ground[x+1][y-1];

                if (MM == TileMapper.GROUND && MR == TileMapper.WATER) {
                    chunk[x][y] = TileMapper.WATER_LEFT;

                } else {
                    chunk[x][y] = TileMapper.VOID;
                }
//                chunk[x][y] = random.nextInt(100) > 90 ? 6 : -1000;
            }
        }

        return chunk;
    }

}
