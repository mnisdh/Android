package android.daehoshin.com.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by daeho on 2017. 9. 29..
 */

public class Score {
    iTetrisEvent iTetrisEvent;
    iTetrisAttribute iTetrisAttribute;
    int locX, locY;
    int cols, rows;

    private int score = 0;

    private Paint paintText = new Paint();
    private Paint paintBorderFill = new Paint();
    private Paint paintBorderLine = new Paint();

    public Score(iTetrisEvent iTetrisEvent, iTetrisAttribute iTetrisAttribute, int locX, int locY, int cols, int rows) {
        this.iTetrisEvent = iTetrisEvent;
        this.iTetrisAttribute = iTetrisAttribute;

        this.locX = locX;
        this.locY = locY;
        this.cols = cols;
        this.rows = rows;

        initPaint();
    }

    private void initPaint(){
        paintText.setTextSize(50);

        paintBorderLine.setColor(Color.DKGRAY);
        paintBorderLine.setStrokeWidth(1);
        paintBorderLine.setStyle(Paint.Style.STROKE);

        paintBorderFill.setColor(Color.GRAY);
        paintBorderFill.setStyle(Paint.Style.FILL);
    }

    public float getUnit(){
        return iTetrisAttribute.getUnit();
    }

    public void score_append(int appendScore){
        score += appendScore;
    }

    public void score_reset(){
        score = 0;
    }

    public void draw(Canvas canvas){
        drawScoreBox(canvas);
        drawScore(canvas);
    }

    private void drawScoreBox(Canvas canvas){
        float unit = getUnit();
        float x = locX * unit;
        float y = locY * unit;

        TetrisDraw.drawOuterBorder(canvas, 10, x, y, x + (unit * cols), y + (unit * rows), paintBorderFill, paintBorderLine);
    }

    private void drawScore(Canvas canvas){
        float unit = getUnit();
        float x = (locX + 1) * unit;
        float y = (locY + 2) * unit;

        canvas.drawText(score + "", (int)x, (int)y, paintText);
    }
}
