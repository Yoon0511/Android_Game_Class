package kr.ac.my_towerdefence.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.my_towerdefence.R;
import kr.ac.my_towerdefence.framework.GameObject;
import kr.ac.my_towerdefence.framework.Metrics;
import kr.ac.my_towerdefence.framework.Sprite;

public class Tower extends Sprite {
    private float fireInterval;
    private float elapsedTimeForFire;
    protected static int[] BITMAP_IDS = {
            R.mipmap.tower00,R.mipmap.tower01
    };

    private float tx,ty;
    private float angle;
    private String TAG = Tower.class.getSimpleName();

    public Tower(float x, float y) {
        super(x, y, R.dimen.tower_radious, R.mipmap.tower00);
        fireInterval = Metrics.floatValue(R.dimen.tower_fire_interval);
    }

    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        ArrayList<GameObject> enemies = MainGame.getInstance().objectsAt(MainGame.Layer.enemy);

        if(!enemies.isEmpty()){
            Enemy target = (Enemy) enemies.get(0);
            setTarget(target.getX(),target.getY());
        }

        elapsedTimeForFire += frameTime;
        if (elapsedTimeForFire > fireInterval) {
            if(!enemies.isEmpty()) {
                fire();
                elapsedTimeForFire -= fireInterval;
            }
        }
    }

    public void setTarget(float tx,float ty){
        this.tx = tx;
        this.ty = ty;
        angle = (float) Math.atan2(ty - this.y,tx - this.x);
    }
    private void fire() {
        Bullet bullet = Bullet.get(x, y, 30,tx,ty);
        MainGame.getInstance().add(MainGame.Layer.bullet, bullet);
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate((float) (angle * 180 / Math.PI),x,y);
        canvas.drawBitmap(bitmap,null,dstRect,null);
        canvas.restore();
    }
}
