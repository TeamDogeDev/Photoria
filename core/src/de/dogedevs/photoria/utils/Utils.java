package de.dogedevs.photoria.utils;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;

/**
 * Created by elektropapst on 04.01.2016.
 */
public class Utils {

    public static double rescale(double val, double minIn, double maxIn, double minOut, double maxOut) {
        return ((maxOut - minOut) * (val - minIn) / (maxIn - minIn)) + minIn;
    }

    private static final int X1 = 0;
    private static final int Y1 = 1;
    private static final int U1 = 2;
    private static final int V1 = 3;
    private static final int X2 = 4;
    private static final int Y2 = 5;
    private static final int U2 = 6;
    private static final int V2 = 7;
    private static final int X3 = 8;
    private static final int Y3 = 9;
    private static final int U3 = 10;
    private static final int V3 = 11;
    private static final int X4 = 12;
    private static final int Y4 = 13;
    private static final int U4 = 14;
    private static final int V4 = 15;

    public static Mesh createFullscreenQuad () {
        float[] verts = new float[16];
        // vertex coord
        verts[X1] = -1;
        verts[Y1] = -1;

        verts[X2] = 1;
        verts[Y2] = -1;

        verts[X3] = 1;
        verts[Y3] = 1;

        verts[X4] = -1;
        verts[Y4] = 1;

        // tex coords
        verts[U1] = 0f;
        verts[V1] = 0f;

        verts[U2] = 1f;
        verts[V2] = 0f;

        verts[U3] = 1f;
        verts[V3] = 1f;

        verts[U4] = 0f;
        verts[V4] = 1f;

        Mesh tmpMesh = new Mesh(
            Mesh.VertexDataType.VertexArray, true, 4, 0, new VertexAttribute(VertexAttributes.Usage.Position, 2, "a_position"),
            new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "a_texCoord0"),
            new VertexAttribute(VertexAttributes.Usage.ColorPacked, 3, "a_color"));

        tmpMesh.setVertices(verts);
        return tmpMesh;
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
