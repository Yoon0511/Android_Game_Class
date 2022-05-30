package kr.ac.my_towerdefence.game;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
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
    static final int maxX = 15;
    static final int maxY = 10;

    protected static int[] BITMAP_IDS = {
            R.mipmap.tile00,
            R.mipmap.tile01
    };
    protected class Point {
        float x, y;
        float dx, dy;
    }
    private String TAG = Map.class.getSimpleName();
    protected ArrayList<Point> points = new ArrayList<>();
    protected static int[][] map = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,25,24,23,0,0,0,0,12,11,10,9,8,0,0},
            {27,26,0,22,0,0,0,0,13,0,0,0,7,0,0},
            {0,0,0,21,0,0,0,0,14,0,0,0,6,0,0},
            {0,0,0,20,19,18,17,16,15,0,0,0,5,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,4,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,3,2,1},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    };

    //    protected static int[][] map = new int[maxY][maxX];
    protected static final ArrayList<Tile> roadTile = new ArrayList<>();
    private static Path path;

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
                MainGame.getInstance().add(MainGame.Layer.map,tile);
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

        for(Tile T : roadTile)
        {
            Point point = new Point();
            point.x = T.getX();
            point.y = T.getY();
            points.add(point);
        }
        buildPath();

        Matrix matrix = new Matrix();
        matrix.reset();
        float scale = tileWidth / 25f;
        matrix.setScale(scale, scale);
        Path transformedPath = new Path();
        path.transform(matrix, transformedPath);

        Enemy.setPath(path);
    };

    public ArrayList<Tile> getRoadTile() {
        return roadTile;
    }

    public int[][] getMap() {
        return map;
    }

    protected void buildPath() {
        int ptCount = points.size();
        if (ptCount < 2) return;

        for (int i = ptCount - 2; i < ptCount; i++) {
            Point pt = points.get(i);
            if (i == 0) { // only next
                Point next = points.get(i + 1);
                pt.dx = ((next.x - pt.x));
                pt.dy = ((next.y - pt.y));
            } else if (i == ptCount - 1) { // only prev
                Point prev = points.get(i - 1);
                pt.dx = ((pt.x - prev.x));
                pt.dy = ((pt.y - prev.y));
            } else { // prev and next
                Point next = points.get(i + 1);
                Point prev = points.get(i - 1);
                pt.dx = ((next.x - prev.x));
                pt.dy = ((next.y - prev.y));
            }
        }

        Point prev = points.get(0);
        path = new Path();
        path.moveTo(prev.x, prev.y);
        for (int i = 1; i < ptCount; i++) {
            Point pt = points.get(i);
            path.cubicTo(
                    prev.x + prev.dx, prev.y + prev.dy,
                    pt.x - pt.dx, pt.y - pt.dy,
                    pt.x, pt.y);
            prev = pt;
        }
    }
    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

    }
}
