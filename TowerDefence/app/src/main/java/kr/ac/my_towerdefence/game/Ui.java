package kr.ac.my_towerdefence.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import kr.ac.my_towerdefence.R;
import kr.ac.my_towerdefence.framework.BitmapPool;
import kr.ac.my_towerdefence.framework.Metrics;
import kr.ac.my_towerdefence.framework.Sprite;
import kr.ac.my_towerdefence.framework.Touchable;

public class Ui extends Sprite implements Touchable {
    private final float tileWidth = Metrics.width/17;
    private final float tileHeight = Metrics.height/10;
    private float x,y;
    private RectF srcRect = new RectF();
    private RectF dstRect = new RectF();
    public Ui() {
        this.x = tileWidth * 16;
        this.y = tileWidth * 4;
        bitmap = BitmapPool.get(R.mipmap.popup_table);
        float w = tileWidth * 2;
        float h = tileHeight * 10;
        this.radius = w / 2;
        dstRect.set(x - w / 2, y - h / 2, x + w / 2, y + h / 2);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap,null,dstRect,null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }
}
