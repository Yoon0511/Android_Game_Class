package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.BoxCollidable;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.CollisionHelper;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.R;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.GameObject;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.GameView;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.Recyclable;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.RecycleBin;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    private Paint collisionPaint;

    public static MainGame getInstance() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return singleton;
    }

    public float frameTime;

    private MainGame() {
    }

    private static MainGame singleton;

    private static final int BALL_COUNT = 10;
//    private ArrayList<GameObject> objects = new ArrayList<>();
    private ArrayList<ArrayList<GameObject>> layers;
    public enum Layer{
        bullet,enemy,player,controller,COUNT
    }
    private Fighter fighter;

    public static void clear() {
        singleton = null;
    }

    public void init() {
//        Random random = new Random();
//        float min = Metrics.size(R.dimen.ball_speed_min);
//        float max = Metrics.size(R.dimen.ball_speed_max);
//        float diff = max - min;
//        for (int i = 0; i < BALL_COUNT; i++) {
//            float dx = random.nextFloat() * diff + min;
//            float dy = random.nextFloat() * diff + min;
//            Ball ball = new Ball(dx, dy);
//            objects.add(ball);
//        }
//        objects.clear();

        initLayers(Layer.COUNT.ordinal());

//        objects.add(new EnemyGenerator());
        add(Layer.controller,new EnemyGenerator());

        float fighterY = Metrics.height - Metrics.size(R.dimen.fighter_y_offset);
        fighter = new Fighter(Metrics.width / 2, fighterY);

        add(Layer.player,fighter);

        collisionPaint = new Paint();
        collisionPaint.setColor(Color.RED);
        collisionPaint.setStyle(Paint.Style.STROKE);
    }

    private void initLayers(int count) {
        layers = new ArrayList<>();
        for (int i = 0;i<count;i++){
            layers.add((new ArrayList<>()));
        }
    }

    public void update(int elapsedNanos) {
        frameTime = elapsedNanos * 1e-9f; // 1_000_000_000.0f;
        for(ArrayList<GameObject> objects : layers){
            for (GameObject gobj : objects) {
                gobj.update();
            }
        }
        checkCollision();
    }

//    private void checkCollision() {
//
//    }
    public ArrayList<GameObject> objectsAt()
    {

    }

    public void draw(Canvas canvas) {
        for(ArrayList<GameObject> objects : layers) {
            for (GameObject gobj : objects) {
                gobj.draw(canvas);
                if (gobj instanceof BoxCollidable) {
                    RectF rect = ((BoxCollidable) gobj).getBoundingBox();
                    canvas.drawRect(rect, collisionPaint);
                }
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                fighter.setTargetPosition(x, y);
                if (action == MotionEvent.ACTION_DOWN) {
                    //fighter.fire();
                }
                return true;
        }
        return false;
    }

    public void add(Layer layer, GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<GameObject> objects = layers.get(layer.ordinal());
                objects.add(gameObject);
            }
        });
    }

    public void remove(GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
//                objects.remove(gameObject);
                for(ArrayList<GameObject> objects : layers){
                    boolean removed = objects.remove(gameObject);
                    if(removed){
                        if (gameObject instanceof Recyclable){
                            RecycleBin.add((Recyclable) gameObject);
                        }
                        break;
                    }
                }
            }
        });
    }

    public int objectCount() {
        int count = 0;
        for(ArrayList<GameObject> objects : layers){
            count += objects.size();
        }
        return count;
    }
}
