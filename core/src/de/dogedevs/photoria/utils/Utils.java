package de.dogedevs.photoria.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import de.dogedevs.photoria.model.entity.components.PositionComponent;

/**
 * Created by elektropapst on 04.01.2016.
 */
public class Utils {

    public static double rescale(double val, double minIn, double maxIn, double minOut, double maxOut) {
        return ((maxOut - minOut) * (val - minIn) / (maxIn - minIn)) + minIn;
    }

    public static double euclDist(PositionComponent pos1, PositionComponent pos2) {
        return Math.sqrt(Math.pow(pos1.x - pos2.x, 2) +
                Math.pow(pos1.y - pos2.y, 2));
    }

    private static int lastX;
    private static int lastY;
    private static int currentX;
    private static int currentY;

    @Deprecated
    public static void grabMouse(int areaAroundCenter) {
        currentX = Gdx.input.getX();
        currentY = Gdx.input.getY();
        if(lastX == 0 && lastY == 0) {
            int oldX = Gdx.graphics.getWidth()/2;
            int oldY = Gdx.graphics.getHeight()/2;
        }

        Circle c = new Circle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, areaAroundCenter);
        if(!c.contains(currentX, currentY)) {
            Gdx.input.setCursorPosition(lastX, lastY);
        } else {
            lastX = currentX;
            lastY = currentY;
        }


//        if(Gdx.input.getX() <= (Gdx.graphics.getWidth()/2) - areaAroundCenter) {
//            Gdx.input.setCursorPosition((Gdx.graphics.getWidth() / 2) - areaAroundCenter, Gdx.input.getY());
//        }
//        if(Gdx.input.getX() >= (Gdx.graphics.getWidth()/2) + areaAroundCenter) {
//            Gdx.input.setCursorPosition((Gdx.graphics.getWidth() / 2) + areaAroundCenter, Gdx.input.getY());
//        }
//
//        if(Gdx.input.getY() <= (Gdx.graphics.getHeight() / 2) - areaAroundCenter) {
//            Gdx.input.setCursorPosition(Gdx.input.getX(), (Gdx.graphics.getHeight() / 2) - areaAroundCenter);
//        }
//        if(Gdx.input.getY() >= (Gdx.graphics.getHeight() / 2) + areaAroundCenter) {
//            Gdx.input.setCursorPosition(Gdx.input.getX(), (Gdx.graphics.getHeight() / 2) + areaAroundCenter);
//        }

    }

//    public static Mesh createFullscreenQuad(){
//        float[] verts = new float[20];
//        int i = 0;
//        verts[i++] = -1.f; // x1
//        verts[i++] = -1.f; // y1
//        verts[i++] =  0.f; // u1
//        verts[i++] =  0.f; // v1
//        verts[i++] =  1.f; // x2
//        verts[i++] = -1.f; // y2
//        verts[i++] =  1.f; // u2
//        verts[i++] =  0.f; // v2
//        verts[i++] =  1.f; // x3
//        verts[i++] =  1.f; // y2
//        verts[i++] =  1.f; // u3
//        verts[i++] =  1.f; // v3
//        verts[i++] = -1.f; // x4
//        verts[i++] =  1.f; // y4
//        verts[i++] =  0.f; // u4
//        verts[i++] =  1.f; // v4
//        Mesh tmpMesh = new Mesh(true, 4, 0
//                , new VertexAttribute(VertexAttributes.Usage.Position, 2, "a_position")
//                , new VertexAttribute(VertexAttributes.Usage.TextureCoordinates
//                , 2, "a_texCoord0"));
//        tmpMesh.setVertices(verts);
//        return tmpMesh;
//    }
}
