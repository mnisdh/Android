package android.daehoshin.com.tetris.blocks;

import android.graphics.Color;

/**
 * Created by daeho on 2017. 9. 28..
 */

public class LBlock extends Block {
    public LBlock(float x, float y, float unit) {
        super(x, y, unit);

        blockType = BlockType.L;
    }

    @Override
    void initStyle() {
        paint.setColor(Color.parseColor("#F78300"));
        innerFillPaint.setColor(Color.parseColor("#F78300"));
        innerBorderPaint.setColor(Color.parseColor("#F2BF8A"));
    }

    @Override
    void setRects(int i) {
        int t = blockType.getValue();
        switch (i){
            case 1:
                rects = new int[][]{{0,t,0},
                                    {0,t,0},
                                    {0,t,t}};
                break;
            case 2:
                rects = new int[][]{{0,0,0},
                                    {t,t,t},
                                    {t,0,0}};
                break;
            case 3:
                rects = new int[][]{{t,t,0},
                                    {0,t,0},
                                    {0,t,0}};
                break;
            default:
                rects = new int[][]{{0,0,t},
                                    {t,t,t},
                                    {0,0,0}};
                break;
        }

    }

}
