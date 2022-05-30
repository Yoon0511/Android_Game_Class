package kr.ac.my_towerdefence.game;

import static java.lang.Math.log;
import static java.lang.Math.sqrt;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.Log;

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

    private float targetX,targetY;
    private float dx,dy;
    private float angle;
    private  float orgx =  MainGame.getInstance().roadTileAt(0).getX();
    private  float orgy =  MainGame.getInstance().roadTileAt(0).getX();

    private int tartgetIndex = 1;
    private static PathMeasure pathMeasure;

    public static void setPath(Path path){
        Enemy.pathMeasure = new PathMeasure(path,false);
    }
    protected static int[] BITMAP_IDS = {
           R.mipmap.enemy00,
    };
    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = BITMAP_IDS.length;
//    private static ArrayList<Enemy> recyceBin = new ArrayList<>();
    public static Enemy get(int level,float x,float y, float speed) {
        Enemy enemy = (Enemy) RecycleBin.get(Enemy.class);
        if (enemy != null) {
            //Enemy enemy = recyceBin.remove(0);
            enemy.set(level,x,y, speed);
            return enemy;
        }
        return new Enemy(level,x,y,speed);
    }

    private void set(int level,float x,float y, float speed) {
        bitmap = BitmapPool.get(BITMAP_IDS[level - 1]);
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.level = level;
        life = maxLife = level * 10;
        gauge.setValue(1.0f);
        tartgetIndex = 1;
        setTartPosistion(tartgetIndex);
    }

    private Enemy(int level,float x,float y, float speed) {
//        super(x, 0, R.dimen.enemy_radius, R.mipmap.f_01_01);
        super(x, y, size, size, BITMAP_IDS[level - 1], 6, 0);
        this.level = level;
        this.speed = speed;
        life = maxLife = level * 10;

        gauge = new Gauge(
                Metrics.size(R.dimen.enemy_gauge_width_fg), R.color.enemy_gauge_fg,
                Metrics.size(R.dimen.enemy_gauge_width_bg), R.color.enemy_gauge_bg,
                size * 0.5f
        );
        gauge.setValue(1.0f);

       setTartPosistion(tartgetIndex);
    }
    public void setTartPosistion(int tartgetIndex){
        targetX = MainGame.getInstance().roadTileAt(tartgetIndex).getX();
        targetY = MainGame.getInstance().roadTileAt(tartgetIndex).getY();

//        angle = (float)Math.atan2(targetY - this.y,targetX - this.x);
//        dx = (float) (speed * Math.cos(angle));
//        dy = (float) (speed * Math.sin(angle));
//        Log.d(TAG, "tx : "+targetX+" ty:"+targetY +" angle: "+angle + " dx :"+dx+" dy : " +dy);
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

    void move(){
        float frameTime = MainGame.getInstance().frameTime;

       float speed = this.speed * frameTime;
       if(Math.abs(targetX - x) < 2)
       {
           x = targetX;
       }
       else
       {
           if(targetX < x)
           {
               x -= speed;
           }else{
               x += speed;
           }
       }

        if(Math.abs(targetY-y) < 2)
        {
            y = targetY;
        }
        else{
            if(targetY < y){
                y -= speed;
            }else{
                y += speed;
            }
        }

       float dis = (float) sqrt((targetY - y)  * (targetY - y) + (targetX - x) * (targetX - x));
       if(dis <= 3.0f){
           tartgetIndex++;
           setTartPosistion(tartgetIndex);
           dis = 5.0f;
       }
        setDstRectWithRadius();
        boundingRect.set(dstRect);
        boundingRect.inset(inset, inset);
        if (dstRect.top > Metrics.height) {
            MainGame.getInstance().remove(this);
            //recyceBin.add(this);
        }
    }
    void nextRoad(){

    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

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
        return level * level * 100;
    }

    public boolean decreaseLife(float power) {
        life -= power;
        if (life <= 0) return true;
        gauge.setValue(life / maxLife);
        return false;
    }
}
