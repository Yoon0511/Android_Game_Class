package kr.ac.my_towerdefence.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.my_towerdefence.R;
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
    private static final float speed = Metrics.size(R.dimen.bullet_initial_speed);;
    private static final float size = Metrics.size(R.dimen.bullet_radious);;
    private float angle;
    private static final float inset = (Metrics.width / 10f) / 3;

    public Bullet(float x, float y,float power,float tx,float ty) {
        super(x, y, R.dimen.bullet_radious, R.mipmap.bullet);
        this.power = power;
        setTartPosistion(tx,ty);
    }

    //    private static ArrayList<Bullet> recycleBin = new ArrayList<>();
    public static Bullet get(float x, float y, float power,float tx,float ty) {
        Bullet bullet = (Bullet) RecycleBin.get(Bullet.class);
        if (bullet != null) {
//            Bullet bullet = recycleBin.remove(0);
            bullet.set(x, y, power,tx,ty);
            return bullet;
        }
        return new Bullet(x, y, power,tx,ty);
    }

    private void set(float x, float y, float power,float tx,float ty) {
        this.x = x;
        this.y = y;
        this.power = power;
        setTartPosistion(tx,ty);
    }

    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        y += dy * frameTime;
        x += dx * frameTime;

        setDstRectWithRadius();

        boundingRect.set(dstRect);
        boundingRect.inset(inset, inset);
//        if (y < 0) {
//            MainGame.getInstance().remove(this);
//            //recycleBin.add(this);
//        }

    }
    public void setTartPosistion(float tx,float ty) {
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
}
