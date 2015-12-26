package de.dogedevs.photoria.generators;

import static de.dogedevs.photoria.rendering.tiles.TileMapper.*;

/**
 * Created by elektropapst on 26.12.2015.
 */
public class BitmaskMapDecorator extends AbstractMapDecorator {

    private int tileId = VOID;

    @Override
    public int[][] decorate(int[][] ground) {

        if (chunk == null) {
            chunk = new int[ground.length][ground.length];
        }
        for (int x = 1; x < ground.length - 1; x++) {
            for (int y = 1; y < ground[x].length - 1; y++) {

                tileId = decorate(ground, x, y, WATER);

                if (tileId == VOID) {
                    tileId = decorate(ground, x, y, LAVA);
                }
                if(tileId == VOID) {
                    tileId = decorateHill(ground, x, y, LAVA_STONE, GROUND);
                }

                chunk[x][y] = tileId;
            }
        }

        return chunk;
    }

    // @formatter:off
    private int decorateHill(int[][] ground, int x, int y, int upperBaseTile, int lowerBaseTile) {

        for(int i = -1; i < 1; i++) {
            for(int j = -1; j < 1; j++) {
                if(ground[x+i][y+j] != upperBaseTile &&
                ground[x+i][y+j] != lowerBaseTile) return VOID;
            }
        }

        return decorate(ground, x, y, upperBaseTile);
    }


    private int decorate(int[][] ground, int x, int y, int baseTile) {
        // binMask : 2^x
        // +-+-+-+
        // |0|1|2|
        // +-+-+-+
        // |7|8|3|
        // +-+-+-+
        // |6|5|4|
        // +-+-+-+
        int bitmask = (ground[x    ][y    ] == baseTile ? 256 : 0)
                    | (ground[x - 1][y    ] == baseTile ? 128 : 0)
                    | (ground[x - 1][y - 1] == baseTile ?  64 : 0)
                    | (ground[x    ][y - 1] == baseTile ?  32 : 0)
                    | (ground[x + 1][y - 1] == baseTile ?  16 : 0)
                    | (ground[x + 1][y    ] == baseTile ?   8 : 0)
                    | (ground[x + 1][y + 1] == baseTile ?   4 : 0)
                    | (ground[x    ][y + 1] == baseTile ?   2 : 0)
                    | (ground[x - 1][y + 1] == baseTile ?   1 : 0);
        return fluidBitmaskToTileId(bitmask, baseTile);
    }

    private int fluidBitmaskToTileId(int bitmask, int baseTile) {
        switch(bitmask) {
            case 0b001100000 :
            case 0b001110000 :
            case 0b000100000 : // N
            case 0b000110000 : return baseTile+2; // _TOP_MIDDLE
            case 0b000010000 : return baseTile+1; // _TOP_LEFT
            case 0b000111000 :
            case 0b000111100 :
            case 0b001111000 : return baseTile+9; // _TOP_LEFT_INNER;
            case 0b000011000 :
            case 0b000001100 :
            case 0b000001000 :
            case 0b000011100 : return baseTile+4; // _MIDDLE_LEFT;
            case 0b000000100 : return baseTile+6; // _BOTTOM_LEFT;
            case 0b000011110 :
            case 0b000001111 :
            case 0b000001110 : return baseTile+11; // _BOTTOM_LEFT_INNER;
            case 0b000000111 :
            case 0b000000011 :
            case 0b000000010 : // n
            case 0b000000110 : return baseTile+7; // _BOTTOM_MIDDLE;
            case 0b000000001 : return baseTile+8; // _BOTTOM_RIGHT;
            case 0b010000011 :
            case 0b011000011 :
            case 0b010000111 : return baseTile+12; // _BOTTOM_RIGHT_INNER;
            case 0b011000001 :
            case 0b011000000 :
            case 0b010000000 : // n
            case 0b010000001 : return baseTile+5; // _MIDDLE_RIGHT;
            case 0b001000000 : return baseTile+3; // _TOP_RIGHT;
            case 0b011100000 :
            case 0b011110000 :
            case 0b011100001 : return baseTile+10; // _TOP_RIGHT_INNER;
            default: return VOID;
        }
    }
    // @formatter:on
}
