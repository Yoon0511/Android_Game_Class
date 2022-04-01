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
import android.util.Log;
import android.view.Choreographer;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
    private Bitmap soccerBitmap;
    private Rect soccerSrcRect = new Rect();
    private Rect soccerDrcRect = new Rect();
    private Paint fpsPaint = new Paint();
    private int ballDx,ballDy;
    private long previousTimeMills;
    private int framesPerSecond;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
       initView();

    }

    private void initView(){
        Resources res = getResources();
        soccerBitmap = BitmapFactory.decodeResource(res,R.mipmap.soccer_ball_240);
        soccerSrcRect.set(0,0,soccerBitmap.getWidth(),soccerBitmap.getHeight());
        soccerDrcRect.set(0,0,200,200);

        ballDx = 10;
        ballDy = 10;

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100);
        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long l) {
        long now = System.currentTimeMillis();
        int elapsed = (int) (now - previousTimeMills);
        framesPerSecond = 1000 / elapsed;
        //Log.v(TAG,"Elapsed: " + elapsed + " FPS: " + framesPerSecond);
        previousTimeMills = now;
        update();
        invalidate();
        Choreographer.getInstance().postFrameCallback(this);
    }

    private void update() {
        soccerDrcRect.offset(ballDx,ballDy);

        if(ballDx > 0){
            if(soccerDrcRect.right > getWidth()){
                ballDx = -ballDx;
            }
        }else{
            if(soccerDrcRect.left < 0){
                ballDx = - ballDx;
            }
        }

        if(ballDy > 0){
            if(soccerDrcRect.bottom > getHeight()){
                ballDy = -ballDy;
            }
        }else{
            if(soccerDrcRect.top < 0){
                ballDy = -ballDy;
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(soccerBitmap,soccerSrcRect,soccerDrcRect,null);
        canvas.drawText("FPS: " + framesPerSecond,100,100,fpsPaint);
        //Log.d(TAG,"onDraw()");
    }


}
