package android.daehoshin.com.tetris.blocks;

import android.graphics.Color;

/**
 * Created by daeho on 2017. 9. 28..
 */

public class OBlock extends Block {
    public OBlock(float x, float y, float unit) {
        super(x, y, unit);

        blockType = BlockType.O;
    }

    @Override
    void initStyle() {
        paint.setColor(Color.parseColor("#F7E600"));
        innerFillPaint.setColor(Color.parseColor("#F7E600"));
        innerBorderPaint.setColor(Color.parseColor("#F4ED97"));
    }

    @Override
    void setRects(int i) {
        int t = blockType.getValue();
        rects = new int[][]{{0,t,t,0},
                            {0,t,t,0}};
    }

}
