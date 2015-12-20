package de.dogedevs.photoria.generators;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import de.dogedevs.photoria.MainGame;

import java.util.Random;

/**
 * Created by elektropapst on 20.12.2015.
 */
 @Deprecated
public class VoronoiMapGenerator implements AbstractMapGenerator {

    private static Random random = new Random();
    private static final int NUM_V_PTS = 15;

    @Override
    public int[][] generate(int xPos, int yPos, int size) {
        int[][] area = new int[size * 3][size * 3];
        System.out.println("Create Chunk " + xPos + " " + yPos);
        Array<Vector2> pts = new Array<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int local_x = xPos + i;
                int local_y = yPos + j;
                random = new Random(getSeedFromPos(local_x, local_y));
                // Create Random Points
                for (int k = 0; k < NUM_V_PTS; k++) {
                    int lx = i + 1;
                    int ly = j + 1;

                    Vector2 vec = new Vector2(random.nextInt(size) + (lx * 64), random.nextInt(size) + (ly * 64));
                    pts.add(vec);
//                    System.out.println("\t\t" + vec);
                }
            }
        }
        System.out.println(pts.size);
        random = new Random(getSeedFromPos(xPos, yPos));

//        pts.clear();
        for (Vector2 vec : pts) {
            area[((int) vec.x)][((int) vec.y)] = random.nextInt(2);
        }


//        for (int i = 0; i < 3; i++) {
//            pts.add(new Vector2(i * size, i * size));
//            area[i * size][i * size] = 1; // bottom Left
//        }
//        for (int i = 0; i < 3; i++) {
//            pts.add(new Vector2(((i + 1) * size) - 1, ((i + 1) * size) - 1));
//            area[((i + 1) * size) - 1][((i + 1) * size) - 1] = 2; // top Right
//        }


        // Voronoi magic
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area[i].length; j++) {
                Vector2 tmpVec = new Vector2(i, j);
                Vector2 vecWithMinDist = null;
                float distance = 0;
                for (Vector2 vec : pts) {
                    if (vecWithMinDist == null) {
                        distance = vec.dst2(tmpVec);
                        vecWithMinDist = vec;
                    } else {
                        if (vec.dst2(tmpVec) < distance) {
                            distance = vec.dst2(tmpVec);
                            vecWithMinDist = vec;
                        }
                    }
                }

                area[i][j] = area[((int) vecWithMinDist.x)][((int) vecWithMinDist.y)];
            }
        }

        // Extract inner part
        int[][] mapPart = new int[size][size];
        for (int i = size; i < 2 * size; i++) {
            for (int j = size; j < 2 * size; j++) {
                mapPart[i - size][j - size] = area[i][j];
            }
        }

        return mapPart;
    }

    private long getSeedFromPos(int x, int y) {
        return (31 * (x * y));
    }
}
