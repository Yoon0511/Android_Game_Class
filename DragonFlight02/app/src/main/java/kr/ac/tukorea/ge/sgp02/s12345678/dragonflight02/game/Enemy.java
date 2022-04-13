package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.game;



import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.R;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.BoxCollidable;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.Sprite;

public class Enemy extends Sprite implements BoxCollidable {
    private static float size;
    private static float inset;
    protected float dy;
    protected RectF boundingRect = new RectF(dstRect);

    public Enemy(float x, float y, float speed) {
//        super(x, y, R.dimen.enemy_radius, R.mipmap.f_01_01);
        super(x,-size/2,size,size,R.mipmap.f_01_01);
        dy = speed;
    }

    public static void setSize(float size) {
        Enemy.size = size;
        Enemy.inset = size / 16;
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        y += dy * frameTime;
        setDstRectWithRadius();
        boundingRect.set(dstRect);
        boundingRect.inset(inset,inset);
        if (dstRect.top > Metrics.height) {
            MainGame.getInstance().remove(this);
        }
    }

    @Override
    public RectF getBoundingBox() {
        return boundingRect;
    }
}
