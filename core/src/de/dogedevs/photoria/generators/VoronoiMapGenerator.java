package de.dogedevs.photoria.generators;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Created by elektropapst on 20.12.2015.
 */
public class VoronoiMapGenerator implements AbstractMapGenerator {

    private static final Random random = new Random();

    @Override
    public int[][] generate(int xPos, int yPos, int size) {
        int[][] mapPart = new int[size<<1][size<<1];
        int numRandomPoints = 15;
        Array<Vector2> randomPoints = new Array<>();
        for (int i = 0; i < numRandomPoints; i++) {
            randomPoints.add(new Vector2(random.nextInt(size<<1), random.nextInt(size<<1)));
        }

        for (Vector2 vec : randomPoints) {
            mapPart[((int) vec.x)][((int) vec.y)] = random.nextInt(2);
        }

        for (int row = 0; row < mapPart.length; row++) {
            for (int col = 0; col < mapPart[row].length; col++) {
                Vector2 tmpVec = new Vector2(row, col);
                Vector2 vecWithMinDistance = null;
                float distance = 0;
                for (Vector2 vec : randomPoints) {
                    if(vecWithMinDistance == null) {
                        distance = vec.dst2(tmpVec);
                        vecWithMinDistance = vec;
                    } else {
                        if(vec.dst2(tmpVec) < distance) {
                            distance = vec.dst2(tmpVec);
                            vecWithMinDistance = vec;
                        }
                    }
                }
                mapPart[row][col] = mapPart[((int) vecWithMinDistance.x)][((int) vecWithMinDistance.y)];
            }
        }

        return mapPart;
    }
}
