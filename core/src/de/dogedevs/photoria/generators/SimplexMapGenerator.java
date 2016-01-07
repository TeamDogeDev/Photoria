package de.dogedevs.photoria.generators;

import com.badlogic.gdx.math.MathUtils;
import de.dogedevs.photoria.model.map.ChunkBuffer;
import de.dogedevs.photoria.utils.Utils;

import java.util.Random;

import static de.dogedevs.photoria.rendering.tiles.TileMapper.*;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class SimplexMapGenerator extends AbstractMapGenerator {

    private Random random;
    private OpenSimplexNoise osn;
    private OpenSimplexNoise temperatureOsn;
    private OpenSimplexNoise rainfallOsn;
//    private int[][] chunk;
    private double local_x;
    private double local_y;
    private double local_x_biom;
    private double local_y_biom;
    private double eval;
    private double temperatureNoise;
    private double rainfallNoise;
    protected int realSize;
    // - 0.86
    // 0.86


    public SimplexMapGenerator(long seed) {
        random = new Random(seed);
        osn = new OpenSimplexNoise(random.nextLong());
        temperatureOsn = new OpenSimplexNoise(random.nextLong());
        rainfallOsn = new OpenSimplexNoise(random.nextLong());
    }

    public SimplexMapGenerator() {
        this(31337);
    }

    @Override
    public int[][][] generate(int chunkX, int chunkY, int size, int overlap) {
        if (chunk == null) {
            chunk = new int[2][size+(2*overlap)][size+(2*overlap)];
        }

        realSize = size+(2*overlap);

        for (int row = 0; row < realSize; row++) {
            for (int col = 0; col < realSize; col++) {
                local_x = row + (chunkX * size);
                local_y = col + (chunkY * size);
                local_x_biom = local_x;
                local_y_biom = local_y;

                eval = osn.eval((local_x / size), (local_y / size));
                temperatureNoise = temperatureOsn.eval((local_x_biom / size), (local_y_biom / size));
                rainfallNoise = rainfallOsn.eval((local_x_biom / size), (local_y_biom / size));

                temperatureNoise = Utils.rescale(temperatureNoise, -0.76, 0.76, 0, 100);
                rainfallNoise = Utils.rescale(rainfallNoise, -0.76, 0.76, 0, 100);
                int biom = getBiom(temperatureNoise, rainfallNoise);
                chunk[BIOMLAYER][row][col] = biom;

                if (this.eval < -0.4f) {
                    chunk[TILELAYER][row][col] = ((int) (this.eval / 0.12f));
                    int val = MathUtils.clamp(chunk[TILELAYER][row][col], 1, 2);
                    if(val == 1) {
                        chunk[TILELAYER][row][col] = WATER;
                    } else {
                        chunk[TILELAYER][row][col] = GROUND;
                    }
                } else if (this.eval > 0.2f) {
                    chunk[TILELAYER][row][col] = (int) (this.eval / 0.14f);
                    int val = MathUtils.clamp(chunk[TILELAYER][row][col], 3, 4);
                    if(val == 3) {
                        chunk[TILELAYER][row][col] = LAVA_STONE;
                    } else {
                        chunk[TILELAYER][row][col] = LAVA;
                    }
                } else {
                    chunk[TILELAYER][row][col] = GROUND;
                }
//                chunk[row][col] = (int) (eval*7);
            }
        }
//        MainGame.log("MIN: " + minNum + " MAX: " + maxNum);
        return chunk;
    }

    private int getBiom(double temperature, double rainfall) {
        if(rainfall < 25) {
            if(temperature < 25) {
                return ChunkBuffer.BLUE_BIOM;
            } else if(temperature < 70) {
                return ChunkBuffer.NORMAL_BIOM;
            } else {
                return ChunkBuffer.RED_BIOM;
            }
        } else if(rainfall < 50) {
            if(temperature < 25) {
                return ChunkBuffer.BLUE_BIOM;
            } else if(temperature < 75) {
                return ChunkBuffer.NORMAL_BIOM;
            } else {
                return ChunkBuffer.YELLOW_BIOM;
            }
        } else if(rainfall < 75) {
            if(temperature < 25) {
                return ChunkBuffer.BLUE_BIOM;
            } else if(temperature < 50) {
                return ChunkBuffer.PURPLE_BIOM;
            } else {
                return ChunkBuffer.YELLOW_BIOM;
            }
        } else {
            if(temperature < 50) {
                return ChunkBuffer.PURPLE_BIOM;
            } else {
                return ChunkBuffer.GREEN_BIOM;
            }
        }
//        if(rainfall < 25) {
//            if(temperature < 25) {
//                return ChunkBuffer.TUNDRA;
//            } else if(temperature < 70) {
//                return ChunkBuffer.GRASS_DESERT_BIOM;
//            } else {
//                return ChunkBuffer.DESERT_BIOM;
//            }
//        } else if (rainfall < 50) {
//            if (temperature < 25) {
//                return ChunkBuffer.TUNDRA;
//            } else if (temperature < 50) {
//                return ChunkBuffer.TAIGA_BIOM;
//            } else if (temperature < 75) {
//                return ChunkBuffer.WOODS_BIOM;
//            } else {
//                return ChunkBuffer.SAVANNA_BIOM;
//            }
//        } else if(rainfall < 75) {
//            if (temperature < 25) {
//                return ChunkBuffer.TUNDRA;
//            } else if (temperature < 50) {
//                return ChunkBuffer.TAIGA_BIOM;
//            } else if (temperature < 75) {
//                return ChunkBuffer.FOREST_BIOM;
//            } else {
//                return ChunkBuffer.SEASONAL_FOREST_BIOM;
//            }
//        } else {
//            if (temperature < 25) {
//                return ChunkBuffer.TUNDRA;
//            } else if (temperature < 50) {
//                return ChunkBuffer.TAIGA_BIOM;
//            } else if (temperature < 75) {
//                return ChunkBuffer.SWAMP_BIOM;
//            } else {
//                return ChunkBuffer.RAIN_FOREST_BIOM;
//            }
        }


}
