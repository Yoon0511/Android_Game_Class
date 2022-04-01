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
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
    private Bitmap soccerBitmap;
    private Rect soccerSrcRect = new Rect();
    private Rect soccer1DrcRect = new Rect();
    private Rect soccer2DrcRect = new Rect();
    private Paint fpsPaint = new Paint();
    private int ball1Dx, ball1Dy;
    private int ball2Dx,ball2Dy;
    private long previousTimeNanos;
    private int framesPerSecond;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
       initView();

    }

    private void initView(){
        Resources res = getResources();
        soccerBitmap = BitmapFactory.decodeResource(res,R.mipmap.soccer_ball_240);
        soccerSrcRect.set(0,0,soccerBitmap.getWidth(),soccerBitmap.getHeight());
        soccer1DrcRect.set(0,0,200,200);
        soccer2DrcRect.set(0,0,200,200);

        ball1Dx = 10;
        ball1Dy = 10;

        ball2Dx = 7;
        ball2Dy = 15;

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100);
        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long currentTimeNanos) {
        long now = currentTimeNanos;
//        long now = System.currentTimeMillis();
        int elapsed = (int) (now - previousTimeNanos);
        framesPerSecond = 1_000_000_000 / elapsed;
        //Log.v(TAG,"Elapsed: " + elapsed + " FPS: " + framesPerSecond);
        previousTimeNanos = now;
        update();
        invalidate();
        Choreographer.getInstance().postFrameCallback(this);
    }

    private void update() {
        soccer1DrcRect.offset(ball1Dx, ball1Dy);
        soccer2DrcRect.offset(ball2Dx,ball1Dy);

        if(ball1Dx > 0){
            if(soccer1DrcRect.right > getWidth()){
                ball1Dx = -ball1Dx;
            }
        }else{
            if(soccer1DrcRect.left < 0){
                ball1Dx = -ball1Dx;
            }
        }

        if(ball1Dy > 0){
            if(soccer1DrcRect.bottom > getHeight()){
                ball1Dy = -ball1Dy;
            }
        }else{
            if(soccer1DrcRect.top < 0){
                ball1Dy = -ball1Dy;
            }
        }

        if(ball2Dx > 0){
            if(soccer2DrcRect.right > getWidth()){
                ball2Dx = -ball2Dx;
            }
        }else{
            if(soccer2DrcRect.left < 0){
                ball2Dx = -ball2Dx;
            }
        }

        if(ball2Dy > 0){
            if(soccer2DrcRect.bottom > getHeight()){
                ball2Dy = -ball2Dy;
            }
        }else{
            if(soccer2DrcRect.top < 0){
                ball2Dy = -ball2Dy;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(soccerBitmap,soccerSrcRect, soccer1DrcRect,null);
        canvas.drawBitmap(soccerBitmap,soccerSrcRect, soccer2DrcRect,null);
        canvas.drawText("FPS: " + framesPerSecond,100,100,fpsPaint);
        //Log.d(TAG,"onDraw()");
    }


}
