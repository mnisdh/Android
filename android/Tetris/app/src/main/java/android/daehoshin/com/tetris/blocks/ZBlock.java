package android.daehoshin.com.tetris.blocks;

import android.graphics.Color;

/**
 * Created by daeho on 2017. 9. 28..
 */

public class ZBlock extends Block {
    public ZBlock(float x, float y, float unit) {
        super(x, y, unit);

        blockType = BlockType.Z;
    }

    @Override
    void initStyle() {
        paint.setColor(Color.parseColor("#F20000"));
        innerFillPaint.setColor(Color.parseColor("#F20000"));
        innerBorderPaint.setColor(Color.parseColor("#EF8F8F"));
    }

    @Override
    void setRects(int i) {
        int t = blockType.getValue();
        switch (i){
            case 1:
                rects = new int[][]{{0,0,t},
                                    {0,t,t},
                                    {0,t,0}};
                break;
            case 2:
                rects = new int[][]{{0,0,0},
                                    {t,t,0},
                                    {0,t,t}};
                break;
            case 3:
                rects = new int[][]{{0,t,0},
                                    {t,t,0},
                                    {t,0,0}};
                break;
            default:
                rects = new int[][]{{t,t,0},
                                    {0,t,t},
                                    {0,0,0}};
                break;
        }

    }

}
