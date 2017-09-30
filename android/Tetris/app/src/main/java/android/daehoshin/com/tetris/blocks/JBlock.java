package android.daehoshin.com.tetris.blocks;

import android.graphics.Color;

/**
 * Created by daeho on 2017. 9. 28..
 */

public class JBlock extends Block {
    public JBlock(float x, float y, float unit) {
        super(x, y, unit);

        blockType = BlockType.J;
    }

    @Override
    void initStyle() {
        paint.setColor(Color.parseColor("#0300DD"));
        innerFillPaint.setColor(Color.parseColor("#0300DD"));
        innerBorderPaint.setColor(Color.parseColor("#6262DB"));
    }

    @Override
    void setRects(int i) {
        int t = blockType.getValue();
        switch (i){
            case 1:
                rects = new int[][]{{0,t,t},
                                    {0,t,0},
                                    {0,t,0}};
                break;
            case 2:
                rects = new int[][]{{0,0,0},
                                    {t,t,t},
                                    {0,0,t}};
                break;
            case 3:
                rects = new int[][]{{0,t,0},
                                    {0,t,0},
                                    {t,t,0}};
                break;
            default:
                rects = new int[][]{{t,0,0},
                                    {t,t,t},
                                    {0,0,0}};
                break;
        }

    }

}
