package kr.ac.samplegame;

public class MainGame {
    public static MainGame getSingleton() {
        if (singleton == null){
            singleton = new MainGame();
        }
        return singleton;
    }
    private MainGame(){
        
    }
    private static MainGame singleton;
}
