package de.dogedevs.photoria.model.map;

/**
 * Created by Furuha on 23.12.2015.
 */
public class OffsetHolder {


    public static float offsetX = 0;
    public static float offsetY = 0;

    public static void addRenderOffset(float x, float y){
        offsetX += x;
        offsetY += y;
    }


}
