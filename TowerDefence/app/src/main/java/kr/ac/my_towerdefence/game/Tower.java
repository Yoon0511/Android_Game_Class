package kr.ac.my_towerdefence.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.my_towerdefence.R;
import kr.ac.my_towerdefence.framework.BitmapPool;
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

    public Tower(float x, float y, Bitmap createBitmap) {
        super(x, y, R.dimen.tower_radious, R.mipmap.tower00);
        this.level = 1;
        this.range = 3 * Map.tileWidth * level;
        this.power = 2;
        if(bitmap != BitmapPool.get(R.mipmap.tower00))
        {
            this.power = 1;
        }
        bitmap = createBitmap;
        fireInterval = Metrics.floatValue(R.dimen.tower_fire_interval);
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
        if(bitmap == BitmapPool.get(R.mipmap.tower00)) //기본 타워
        {
            Bullet bullet = Bullet.get(x, y, power,tx,ty,R.mipmap.bullet);
            MainGame.getInstance().add(MainGame.Layer.bullet, bullet);
        }
       else if(bitmap == BitmapPool.get(R.mipmap.icetower)){ //슬로우 공격 타워
            Bullet bullet = Bullet.get(x, y, power,tx,ty,R.mipmap.icebullet);
            MainGame.getInstance().add(MainGame.Layer.bullet, bullet);
        }
        else if(bitmap == BitmapPool.get(R.mipmap.explosiontower)){ //광역공격타워
            Bullet bullet = Bullet.get(x, y, power,tx,ty,R.mipmap.explosionbullet);
            MainGame.getInstance().add(MainGame.Layer.bullet, bullet);
        }
    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate((float) (angle * 180 / Math.PI),x,y);
        canvas.drawBitmap(bitmap,null,dstRect,null);
        canvas.restore();
    }

    public void upgrade(){
        this.level += 1;
        this.power *= 1.2;
        this.fireInterval *= 0.9;
    }

    public void remove(){
        MainGame.getInstance().remove(this);
    }
}
