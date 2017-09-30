package android.daehoshin.com.tetris.blocks;

import android.graphics.Color;

/**
 * Created by daeho on 2017. 9. 28..
 */

public class TBlock extends Block {
    public TBlock(float x, float y, float unit) {
        super(x, y, unit);

        blockType = BlockType.T;
    }

    @Override
    void initStyle() {
        paint.setColor(Color.parseColor("#A000EA"));
        innerFillPaint.setColor(Color.parseColor("#A000EA"));
        innerBorderPaint.setColor(Color.parseColor("#CD94E8"));
    }

    @Override
    void setRects(int i) {
        int t = blockType.getValue();
        switch (i){
            case 1:
                rects = new int[][]{{0,t,0},
                                    {0,t,t},
                                    {0,t,0}};
                break;
            case 2:
                rects = new int[][]{{0,0,0},
                                    {t,t,t},
                                    {0,t,0}};
                break;
            case 3:
                rects = new int[][]{{0,t,0},
                                    {t,t,0},
                                    {0,t,0}};
                break;
            default:
                rects = new int[][]{{0,t,0},
                                    {t,t,t},
                                    {0,0,0}};
                break;
        }

    }

}
