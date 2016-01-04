package de.dogedevs.photoria.utils;

/**
 * Created by elektropapst on 04.01.2016.
 */
public class Utils {

    public static double rescale(double val, double minIn, double maxIn, double minOut, double maxOut) {
        return ((maxOut - minOut) * (val - minIn) / (maxIn - minIn)) + minIn;
    }
}
