package kr.ac.my_towerdefence.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    private int[][] map;
    private int levelText;
    private Tower tower;
    private Paint label = new Paint();
    private float labelx,labely;

    public Ui() {
        this.x = tileWidth * 16;
        this.y = tileWidth * 4;
        bitmap = BitmapPool.get(R.mipmap.popup_table);
        float w = tileWidth * 2;
        float h = tileHeight * 10;
        this.radius = w / 2;
        dstRect.set(x - w / 2, y - h / 2, x + w / 2, y + h / 2);
        map = MainGame.getInstance().getmap();
        this.labelx = this.x+ w * -0.1f;
        this.labely = h;
        label.setColor(Color.BLACK);
        label.setTextSize(50);

        moveTowerImg = new Sprite(-100,-100,R.dimen.tower_radious,R.mipmap.tower00);
        MainGame.getInstance().add(MainGame.Layer.controller,moveTowerImg);

        towerBtnInit(h * 0.2f,R.mipmap.tower00);
        towerBtnInit(h * 0.4f,R.mipmap.icetower);
        towerBtnInit(h * 0.6f,R.mipmap.explosiontower);
        towerOpthionBtnInit(x + w * -0.2f,h * 0.8f,R.mipmap.uninstall);
        towerOpthionBtnInit(x + w * 0.2f,h * 0.8f,R.mipmap.upgrade);

    }
    public void CreateTower(int mapx,int mapy,Bitmap bitmap)
    {
        Tower tower = new Tower(tileWidth * mapx + tileWidth/2,tileHeight * mapy + tileHeight/2,bitmap);
        MainGame.getInstance().add(MainGame.Layer.player, tower);
        MainGame.getInstance().TOWER[mapx][mapy] = tower;
    }

    public void setMoveTowerImg(float x,float y,Bitmap bitmap)
    {
        moveTowerImg.setBitmap(bitmap);
        moveTowerImg.setPos(x,y);
        moveTowerImg.setDstRectWithRadius();
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap,null,dstRect,null);
        Log.d(TAG,""+labelx);
        canvas.drawText("" + levelText,labelx,labely * 0.725f,label);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }

    private void towerBtnInit(float y,int bitmap)
    {
        MainGame.getInstance().add(MainGame.Layer.controller,new Button(
                this.x, y, tileWidth * 1.5f, tileHeight * 1.5f,bitmap, bitmap, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action,MotionEvent e,Bitmap bitmap) {
                if(action == Button.Action.pressed)
                {
                    setMoveTowerImg(e.getX(),e.getY(),bitmap);
                    return true;
                }
                else if(action == Button.Action.released) {
                    int x = (int) (e.getX() / tileWidth);
                    int y = (int) (e.getY() / tileHeight);
                    setMoveTowerImg(-100, -100, bitmap);
                    if (x < 15 && x >= 0 && y >= 0 && y < 10) {
                        if(MainGame.getInstance().TOWER[x][y] == null &&
                        map[y][x] == 0)
                        {
                            CreateTower(x, y, bitmap);
                        }

                    }
                    return true;
                }
                else if(action == Button.Action.move) {
                        setMoveTowerImg(e.getX(), e.getY(), bitmap);
                        return true;
                    }
                return false;
            }
        }
        ));
    }
    public void setUiTower(Tower tower) {
        this.tower = tower;
        if(tower != null){
            levelText = tower.getlevel();
        }
        else{
            levelText = 0;
        }
    }

    private void towerOpthionBtnInit(float x,float y,int bitmap)
    {
        MainGame.getInstance().add(MainGame.Layer.controller,new Button(
                x, y, tileWidth * 0.7f, tileHeight * 0.7f, bitmap, bitmap, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action, MotionEvent e, Bitmap bitmap) {
                if(tower == null) return false;

                if(action == Button.Action.pressed){
                    if(bitmap == BitmapPool.get(R.mipmap.upgrade)) {
                        Log.d(TAG,"update");
                        tower.upgrade();
                        return true;
                    }
                    else if(bitmap == BitmapPool.get(R.mipmap.uninstall)){
                        Log.d(TAG,"uninstall");
                        tower.remove();
                        tower = null;
                        return true;
                    }
                }
                return false;
            }
        }
        ));
    }
}
