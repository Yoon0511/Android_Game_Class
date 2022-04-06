package kr.ac.samplegame;

import android.content.res.Resources;

public class Metrics {
    public static float size(int dimenResId){
        Resources res = GameView.view.getResources();
        return res.getDimension(dimenResId);
    }
}
