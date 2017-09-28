package android.daehoshin.com.tetris.blocks;

import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Created by daeho on 2017. 9. 28..
 */

public class IBlock extends Block {
    public IBlock(float x, float y, float unit) {
        super(x, y, unit);
    }

    @Override
    void initStyle() {
        paint.setColor(Color.parseColor("#00D1ED"));
        innerBorderPaint.setColor(Color.parseColor("#9DE1EA"));
    }

    @Override
    void drawLeft(Canvas canvas) {
        drawSingleRect(canvas, 0, 0);
        drawSingleRect(canvas, 0, 1);
        drawSingleRect(canvas, 0, 2);
        drawSingleRect(canvas, 0, 3);
    }

    @Override
    void drawBottom(Canvas canvas) {
        drawSingleRect(canvas, 0, 3);
        drawSingleRect(canvas, 1, 3);
        drawSingleRect(canvas, 2, 3);
        drawSingleRect(canvas, 3, 3);
    }

    @Override
    void drawRight(Canvas canvas) {
        drawSingleRect(canvas, 3, 0);
        drawSingleRect(canvas, 3, 1);
        drawSingleRect(canvas, 3, 2);
        drawSingleRect(canvas, 3, 3);
    }

    @Override
    void drawTop(Canvas canvas) {
        drawSingleRect(canvas, 0, 0);
        drawSingleRect(canvas, 1, 0);
        drawSingleRect(canvas, 2, 0);
        drawSingleRect(canvas, 3, 0);
    }

}
