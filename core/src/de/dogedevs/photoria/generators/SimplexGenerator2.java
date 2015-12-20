package de.dogedevs.photoria.generators;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Furuha on 20.12.2015.
 */
public class SimplexGenerator2 implements AbstractMapGenerator {
    @Override
    public int[][] generate(int xPos, int yPos, int size) {
        SimplexNoise_octave gen = new SimplexNoise_octave(1337);
        int[][] out = new int[size][size];
        float[][] simplexnoise = new float[size][size];
        float frequency = 1f / (float) 64;

        for(int x = 0; x < 64; x++){
            for(int y = 0; y < 64; y++){
                simplexnoise[x][y] = (float) gen.noise(xPos+x * frequency, yPos+y * frequency);
//                simplexnoise[x][y] = (simplexnoise[x][y] + 1) / 2;   //generate values between 0 and 1
                out[x][y] = Math.round(simplexnoise[x][y]);
            }
        }
        return out;
    }


//    public float octavedNoise(int octaves, float roughness, float scale) {
//        float noiseSum = 0;
//        float layerFrequency = scale;
//        float layerWeight = 1;
//        float weightSum = 0;
//
//        for (int octave = 0; octave < octaves; octave++) {
//            noiseSum += gen.noise(x * layerFrequency, y * layerFrequency) * layerWeight;
//            layerFrequency *= 2;
//            weightSum += layerWeight;
//            layerWeight *= roughness;
//        }
//        return noiseSum / weightSum;
//    }

}
