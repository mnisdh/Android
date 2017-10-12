package android.daehoshin.com.musicplayer.player;

/**
 * Created by daeho on 2017. 10. 12..
 */

public class Player {
    private static Player p = null;
    public static Player getInstance(){
        if(p == null) new Player();

        return p;
    }

    private Player(){

    }


}
