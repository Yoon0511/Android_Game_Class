package kr.ac.my_towerdefence.game;


import android.util.Log;

import kr.ac.my_towerdefence.R;
import kr.ac.my_towerdefence.framework.AnimSprite;
import kr.ac.my_towerdefence.framework.Recyclable;
import kr.ac.my_towerdefence.framework.RecycleBin;

public class Explosion extends AnimSprite implements Recyclable {
    private String TAG = Explosion.class.getSimpleName();

    public static Explosion get(float x, float y, float radius) {
        Explosion ex = (Explosion) RecycleBin.get(Explosion.class);
        if (ex == null) {
            ex = new Explosion();
        }
        ex.init(x, y, radius);
        return ex;
    }
    private Explosion() {
        super(0, 0, 0, 0, R.mipmap.explosion, 20, 0);
    }
    private void init(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        setDstRectWithRadius();
        createdOn = System.currentTimeMillis();
    }

    public void update() {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        if (time > 19.0f/20.0f) {
            MainGame.getInstance().remove(this);
        }
    }

    @Override
    public void finish() { }
}
