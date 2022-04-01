package kr.ac.samplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View {
    private static final String TAG = GameView.class.getSimpleName();
    private Bitmap soccerBitmap;
    private Rect soccerSrcRect = new Rect();
    private Rect soccerDrcRect = new Rect();
    private Paint textPaint = new Paint();
    private int ballDx,ballDy;

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

        updateFrame();
    }

    private void updateFrame() {
        update();
        invalidate();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                updateFrame();
            }
        },16);
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
        Log.d(TAG,"onDraw()");
    }
}
