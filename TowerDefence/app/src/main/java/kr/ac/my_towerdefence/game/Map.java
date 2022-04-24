package kr.ac.my_towerdefence.game;

import android.graphics.Canvas;
import android.opengl.Matrix;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import kr.ac.my_towerdefence.R;
import kr.ac.my_towerdefence.framework.BitmapPool;
import kr.ac.my_towerdefence.framework.GameObject;
import kr.ac.my_towerdefence.framework.Metrics;
import kr.ac.my_towerdefence.framework.Sprite;

public class Map implements GameObject {
    static final int maxX = 10;
    static final int maxY = 10;

    protected static int[] BITMAP_IDS = {
            R.mipmap.tile00,
            R.mipmap.tile01
    };
    private String TAG = Map.class.getSimpleName();

    protected static int[][] map = {
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,11,10,9,8,0,0,0},
            {0,0,0,12,0,0,7,0,0,0},
            {0,0,0,13,0,0,6,0,0,0},
            {0,0,0,14,0,0,5,0,0,0},
            {18,17,16,15,0,0,4,3,2,1},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
    };

    //    protected static int[][] map = new int[maxY][maxX];
    protected static final ArrayList<Tile> roadTile = new ArrayList<>();

    public Map(){

//        boolean a = true;
//        for(int i = 0;i<maxX;++i){
//            for(int k = 0;k<maxY;++k){
//                if (a){
//                    map[i][k] = 0;
//                }
//                else{
//                    map[i][k] = 1;
//                }
//                a = !a;
//            }
//        }

    };

    public void init() {
//        float tileWidth = BitmapPool.get(BITMAP_IDS[0]).getWidth();
//        float tileHeight = BitmapPool.get(BITMAP_IDS[0]).getHeight();

        float tileWidth = Metrics.width/maxX;
        float tileHeight = Metrics.height/maxY;

        for (int y = 0; y < maxY; ++y) {
            for (int x = 0; x < maxX; ++x) {
                int order = map[y][x];
                float tileX = x * tileWidth + tileWidth/2;
                float tileY = y * tileHeight + tileHeight/2;
                Tile tile;
                if(order == 0){
                    tile = new Tile(tileX,tileY,tileWidth,tileHeight,BITMAP_IDS[order],0);
                }
                else{
                    tile = new Tile(tileX,tileY,tileWidth,tileHeight,BITMAP_IDS[1],order);
                    roadTile.add(tile);
                }
            }
        }
        Collections.sort(roadTile, new Comparator<Tile>() {
            @Override
            public int compare(Tile tile, Tile t1) {
                int a = tile.getRoadOrder();
                int b = t1.getRoadOrder();
                if (a == b) return 0;
                if (a > b) return 1;
                if (a < b) return -1;
                return 0;
            }
        });

        for(Tile t : roadTile){
            Log.d(TAG, "tx:" + t.getX() + "ty: " + t.getY());
        }
    };

    public ArrayList<Tile> getRoadTile() {
        return roadTile;
    }

    public int[][] getMap() {
        return map;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

    }
}