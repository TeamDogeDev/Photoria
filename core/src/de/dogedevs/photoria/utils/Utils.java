package de.dogedevs.photoria.utils;

import com.badlogic.gdx.Gdx;
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

    public static void lockMouseToScreen() {
        currentX = Gdx.input.getX();
        currentY = Gdx.input.getY();

        if(currentX <= 0) {
            Gdx.input.setCursorPosition(0, currentY);
        } else if(currentX >= Gdx.graphics.getWidth()) {
            Gdx.input.setCursorPosition(Gdx.graphics.getWidth(), currentY);
        }

        if(currentY <= 0) {
            Gdx.input.setCursorPosition(currentX, 0);
        } else if(currentY >= Gdx.graphics.getHeight()) {
            Gdx.input.setCursorPosition(currentX, Gdx.graphics.getHeight());
        }

    }
}
