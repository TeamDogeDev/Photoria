package de.dogedevs.photoria.utils;

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
