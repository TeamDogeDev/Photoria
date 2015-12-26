package de.dogedevs.photoria.generators;

import de.dogedevs.photoria.rendering.tiles.TileMapper;

import static de.dogedevs.photoria.rendering.tiles.TileMapper.*;

/**
 * Created by elektropapst on 26.12.2015.
 */
public class MapDecorator extends AbstractMapDecorator {

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

        for (int x = 1; x < chunk.length-1; x++) {
            for (int y = 1; y < chunk[x].length-1; y++) {
                if(y-2 < 0) continue; // q'n'd

                int tm = chunk[x][y + 1];
                int ml = chunk[x - 1][y];
                int mm = chunk[x][y];
                int mr = chunk[x + 1][y];
                int bm = chunk[x][y - 1];

                // LEFT SIDE
                if(mm == LAVA_STONE_BOTTOM_LEFT_0
                && (ml == LAVA_STONE_BOTTOM_LEFT_1 || ml == LAVA_STONE_BOTTOM_MIDDLE_1)) {
                    chunk[x][y] = LAVA_STONE_BOTTOM_LEFT_WALL_0;
                    chunk[x][y-1] = LAVA_STONE_BOTTOM_LEFT_WALL_1;
                    chunk[x][y-2] = LAVA_STONE_BOTTOM_LEFT_WALL_2;
                }else
                if(mm == LAVA_STONE_BOTTOM_LEFT_INNER
                && bm == LAVA_STONE_BOTTOM_LEFT_0) {
                    chunk[x][y-1] = LAVA_STONE_BOTTOM_LEFT_WALL_0;
                    chunk[x][y-2] = LAVA_STONE_BOTTOM_LEFT_WALL_1;
                }else
                if(tm == LAVA_STONE_BOTTOM_LEFT_INNER
                && mm == LAVA_STONE_MIDDLE_LEFT
                && (bm == LAVA_STONE_BOTTOM_LEFT_0 || bm == LAVA_STONE_BOTTOM_LEFT_WALL_0)) {
                    chunk[x][y] = LAVA_STONE_MIDDLE_LEFT_WALL;
                    chunk[x][y-1] = LAVA_STONE_MIDDLE_LEFT_WALL_CORNER;
                }else
                if(tm == LAVA_STONE_BOTTOM_LEFT_INNER
                && mm == LAVA_STONE_MIDDLE_LEFT
                && bm == LAVA_STONE_MIDDLE_LEFT) {
                    chunk[x][y] = LAVA_STONE_MIDDLE_LEFT_WALL;
                    chunk[x][y-1] = LAVA_STONE_MIDDLE_LEFT_WALL_STRAIGHT;
                }else
                // RIGHT SIDE
                if(mm == LAVA_STONE_BOTTOM_RIGHT_0
                &&(mr == LAVA_STONE_BOTTOM_RIGHT_1 || mr == LAVA_STONE_BOTTOM_MIDDLE_1)) {
                    chunk[x][y] = LAVA_STONE_BOTTOM_RIGHT_WALL_0;
                    chunk[x][y-1] = LAVA_STONE_BOTTOM_RIGHT_WALL_1;
                    chunk[x][y-2] = LAVA_STONE_BOTTOM_RIGHT_WALL_2;
                }else
                if(mm == LAVA_STONE_BOTTOM_RIGHT_INNER
                && bm == LAVA_STONE_BOTTOM_RIGHT_0) {
                    chunk[x][y-1] = LAVA_STONE_BOTTOM_RIGHT_WALL_0;
                    chunk[x][y-2] = LAVA_STONE_BOTTOM_RIGHT_WALL_1;
                }else
                if(tm == LAVA_STONE_BOTTOM_RIGHT_INNER
                && mm == LAVA_STONE_MIDDLE_RIGHT
                && (bm == LAVA_STONE_BOTTOM_RIGHT_0 || bm == LAVA_STONE_BOTTOM_RIGHT_WALL_0)) {
                    chunk[x][y] = LAVA_STONE_MIDDLE_RIGHT_WALL;
                    chunk[x][y-1] = LAVA_STONE_MIDDLE_RIGHT_WALL_CORNER;
                }else
                if(tm == LAVA_STONE_BOTTOM_RIGHT_INNER
                && mm == LAVA_STONE_MIDDLE_RIGHT
                && bm == LAVA_STONE_MIDDLE_RIGHT) {
                    chunk[x][y] = LAVA_STONE_MIDDLE_RIGHT_WALL;
                    chunk[x][y-1] = LAVA_STONE_MIDDLE_RIGHT_WALL_STRAIGHT;
                }

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

        int tile = decorate(ground, x, y, upperBaseTile);

        // WUB WUB WUB 3 nach unten
        if(tile == TileMapper.LAVA_STONE_BOTTOM_MIDDLE_0 && y-2 >= 0) {
            chunk[x][y-1] = LAVA_STONE_BOTTOM_MIDDLE_1;
            chunk[x][y-2] = LAVA_STONE_BOTTOM_MIDDLE_2;
        }else if(tile == TileMapper.LAVA_STONE_BOTTOM_LEFT_0 && y-2 >= 0) {
            chunk[x][y-1] = LAVA_STONE_BOTTOM_LEFT_1;
            chunk[x][y-2] = LAVA_STONE_BOTTOM_LEFT_2;
        }else if(tile == TileMapper.LAVA_STONE_BOTTOM_RIGHT_0 && y-2 >= 0) {
            chunk[x][y-1] = LAVA_STONE_BOTTOM_RIGHT_1;
            chunk[x][y-2] = LAVA_STONE_BOTTOM_RIGHT_2;
        }

        return  tile;
    }

    private int calculateBitmask(int[][] array, int x, int y, int baseTile) {
        return (array[x    ][y    ] == baseTile ? 256 : 0)
             | (array[x - 1][y    ] == baseTile ? 128 : 0)
             | (array[x - 1][y - 1] == baseTile ?  64 : 0)
             | (array[x    ][y - 1] == baseTile ?  32 : 0)
             | (array[x + 1][y - 1] == baseTile ?  16 : 0)
             | (array[x + 1][y    ] == baseTile ?   8 : 0)
             | (array[x + 1][y + 1] == baseTile ?   4 : 0)
             | (array[x    ][y + 1] == baseTile ?   2 : 0)
             | (array[x - 1][y + 1] == baseTile ?   1 : 0);
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
        return fluidBitmaskToTileId(calculateBitmask(ground, x, y, baseTile), baseTile);
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
