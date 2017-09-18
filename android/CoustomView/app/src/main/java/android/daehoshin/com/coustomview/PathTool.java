package android.daehoshin.com.coustomview;

import android.graphics.Path;

/**
 * Created by daeho on 2017. 9. 18..
 */

public class PathTool extends Path{
    private int color;

    public PathTool(int color){
        this.color = color;
    }

    public int getColor(){
        return this.color;
    }
}
