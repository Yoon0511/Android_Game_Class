package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02;

import android.content.res.Resources;
import android.util.TypedValue;

import java.lang.reflect.Type;

public class Metrics {
    public static int width;
    public static int height;

    public static float size(int dimenResId) {
        Resources res = GameView.view.getResources();
        return res.getDimension(dimenResId);
    }

    public static float floatValue(int dimenResId) {
        Resources res = GameView.view.getResources();
        TypedValue outValue = new TypedValue();
        res.getValue(dimenResId,outValue,true);
        float value = outValue.getFloat();
        return value;
    }
}
