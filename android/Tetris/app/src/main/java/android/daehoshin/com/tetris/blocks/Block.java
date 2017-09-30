package android.daehoshin.com.tetris.blocks;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;

/**
 * Created by daeho on 2017. 9. 28..
 */

public abstract class Block {
    BlockType blockType = BlockType.UNKNOWN;

    float x, y, unit;
    int rot = 0;
    Paint paint = new Paint();
    Paint innerBorderPaint = new Paint();
    Paint innerFillPaint = new Paint();
    int[][] rects;

    public Block(float x, float y, float unit){
        this.x = x;
        this.y = y;
        this.unit = unit;

        innerBorderPaint.setColor(Color.LTGRAY);
        innerBorderPaint.setStrokeWidth(2);
        innerBorderPaint.setStyle(Paint.Style.STROKE);

        innerFillPaint.setColor(Color.LTGRAY);
        innerFillPaint.setStyle(Paint.Style.FILL);

        initStyle();
    }

    abstract void initStyle();
    public void draw(Canvas canvas){
        setRects(rot);

        drawRects(canvas, rects);
    }
    abstract void setRects(int type);
    public int[][] getRects(){
        setRects(rot);
        return rects;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    public BlockType getBlockType(){
        return blockType;
    }

    private void drawSingleRect(Canvas canvas, int startX, int startY){
        float minX = unit * startX + x;
        float minY = unit * startY + y;
        float maxX = unit * (startX + 1) + x;
        float maxY = unit * (startY + 1) + y;

        canvas.drawRect(minX, minY, maxX, maxY, paint);

        drawInnerBorder(canvas, minX, minY, maxX, maxY);
    }
    private void drawRects(Canvas canvas, int[][] rects){
        for(int i = 0; i < rects.length; i++){
            for(int j = 0; j < rects[i].length; j++){
                if(rects[i][j] != 0) drawSingleRect(canvas, j, i);
            }
        }
    }

    private void drawInnerBorder(Canvas canvas, float minX, float minY, float maxX, float maxY){
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

        innerFillPaint.setShader(new LinearGradient(minX - temp, minY - temp, minX + temp, maxY, Color.WHITE, innerFillPaint.getColor(), Shader.TileMode.CLAMP));
        canvas.drawPath(path, innerFillPaint);
        canvas.drawPath(path, innerBorderPaint);
    }
    private void drawInnerBorderBottom(Canvas canvas, float minX, float minY, float maxX, float maxY){
        float temp = unit / 8;

        Path path = new Path();
        path.moveTo(minX, maxY);
        path.lineTo(minX + temp, maxY - temp);
        path.lineTo(maxX - temp, maxY - temp);
        path.lineTo(maxX, maxY);
        path.close();

        innerFillPaint.setShader(new LinearGradient(minX - temp, maxY - temp * 2, maxX, maxY, Color.GRAY, innerFillPaint.getColor(), Shader.TileMode.CLAMP));
        canvas.drawPath(path, innerFillPaint);
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

        innerFillPaint.setShader(new LinearGradient(maxX - temp * 2, minY - temp, maxX, maxY, Color.GRAY, innerFillPaint.getColor(), Shader.TileMode.CLAMP));
        canvas.drawPath(path, innerFillPaint);
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

        innerFillPaint.setShader(new LinearGradient(minX - temp, minY - temp, maxX, minY + temp, Color.WHITE, innerFillPaint.getColor(), Shader.TileMode.CLAMP));
        canvas.drawPath(path, innerFillPaint);
        canvas.drawPath(path, innerBorderPaint);
    }

    public void moveUp(){ y -= unit; }
    public void moveDown(){ y += unit; }
    public void moveLeft(){ x -= unit; }
    public void moveRight(){ x += unit; }

    public void move(int x, int y){
        this.x += x * unit;
        this.y += y * unit;
    }

    public void setLocation(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void rotation(){
        if(rot == 3) rot = 0;
        else rot++;
    }
}
