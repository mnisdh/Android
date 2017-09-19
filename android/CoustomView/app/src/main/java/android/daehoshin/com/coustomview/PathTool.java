package android.daehoshin.com.coustomview;

import android.graphics.Path;

/**
 * Created by daeho on 2017. 9. 18..
 */

public class PathTool extends Path{
    private int color;
    private float size;

    public PathTool(int color, float size){
        this.color = color;
        this.size = size;
    }

    public int getColor(){
        return this.color;
    }

    public float getSize() { return this.size; }
}
