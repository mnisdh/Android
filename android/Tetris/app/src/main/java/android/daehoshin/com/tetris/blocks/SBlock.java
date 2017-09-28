package android.daehoshin.com.tetris.blocks;

import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Created by daeho on 2017. 9. 28..
 */

public class SBlock extends Block {
    public SBlock(float x, float y, float unit) {
        super(x, y, unit);

        rot = 1;
    }

    @Override
    void initStyle() {
        paint.setColor(Color.parseColor("#1FEF00"));
        innerBorderPaint.setColor(Color.parseColor("#9AED8E"));
    }

    @Override
    void drawLeft(Canvas canvas) {
        drawSingleRect(canvas, 0, 0);
        drawSingleRect(canvas, 0, 1);
        drawSingleRect(canvas, 1, 1);
        drawSingleRect(canvas, 1, 2);
    }

    @Override
    void drawBottom(Canvas canvas) {
        drawSingleRect(canvas, 1, 2);
        drawSingleRect(canvas, 2, 2);
        drawSingleRect(canvas, 0, 3);
        drawSingleRect(canvas, 1, 3);
    }

    @Override
    void drawRight(Canvas canvas) {
        drawSingleRect(canvas, 2, 1);
        drawSingleRect(canvas, 2, 2);
        drawSingleRect(canvas, 3, 2);
        drawSingleRect(canvas, 3, 3);
    }

    @Override
    void drawTop(Canvas canvas) {
        drawSingleRect(canvas, 2, 0);
        drawSingleRect(canvas, 3, 0);
        drawSingleRect(canvas, 1, 1);
        drawSingleRect(canvas, 2, 1);
    }

}
