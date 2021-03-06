package kr.ac.my_towerdefence.game;

import static java.lang.Math.log;
import static java.lang.Math.sqrt;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

import kr.ac.my_towerdefence.R;
import kr.ac.my_towerdefence.framework.AnimSprite;
import kr.ac.my_towerdefence.framework.BitmapPool;
import kr.ac.my_towerdefence.framework.BoxCollidable;
import kr.ac.my_towerdefence.framework.Metrics;
import kr.ac.my_towerdefence.framework.Recyclable;
import kr.ac.my_towerdefence.framework.RecycleBin;

public class Enemy extends AnimSprite implements BoxCollidable, Recyclable {
    private static final String TAG = Enemy.class.getSimpleName();
    private int level;
    private float life, maxLife;
    protected float speed;
    protected Gauge gauge;
    protected RectF boundingRect = new RectF();

    private float dx,dy;
    private float angle;
    private static PathMeasure pathMeasure;
    private float dist;
    private static Random random = new Random();

    public static void setPath(Path path){
        Enemy.pathMeasure = new PathMeasure(path,false);
    }
    protected static int[] BITMAP_IDS = {
           R.mipmap.enemy00,
    };
    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = BITMAP_IDS.length;

    public static Enemy get(int level,float x,float y, float speed) {
        Enemy enemy = (Enemy) RecycleBin.get(Enemy.class);
        if (enemy != null) {
            enemy.set(level,x,y, speed);
            return enemy;
        }
        return new Enemy(level,x,y,speed);
    }

    private void set(int level,float x,float y, float speed) {
        bitmap = BitmapPool.get(R.mipmap.enemy00);
        dist = 0;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.level = level;
        life = maxLife = level * 10;
        setDstRectWithRadius();
        boundingRect.set(dstRect);
        boundingRect.inset(inset, inset);
        gauge.setValue(1.0f);
    }

    private Enemy(int level,float x,float y, float speed) {
//        super(x, 0, R.dimen.enemy_radius, R.mipmap.f_01_01);
        super(x, y, size, size, R.mipmap.enemy00, 6, 0);
        dist = 0;
        this.level = level;
        this.speed = speed;
        life = maxLife = level * 30;
        setDstRectWithRadius();
        boundingRect.set(dstRect);
        boundingRect.inset(inset, inset);
        gauge = new Gauge(
                Metrics.size(R.dimen.enemy_gauge_width_fg), R.color.enemy_gauge_fg,
                Metrics.size(R.dimen.enemy_gauge_width_bg), R.color.enemy_gauge_bg,
                size * 0.5f
        );
        gauge.setValue(1.0f);
    }

    private static float size, inset;
    public static void setSize(float size) {
        Enemy.size = size;
        Enemy.inset = size / 4;
    }

    @Override
    public void update() {
//        super.update();
        move();
    }

    private float[] pos = new float[2];
    private float[] tan = new float[2];

    void move(){
        float frameTime = MainGame.getInstance().frameTime;
        dist += speed * frameTime;
        if (dist > pathMeasure.getLength()) {
            MainGame.getInstance().remove(this);
            return;
        }

        dx = speed * frameTime;
        dy = speed * frameTime;
        pathMeasure.getPosTan(dist, pos, tan);
        x = pos[0];
        y = pos[1];
        angle = (float)(Math.atan2(tan[1], tan[0]) * 180 / Math.PI) ;

        setDstRectWithRadius();
        boundingRect.set(dstRect);
        boundingRect.inset(inset*1.2f, inset);

    }
    void nextRoad(){

    }
    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle, x, y);
        super.draw(canvas);
        canvas.restore();

        gauge.draw(canvas, x, y - size / 3);
    }

    @Override
    public RectF getBoundingRect() {
        return boundingRect;
    }

    @Override
    public void finish() {

    }

    public int getScore() {
        return level * 50;
    }

    public boolean decreaseLife(float power) {
        life -= power;
        if (life <= 0) return true;
        gauge.setValue(life / maxLife);
        return false;
    }
    public void slow()
    {
        ValueAnimator animator = ValueAnimator
                .ofFloat(speed * 0.5f, speed)
                .setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                speed = (float) valueAnimator.getAnimatedValue();
            }
        });
        animator.start();
    }
}
