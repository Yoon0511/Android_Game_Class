package kr.ac.my_towerdefence.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.my_towerdefence.R;
import kr.ac.my_towerdefence.framework.CollisionHelper;
import kr.ac.my_towerdefence.framework.GameObject;
import kr.ac.my_towerdefence.framework.Sound;

public class CollisionChecker implements GameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();

    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        ArrayList<GameObject> enemies = game.objectsAt(MainGame.Layer.enemy);
        ArrayList<GameObject> bullets = game.objectsAt(MainGame.Layer.bullet);
        for (GameObject o1: enemies) {
            if (!(o1 instanceof Enemy)) {
                continue;
            }
            Enemy enemy = (Enemy) o1;
            for (GameObject o2: bullets) {
                if (!(o2 instanceof Bullet)) {
                    continue;
                }
                Bullet bullet = (Bullet) o2;
                if (CollisionHelper.collides(enemy, bullet)) {
                    bullet.abilityActivation(enemy);
                    game.remove(bullet);
                    float power = bullet.getPower();
                    boolean dead = enemy.decreaseLife(power);
                    if (dead) {
                        game.remove(enemy);
                        MainGame.getInstance().score.add(enemy.getScore());
                        Sound.playEffect(R.raw.jelly_coin);
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
