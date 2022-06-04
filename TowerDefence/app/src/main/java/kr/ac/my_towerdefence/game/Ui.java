package kr.ac.my_towerdefence.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.nfc.Tag;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.my_towerdefence.R;
import kr.ac.my_towerdefence.framework.BitmapPool;
import kr.ac.my_towerdefence.framework.Button;
import kr.ac.my_towerdefence.framework.Metrics;
import kr.ac.my_towerdefence.framework.Sprite;
import kr.ac.my_towerdefence.framework.Touchable;

public class Ui extends Sprite implements Touchable {
    private static final String TAG = Button.class.getSimpleName();
    private final float tileWidth = Metrics.width/17;
    private final float tileHeight = Metrics.height/10;
    private float x,y;
    private RectF srcRect = new RectF();
    private RectF dstRect = new RectF();
    private Button[] towerBtns;
    private Sprite moveTowerImg;

    public Ui() {
        this.x = tileWidth * 16;
        this.y = tileWidth * 4;
        bitmap = BitmapPool.get(R.mipmap.popup_table);
        float w = tileWidth * 2;
        float h = tileHeight * 10;
        this.radius = w / 2;
        dstRect.set(x - w / 2, y - h / 2, x + w / 2, y + h / 2);
        moveTowerImg = new Sprite(-100,-100,R.dimen.tower_radious,R.mipmap.tower00);
        MainGame.getInstance().add(MainGame.Layer.controller,moveTowerImg);

        MainGame.getInstance().add(MainGame.Layer.controller,new Button(
                this.x, h * 0.3f, tileWidth * 1.5f, tileHeight * 1.5f, R.mipmap.tower00, R.mipmap.tower00, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action,MotionEvent e,Bitmap bitmap) {
                if(action == Button.Action.pressed)
                {
                    setMoveTowerImg(e.getX(),e.getY());
                    return true;
                }
                else if(action == Button.Action.released)
                {
                    int x = (int) (e.getX() / tileWidth);
                    int y = (int) (e.getY() / tileHeight);
                    setMoveTowerImg(-100,-100);
                    CreateTower(x,y);

                    return true;
                }
                return false;
            }
        }
        ));
    }
    public void CreateTower(int mapx,int mapy)
    {
        Tower tower = new Tower(tileWidth * mapx + tileWidth/2,tileHeight * mapy + tileHeight/2);
        MainGame.getInstance().add(MainGame.Layer.player, tower);
    }

    public void setMoveTowerImg(float x,float y)
    {
        moveTowerImg.setPos(x,y);
        moveTowerImg.setDstRectWithRadius();
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
