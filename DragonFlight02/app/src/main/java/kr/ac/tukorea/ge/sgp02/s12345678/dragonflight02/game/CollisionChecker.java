package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.game;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.CollisionHelper;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.GameObject;

public class CollisionChecker implements GameObject {

    private static final String TAG = CollisionChecker.class.getSimpleName();

    @Override
    public void update() {
        ArrayList<GameObject> enemies = MainGame.getInstance().objectsAt(Layer.enemy);
        ArrayList<GameObject> bullets =  MainGame.getInstance().objectsAt(Layer.bullet);

        for(GameObject o1 : enemies){
            if(!(o1 instanceof Enemy)) {
                continue;
            }
            Enemy enemy = (Enemy) o1;
            for(GameObject o2 : bullets){
                if(!(o2 instanceof Bullet)){
                    continue;
                }
                Bullet bullet = (Bullet) o2;
                if(CollisionHelper.collides(enemy,bullet)){
                    Log.d(TAG,"Coliision");
                    remove(enemy);
                    remove(bullet);
                    break;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
