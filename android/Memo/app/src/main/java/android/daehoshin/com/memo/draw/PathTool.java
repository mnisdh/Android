package android.daehoshin.com.memo.draw;

import android.graphics.Path;

/**
 * Path에 color와 size를 포함하기위해 path를 상속받아 만든 클래스
 *
 * Created by daeho on 2017. 9. 18..
 */

public class PathTool extends Path{
    private int color;
    private float size;

    /**
     * 생성자에서 초기값을 받아 셋팅
     * @param color
     * @param size
     */
    public PathTool(int color, float size){
        this.color = color;
        this.size = size;
    }

    /**
     * 컬러를 받아오는 메소드
      * @return
     */
    public int getColor(){
        return this.color;
    }

    /**
     * 사이즈를 받아오는 메소드
     * @return
     */
    public float getSize() { return this.size; }
}
