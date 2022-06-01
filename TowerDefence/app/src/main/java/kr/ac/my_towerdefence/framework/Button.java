package kr.ac.my_towerdefence.framework;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;

public class Button extends Sprite implements Touchable {
    private static final String TAG = Button.class.getSimpleName();
    protected final Callback callback;
    private final Bitmap normalBitmap;
    private Bitmap pressedBitmap;
    private boolean pressed;

    public enum Action {
        pressed, released,move
    }
    public interface Callback {
        public boolean onTouch(Action action,MotionEvent e,Bitmap bitmap);
    }
    public Button(float x, float y, float w, float h, int bitmapResId, int pressedResId, Callback callback) {
        super(x, y, w, h, bitmapResId);
        normalBitmap = bitmap;
        if (pressedResId != 0) {
            pressedBitmap = BitmapPool.get(pressedResId);
        }
        this.callback = callback;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        if (!pressed && !dstRect.contains(x, y)) {
            return false;
        }
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                pressed = true;
                bitmap = pressedBitmap;
                callback.onTouch(Action.pressed,e,normalBitmap);
                return true;
            case MotionEvent.ACTION_UP:
                pressed = false;
                bitmap = normalBitmap;
                return callback.onTouch(Action.released,e,normalBitmap);
            case MotionEvent.ACTION_MOVE:
                return callback.onTouch(Action.pressed,e,normalBitmap);
        }
        return false;
    }
}
