package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.game;



import android.graphics.Canvas;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.R;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.Sprite;

public class Enemy extends Sprite {
    private static float size;
    protected float dy;
    public Enemy(float x, float y, float speed) {
//        super(x, y, R.dimen.enemy_radius, R.mipmap.f_01_01);
        super(x,-size/2,size,size,R.mipmap.f_01_01);
        dy = speed;
    }

    public static void setSize(float size) {
        Enemy.size = size;
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        y += dy * frameTime;
        setDstRectWithRadius();
        if (dstRect.top > Metrics.height) {
            MainGame.getInstance().remove(this);
        }
    }
}
