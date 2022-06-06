package kr.ac.my_towerdefence.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.ArrayList;

import kr.ac.my_towerdefence.R;
import kr.ac.my_towerdefence.framework.BitmapPool;
import kr.ac.my_towerdefence.framework.BoxCollidable;
import kr.ac.my_towerdefence.framework.GameObject;
import kr.ac.my_towerdefence.framework.Metrics;
import kr.ac.my_towerdefence.framework.Recyclable;
import kr.ac.my_towerdefence.framework.RecycleBin;
import kr.ac.my_towerdefence.framework.Sprite;

public class Bullet extends Sprite implements BoxCollidable, Recyclable {
    private static final String TAG = Bullet.class.getSimpleName();
    protected RectF boundingRect = new RectF();

    private float power;
    private float tx,ty;
    private float dx,dy;
    private static final float speed = Metrics.size(R.dimen.bullet_initial_speed);
    private static final float size = Metrics.size(R.dimen.bullet_radious);
    private float angle;
    private static final float inset = (Metrics.width / 10f) / 3;

    public Bullet(float x, float y,float power,float tx,float ty,int bitmapId) {
        super(x, y, R.dimen.bullet_radious, R.mipmap.bullet);
        this.power = power;
        setTargetAngle(tx,ty);
        bitmap = BitmapPool.get(bitmapId);
    }

    public static Bullet get(float x, float y, float power,float tx,float ty,int bitmapId) {
        Bullet bullet = (Bullet) RecycleBin.get(Bullet.class);
        if (bullet != null) {
            bullet.set(x, y, power,tx,ty,bitmapId);
            return bullet;
        }
        return new Bullet(x, y, power,tx,ty,bitmapId);
    }

    private void set(float x, float y, float power,float tx,float ty,int bitmapId) {
        this.x = x;
        this.y = y;
        this.power = power;
        setTargetAngle(tx,ty);
        bitmap = BitmapPool.get(bitmapId);

    }

    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        y += dy * frameTime;
        x += dx * frameTime;

        setDstRectWithRadius();
        boundingRect.set(dstRect);
    }
    public void setTargetAngle(float tx, float ty) {
        this.tx = tx;
        this.ty = ty;

        angle = (float) Math.atan2(ty - this.y, tx - this.x);
        dx = (float) (speed * Math.cos(angle));
        dy = (float) (speed * Math.sin(angle));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate((float) (angle * 180 / Math.PI),x,y);
       canvas.drawBitmap(bitmap,null,dstRect,null);
        canvas.restore();
    }

    @Override
    public RectF getBoundingRect() {
        return boundingRect;
    }

    @Override
    public void finish() {

    }

    public float getPower() {
        return power;
    }

    public void abilityActivation(Enemy enemy)
    {
        if(bitmap == BitmapPool.get(R.mipmap.icebullet)){
            enemy.slow();
        }
        else if(bitmap == BitmapPool.get(R.mipmap.explosionbullet)){
            explode();
        }
    }

    private void explode() {
        Explosion ex = Explosion.get(getX(), getY(), Map.tileWidth);
        MainGame.getInstance().add(MainGame.Layer.explosion, ex);
        ArrayList<GameObject> enemys = MainGame.getInstance().objectsAt(MainGame.Layer.enemy);
        double explosion_radius = Map.tileWidth * 1.5;
        for (GameObject e: enemys) {
            Enemy enemy = (Enemy) e;
            float dx = x - enemy.getX();
            float dy = y - enemy.getY();
            double dist = Math.sqrt(dx * dx + dy * dy);
            if (dist < explosion_radius) {
                boolean dead = enemy.decreaseLife(power);
                if (dead) {
                    MainGame.getInstance().remove(enemy);
                }
            }
        }
    }
}