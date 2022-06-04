package kr.ac.my_towerdefence.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    private int level,power;
    private float range = 5 * Map.tileWidth * level;
    private float tx,ty;
    private float angle;
    private String TAG = Tower.class.getSimpleName();
    private Paint iceTowerPaint = new Paint();

    public Tower(float x, float y) {
        super(x, y, R.dimen.tower_radious, R.mipmap.tower00);
        this.level = 1;
        this.range = 3 * Map.tileWidth * level;
        this.power = 5;
        fireInterval = Metrics.floatValue(R.dimen.tower_fire_interval);
        iceTowerPaint.setColor(Color.BLUE);
    }

    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        Enemy enemy = MainGame.getInstance().findNearestEnemy(this);

        if(enemy == null) return;

        float dx = enemy.getX() - x;
        float dy = enemy.getY() - y;
        double dist = Math.sqrt(dx * dx + dy * dy);
        if (dist > 1.2 * range) {
            return;
        }

        if (dist > range) {
            return;
        }
        setTarget(enemy.getX(),enemy.getY());
        elapsedTimeForFire += frameTime;
        if (elapsedTimeForFire > fireInterval) {
            fire();
            elapsedTimeForFire -= fireInterval;
        }
    }

    public void setTarget(float tx,float ty){
        this.tx = tx;
        this.ty = ty;
        angle = (float) Math.atan2(ty - this.y,tx - this.x);
    }
    private void fire() {
        Bullet bullet = Bullet.get(x, y, power,tx,ty);
        MainGame.getInstance().add(MainGame.Layer.bullet, bullet);
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate((float) (angle * 180 / Math.PI),x,y);
        canvas.drawBitmap(bitmap,null,dstRect,null);
        canvas.restore();
    }
}
