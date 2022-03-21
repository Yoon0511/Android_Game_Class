package kr.ac.carsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final ArrayList<Integer> CARD_IDS = new ArrayList<>();
    private static final ArrayList<ImageButton> CARDS = new ArrayList<>();
    private ImageButton previousButton;
    private int[] resIds = new int []{
            R.mipmap.card_as,R.mipmap.card_2c,R.mipmap.card_3d,R.mipmap.card_4h,
            R.mipmap.card_5s,R.mipmap.card_jc,R.mipmap.card_kd,R.mipmap.card_qh,
            R.mipmap.card_as,R.mipmap.card_2c,R.mipmap.card_3d,R.mipmap.card_4h,
            R.mipmap.card_5s,R.mipmap.card_jc,R.mipmap.card_kd,R.mipmap.card_qh
    };
    private int Flips;
    private TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setCardIds();
        scoreTextView = findViewById(R.id.scoreTextView);
        startGame();
    }

    private void startGame() {
        for(int i = 0;i< resIds.length;++i){
            int resId = resIds[i];
            ImageButton btn = CARDS.get(i);
            btn.setTag(resId);
        }
        setScore(0);
    }

    public void onBtnCard(View view) {


        if ((ImageButton) view == previousButton){
            Log.d("MYTAG","same card");
            return;
        }

        int btnindex = findButtonIndex(view.getId());
        ImageButton imagebutton = CARDS.get(btnindex);

        int prevResId = 0;
        if(previousButton != null){
            prevResId = (Integer) previousButton.getTag();
        }


        int resId = (Integer)imagebutton.getTag();
        if(resId != prevResId){
            imagebutton.setImageResource(resId);
            if(previousButton != null){
                previousButton.setImageResource(R.mipmap.card_blue_back);
            }
            previousButton = CARDS.get(btnindex);
            setScore(Flips + 1);
        }
        else{
            imagebutton.setVisibility(View.INVISIBLE);
            previousButton.setVisibility(View.INVISIBLE);
            previousButton = null;
        }
    }

    private void setScore(int flips) {
        this.Flips = flips;

        scoreTextView.setText("Flips: " + flips);
    }

    private int findButtonIndex(int id) {
        int len = CARD_IDS.size();
        for(int i = 0;i<len;++i){
            if(id == CARD_IDS.get(i)){
                return i;
            }
        }
        return -1;
    }

    private  void setCardIds() {
        int listindex = 0;
        int id = 0;
        for(int i = 0;i<4;++i){
            for(int j = 0;j<4;++j) {
                if(i == 0){
                    id = getResources().getIdentifier("card_0"+j, "id", getPackageName());
                }
                else{
                    id = getResources().getIdentifier("card_"+(i * 10 + j), "id", getPackageName());
                }
                CARD_IDS.add(listindex,id);
                CARDS.add(listindex,(ImageButton) findViewById(id));
                listindex++;
            }
        }
    }

    public void onBtnRestart(View view) {
        askRetry();
    }

    private void askRetry() {
        new AlertDialog.Builder(this)
                .setTitle("Restart")
                .setMessage("Do you really want to restart the game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startGame();
                    }
                })
                .setNegativeButton("No", null)
                .create()
                .show();
    }
}