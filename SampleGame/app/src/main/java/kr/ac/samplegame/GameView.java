package kr.ac.samplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
//    private static final int BALL_COUNT = 10;

//    private ArrayList<Ball> balls = new ArrayList<>();
//    private ArrayList<GameObject> objects = new ArrayList<>();
//    private Fighter fighter;

    private Paint fpsPaint = new Paint();
    private long previousTimeNanos;
    private int framesPerSecond;

    public static GameView view;
    private boolean initialized;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//       initView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Metrics.width = w;
        Metrics.height = h;

        if(!initialized){
            initView();
            initialized = true;
        }

    }

    private void initView(){
        view = this;

//        Resources res = getResources();
//        Bitmap soccerBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
//        Ball.setBitmap(soccerBitmap);

        MainGame game = MainGame.getSingleton();
        game.init();

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100);
        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long currentTimeNanos) {
        long now = currentTimeNanos;
//        long now = System.currentTimeMillis();
        int elapsed = (int) (now - previousTimeNanos);
        if(elapsed != 0){
            framesPerSecond = 1_000_000_000 / elapsed;
            //Log.v(TAG,"Elapsed: " + elapsed + " FPS: " + framesPerSecond);
            previousTimeNanos = now;
            MainGame.getSingleton().update(elapsed);
            invalidate();
        }

        Choreographer.getInstance().postFrameCallback(this);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        MainGame.getSingleton().draw(canvas);
//        fighter.draw(canvas);
        canvas.drawText("FPS: " + framesPerSecond,100,100,fpsPaint);
        //Log.d(TAG,"onDraw()");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return MainGame.getSingleton().onTouchEvent(event);
    }
}
