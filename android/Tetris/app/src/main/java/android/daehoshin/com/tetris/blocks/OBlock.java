package android.daehoshin.com.tetris.blocks;

import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Created by daeho on 2017. 9. 28..
 */

public class OBlock extends Block {
    public OBlock(float x, float y, float unit) {
        super(x, y, unit);
    }

    @Override
    void initStyle() {
        paint.setColor(Color.parseColor("#F7E600"));
        innerBorderPaint.setColor(Color.parseColor("#F4ED97"));
    }

    private void drawCommon(Canvas canvas){
        drawSingleRect(canvas, 0, 2);
        drawSingleRect(canvas, 0, 3);
        drawSingleRect(canvas, 1, 2);
        drawSingleRect(canvas, 1, 3);
    }

    @Override
    void drawLeft(Canvas canvas) {
        drawCommon(canvas);
    }

    @Override
    void drawBottom(Canvas canvas) {
        drawCommon(canvas);
    }

    @Override
    void drawRight(Canvas canvas) {
        drawCommon(canvas);
    }

    @Override
    void drawTop(Canvas canvas) {
        drawCommon(canvas);
    }

}
