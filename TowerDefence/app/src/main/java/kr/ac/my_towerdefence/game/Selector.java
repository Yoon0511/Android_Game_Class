package kr.ac.my_towerdefence.game;

import kr.ac.my_towerdefence.R;
import kr.ac.my_towerdefence.framework.Sprite;

public class Selector extends Sprite {
    private Tower tower;

    Tower getTower() {return tower;};
    public Selector(){
        super(0,0,Map.tileWidth,Map.tileHeight, R.mipmap.selection);
    }

    public Tower select(int mapx,int mapy)
    {
        if(mapx < 15 && mapx >=0 && mapy < 10 && mapy >= 0)
        {
            this.tower = MainGame.getInstance().TOWER[mapx][mapy];
            x = mapx * Map.tileWidth + Map.tileWidth/2;
            y = mapy * Map.tileHeight + Map.tileHeight/2;
            setDstRectWithRadius();
        }
        else
            return null;

        return tower;
    }
}
