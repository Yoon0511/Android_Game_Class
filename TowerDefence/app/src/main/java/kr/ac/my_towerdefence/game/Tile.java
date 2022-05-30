package kr.ac.my_towerdefence.game;

import kr.ac.my_towerdefence.framework.Sprite;

public class Tile extends Sprite {
    protected int roadOrder;

    public Tile(float x, float y, float w, float h, int bitmapResId,int order) {
        super(x, y, w, h, bitmapResId);
        this.roadOrder = order;
    }

    public int getRoadOrder(){
        return roadOrder;
    }
}
