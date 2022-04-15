package kr.ac.my_towerdefence;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import kr.ac.my_towerdefence.framework.GameView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this, null));

    }
}