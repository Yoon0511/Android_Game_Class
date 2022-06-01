package kr.ac.my_towerdefence.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.my_towerdefence.R;
import kr.ac.my_towerdefence.framework.BoxCollidable;
import kr.ac.my_towerdefence.framework.GameObject;
import kr.ac.my_towerdefence.framework.GameView;
import kr.ac.my_towerdefence.framework.Metrics;
import kr.ac.my_towerdefence.framework.Recyclable;
import kr.ac.my_towerdefence.framework.RecycleBin;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    private Paint collisionPaint;
    private Score score;
    private Map map;
    private Tile tile;
    private Ui ui;
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
    public enum Layer {
        map, enemy, player,bullet ,ui, controller, COUNT
    }
    private Fighter fighter;

    public static void clear() {
        singleton = null;
    }

    public void init() {

//        objects.clear();
        initLayers(Layer.COUNT.ordinal());

        add(Layer.controller, new EnemyGenerator());
        add(Layer.controller, new CollisionChecker());

        map = new Map();
        map.init();
        add(Layer.map,map);
        //float fighterY = Metrics.height - Metrics.size(R.dimen.fighter_y_offset);
        //fighter = new Fighter(Metrics.width / 2, fighterY);
        float tileWidth = map.getTileWidth();
        float tileHeight = map.getTileHeight();
        Tower tower = new Tower(tileWidth * 6 + tileWidth/2,tileHeight * 7 + tileHeight/2);
        add(Layer.player, tower);


        ui = new Ui();
        add(Layer.ui,ui);
        //score = new Score();
        //add(Layer.ui, score);

        collisionPaint = new Paint();
        collisionPaint.setColor(Color.RED);
        collisionPaint.setStyle(Paint.Style.STROKE);
    }

    private void initLayers(int count) {
        layers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            layers.add(new ArrayList<>());
        }
    }

    public void update(int elapsedNanos) {
        frameTime = elapsedNanos * 1e-9f; // 1_000_000_000.0f;
        for (ArrayList<GameObject> objects : layers) {
            for (GameObject gobj : objects) {
                gobj.update();
            }
        }

//        checkCollision();
    }

//    private void checkCollision() {
//    }

    public ArrayList<GameObject> objectsAt(Layer layer) {
        return layers.get(layer.ordinal());
    }
    public Tile roadTileAt(int index) {return map.getRoadTile().get(index);}

    public void draw(Canvas canvas) {
        for (ArrayList<GameObject> objects : layers) {
            for (GameObject gobj : objects) {
                gobj.draw(canvas);
                if (gobj instanceof BoxCollidable) {
                    RectF rect = ((BoxCollidable) gobj).getBoundingRect();
                    canvas.drawRect(rect, collisionPaint);
                }
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }
        int x = (int) (event.getX() / map.getTileWidth());
        int y = (int) (event.getY() / map.getTileHeight());
        Log.d(TAG, "onTouchEvent: " + x + " - " + y);

        if(ui.onTouchEvent(event)){
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
                for (ArrayList<GameObject> objects : layers) {
                    boolean removed = objects.remove(gameObject);
                    if (!removed) continue;
                    if (gameObject instanceof Recyclable) {
                        RecycleBin.add((Recyclable) gameObject);
                    }
                    break;
                }
            }
        });
    }

    public int objectCount() {
        int count = 0;
        for (ArrayList<GameObject> objects : layers) {
            count += objects.size();
        }
        return count;
    }
}
