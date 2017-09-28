package android.daehoshin.com.tetris.blocks;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by daeho on 2017. 9. 28..
 */

public abstract class Block {
    float x, y, unit;
    int rot = 0;
    Paint paint = new Paint();
    Paint innerBorderPaint = new Paint();

    public Block(float x, float y, float unit){
        this.x = x;
        this.y = y;
        this.unit = unit;

        innerBorderPaint.setColor(Color.LTGRAY);
        innerBorderPaint.setStrokeWidth(0.1f);
        innerBorderPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        initStyle();
    }

    abstract void initStyle();
    public void draw(Canvas canvas){
        switch (rot){
            case 1: drawBottom(canvas); break;
            case 2: drawRight(canvas); break;
            case 3: drawTop(canvas); break;
            default: drawLeft(canvas); break;
        }
    }
    abstract void drawLeft(Canvas canvas);
    abstract void drawBottom(Canvas canvas);
    abstract void drawRight(Canvas canvas);
    abstract void drawTop(Canvas canvas);

    protected void drawSingleRect(Canvas canvas, int startX, int startY){
        float minX = unit * startX + x;
        float minY = unit * startY + y;
        float maxX = unit * (startX + 1) + x;
        float maxY = unit * (startY + 1) + y;

        canvas.drawRect(minX, minY, maxX, maxY, paint);
        drawInnerBorderLeft(canvas, minX, minY, maxX, maxY);
        drawInnerBorderBottom(canvas, minX, minY, maxX, maxY);
        drawInnerBorderRight(canvas, minX, minY, maxX, maxY);
        drawInnerBorderTop(canvas, minX, minY, maxX, maxY);
    }

    private void drawInnerBorderLeft(Canvas canvas, float minX, float minY, float maxX, float maxY){
        float temp = unit / 8;

        Path path = new Path();
        path.moveTo(minX, minY);
        path.lineTo(minX + temp, minY + temp);
        path.lineTo(minX + temp, maxY - temp);
        path.lineTo(minX, maxY);
        path.close();

        canvas.drawPath(path, innerBorderPaint);
    }
    private void drawInnerBorderBottom(Canvas canvas, float minX, float minY, float maxX, float maxY){
        float temp = unit / 8;

        Path path = new Path();
        path.moveTo(minX, maxY);
        path.lineTo(minX - temp, maxY - temp);
        path.lineTo(maxX - temp, maxY - temp);
        path.lineTo(maxX, maxY);
        path.close();

        canvas.drawPath(path, innerBorderPaint);
    }
    private void drawInnerBorderRight(Canvas canvas, float minX, float minY, float maxX, float maxY){
        float temp = unit / 8;

        Path path = new Path();
        path.moveTo(maxX, minY);
        path.lineTo(maxX - temp, minY + temp);
        path.lineTo(maxX - temp, maxY - temp);
        path.lineTo(maxX, maxY);
        path.close();

        canvas.drawPath(path, innerBorderPaint);
    }
    private void drawInnerBorderTop(Canvas canvas, float minX, float minY, float maxX, float maxY){
        float temp = unit / 8;

        Path path = new Path();
        path.moveTo(minX, minY);
        path.lineTo(minX + temp, minY + temp);
        path.lineTo(maxX - temp, minY + temp);
        path.lineTo(maxX, minY);
        path.close();

        canvas.drawPath(path, innerBorderPaint);
    }

    public void moveUp(){ y -= unit; }
    public void moveDown(){ y += unit; }
    public void moveLeft(){ x -= unit; }
    public void moveRight(){ x += unit; }

    public void rotation(){
        if(rot == 3) rot = 0;
        else rot++;
    }
}
