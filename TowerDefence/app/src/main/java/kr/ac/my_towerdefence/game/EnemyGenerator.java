package kr.ac.my_towerdefence.game;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.my_towerdefence.R;
import kr.ac.my_towerdefence.framework.GameObject;
import kr.ac.my_towerdefence.framework.Metrics;

public class EnemyGenerator implements GameObject {
    private static final float INITIAL_SPAWN_INTERVAL = 1.0f;
    private final float spawnInterval;
    private final float fallSpeed;
    private float elapsedTime;
    private int wave;

    public EnemyGenerator() {
        this.spawnInterval = INITIAL_SPAWN_INTERVAL;
        this.fallSpeed = Metrics.size(R.dimen.enemy_initial_speed);

        float enemySize = Metrics.width / 10.0f;
        Enemy.setSize(enemySize);
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        elapsedTime += frameTime;
        if (elapsedTime > spawnInterval) {
            spawn();

            elapsedTime -= spawnInterval;
        }
    }

    private void spawn() {
        wave++;
        Random rand = new Random();
//        float x = MainGame.getInstance().roadTileAt(0).getX() + 10;
//        float y = MainGame.getInstance().roadTileAt(0).getY() + 10;

        float x = 300;
        float y = 200;

        int level = (wave + 15) / 10 - rand.nextInt(3);
        if (level < Enemy.MIN_LEVEL) level = Enemy.MIN_LEVEL;
        if (level > Enemy.MAX_LEVEL) level = Enemy.MAX_LEVEL;
        Enemy enemy = Enemy.get(level, x,y,fallSpeed);
        MainGame.getInstance().add(MainGame.Layer.enemy, enemy);

    }

    @Override
    public void draw(Canvas canvas) {
    }
}
