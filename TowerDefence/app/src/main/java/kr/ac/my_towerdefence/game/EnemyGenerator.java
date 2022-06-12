package kr.ac.my_towerdefence.game;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.my_towerdefence.R;
import kr.ac.my_towerdefence.framework.GameObject;
import kr.ac.my_towerdefence.framework.Metrics;

public class EnemyGenerator implements GameObject {
    private static final float INITIAL_SPAWN_INTERVAL = 1.0f;
    private static final float INITIAL_BIG_SPAWN_INTERVAL = 20.0f;
    private float spawnInterval;
    private float fallSpeed;
    private float elapsedTime;
    private float BigWaveTime;
    private float BigWaveSpwan;
    private float BigWaveSpwanTime;
    private int wave;
    private boolean normalPhase;
    private int level = 1;

    public EnemyGenerator() {
        this.spawnInterval = INITIAL_SPAWN_INTERVAL;
        this.fallSpeed = Metrics.size(R.dimen.enemy_initial_speed);

        float enemySize = Metrics.width / 10.0f;
        Enemy.setSize(enemySize);
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        if(normalPhase)
        {
            BigWaveTime += frameTime;
            elapsedTime += frameTime;
            if (elapsedTime > spawnInterval) {
                spawn();
                elapsedTime -= spawnInterval;
                spawnInterval *= 0.995;
            }
            if(BigWaveTime > INITIAL_BIG_SPAWN_INTERVAL){
                normalPhase = false;
            }
        }
        else{
            BigWaveSpwan += frameTime;
            BigWaveSpwanTime += frameTime;
            if(BigWaveSpwan > spawnInterval*0.7){
                spawn();
                BigWaveSpwan -= spawnInterval*0.7;
            }
            if(BigWaveSpwanTime > 20.0f)
            {
                normalPhase = true;
                BigWaveSpwan = BigWaveSpwanTime = 0;
            }
        }

    }

    private void spawn() {
        wave++;
        Random rand = new Random();

        float x = 300;
        float y = 200;

        if(wave % 30 == 0)
        {
            level++;
        }

        Enemy enemy = Enemy.get(level, x,y,fallSpeed);
        MainGame.getInstance().add(MainGame.Layer.enemy, enemy);

    }

    @Override
    public void draw(Canvas canvas) {
    }
}
