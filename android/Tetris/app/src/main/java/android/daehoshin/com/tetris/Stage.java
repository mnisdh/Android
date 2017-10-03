package android.daehoshin.com.tetris;

import android.daehoshin.com.tetris.blocks.Block;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by daeho on 2017. 9. 28..
 */

public class Stage {
    iTetrisEvent iTetrisEvent;
    iTetrisAttribute iTetrisAttribute;
    int locX, locY;
    int cols, rows;

    private Paint paintDummyFill = new Paint();
    private Paint paintDummyBorderFill = new Paint();
    private Paint paintDummyBorderLine = new Paint();

    private Paint paintBorderFill = new Paint();
    private Paint paintBorderLine = new Paint();

    private Block currentBlock = null;

    int[][] rects = {{9,0,0,0,0,0,0,0,0,0,0,9},
                     {9,0,0,0,0,0,0,0,0,0,0,9},
                     {9,0,0,0,0,0,0,0,0,0,0,9},
                     {9,0,0,0,0,0,0,0,0,0,0,9},
                     {9,0,0,0,0,0,0,0,0,0,0,9},
                     {9,0,0,0,0,0,0,0,0,0,0,9},
                     {9,0,0,0,0,0,0,0,0,0,0,9},
                     {9,0,0,0,0,0,0,0,0,0,0,9},
                     {9,0,0,0,0,0,0,0,0,0,0,9},
                     {9,0,0,0,0,0,0,0,0,0,0,9},
                     {9,0,0,0,0,0,0,0,0,0,0,9},
                     {9,0,0,0,0,0,0,0,1,0,0,9},
                     {9,0,0,0,0,0,0,0,1,0,0,9},
                     {9,0,0,0,0,0,0,1,0,0,0,9},
                     {9,0,0,0,0,0,1,0,0,0,0,9},
                     {9,0,0,0,1,0,0,0,0,0,0,9},
                     {9,1,0,0,0,0,0,0,0,0,1,9},
                     {9,1,0,0,0,0,0,0,0,0,1,9},
                     {9,1,0,0,0,0,0,0,0,0,1,9},
                     {9,1,1,1,0,0,0,0,0,0,1,9},
                     {9,9,9,9,9,9,9,9,9,9,9,9}};

    public Stage(iTetrisEvent iTetrisEvent, iTetrisAttribute iTetrisAttribute, int locX, int locY, int cols, int rows) {
        this.iTetrisEvent = iTetrisEvent;
        this.iTetrisAttribute = iTetrisAttribute;

        this.locX = locX;
        this.locY = locY;
        this.cols = cols;
        this.rows = rows;

        initRects();

        initPaint();
    }

    private void initRects(){
        rects = new int[rows + 1][cols + 2];
        for(int i = 0; i < rows + 1; i++){
            for(int j = 0; j < cols + 2; j++){
                if(i == rows) rects[i][j] = 9;
                else{
                    if(j == 0) rects[i][j] = 9;
                    else if(j == cols + 1) rects[i][j] = 9;
                    else rects[i][j] = 0;
                }
            }
        }
    }

    private void initPaint(){
        paintDummyFill.setColor(Color.GRAY);
        paintDummyFill.setStyle(Paint.Style.FILL);

        paintDummyBorderFill.setColor(Color.DKGRAY);
        paintDummyBorderFill.setStyle(Paint.Style.FILL);

        paintDummyBorderLine.setColor(Color.GRAY);
        paintDummyBorderLine.setStrokeWidth(1);
        paintDummyBorderLine.setStyle(Paint.Style.STROKE);

        paintBorderLine.setColor(Color.BLUE);
        paintBorderLine.setStrokeWidth(1);
        paintBorderLine.setStyle(Paint.Style.STROKE);
        //paint.setPathEffect(new DashPathEffect(new float[] { 4, 4 }, 0));

        paintBorderFill.setColor(Color.RED);
        paintBorderFill.setStyle(Paint.Style.FILL);
    }

    public float getUnit(){
        return iTetrisAttribute.getUnit();
    }

    public Block getCurrentBlock(){
        return currentBlock;
    }

    public void addBlock(Block block){
        float unit = getUnit();
        block.setLocation(locX * unit, locY * unit);
        block.move(4, 0);

        currentBlock = block;
    }

    public void draw(Canvas canvas){
        drawStage(canvas);
        drawDummy(canvas);
        drawBlock(canvas);
    }

    private void drawBlock(Canvas canvas){
        if(currentBlock != null) currentBlock.draw(canvas);
    }

    private void drawStage(Canvas canvas){
        float unit = getUnit();
        Path path = new Path();
        float x = locX * unit;
        float y = locY * unit;

        TetrisDraw.drawOuterBorder(canvas, 10, x, y, x + (unit * cols), y + (unit * rows), paintBorderFill, paintBorderLine);
    }

    private void drawDummy(Canvas canvas){
        float unit = getUnit();
        float x = locX * unit;
        float y = locY * unit;

        for(int i = 0; i < rects.length - 1; i++){
            for(int j = 1; j < rects[0].length - 1; j++){
                if(rects[i][j] != 0) {
                    float minX = x + ((j - 1) * unit);
                    float minY = y + (i * unit);
                    float maxX = x + ((j) * unit);
                    float maxY = y + ((i + 1) * unit);
                    canvas.drawRect(minX, minY, maxX, maxY, paintDummyFill);
                    TetrisDraw.drawInnerBorder(canvas, (int)(unit / 8), minX, minY, maxX, maxY, paintDummyBorderFill, paintDummyBorderLine);
                }
            }
        }
    }

    private boolean isClash(Block block){
        int[][] bRects = block.getRects();
        float unit = getUnit();
        int bLocX = (int)(block.getX() / unit - locX + 1);
        int bLocY = (int)(block.getY() / unit - locY);

        for(int i = 0; i < bRects.length; i++){
            if(rects.length <= i + bLocY) continue;
            for(int j = 0; j < bRects[0].length; j++){
                if(rects[0].length <= j + bLocX) continue;

                int temp = bRects[i][j];
                if(temp == 0) continue;

                if(rects[i + bLocY][j + bLocX] + temp != temp) return true;
            }
        }

        return false;
    }

    private void addDummy(Block block){
        int[][] bRects = block.getRects();
        float unit = getUnit();
        int bLocX = (int)(block.getX() / unit - locX + 1);
        int bLocY = (int)(block.getY() / unit - locY);

        for(int i = 0; i < bRects.length; i++){
            if(rects.length <= i + bLocY) continue;
            for(int j = 0; j < bRects[0].length; j++){
                if(rects[0].length <= j + bLocX) continue;

                int temp = bRects[i][j];
                if(temp == 0) continue;

                rects[i + bLocY][j + bLocX] = 9;
            }
        }
    }

    private int getCompletedRow(){
        for(int i = rects.length - 2; i >= 0; i--){
            boolean isAllSame = true;
            for(int j = 0; j < rects[0].length; j++){
                if(rects[i][j] != 9) isAllSame = false;
            }
            if(isAllSame) return i;
        }

        return -1;
    }

    private void removeCompletedRow(int index){
        for(int i = rects.length - 2; i >= 0; i--){
            if(i >= index) continue;
            for(int j = 0; j < rects[0].length; j++){
                rects[i + 1][j] = rects[i][j];
            }
        }
    }

    public void blockRotation(){
        if(currentBlock == null) return;

        currentBlock.rotation(false);
        if(isClash(currentBlock)){
            blockMoveLeft();
            if(isClash(currentBlock)){
                blockMoveRight();
                if(isClash(currentBlock)){
                    currentBlock.rotation(true);
                }
            }
        }

        iTetrisEvent.redraw();
    }

    public void blockMoveLeft(){
        if(currentBlock == null) return;

        currentBlock.moveLeft();
        if(isClash(currentBlock)) currentBlock.moveRight();
        else iTetrisEvent.redraw();
    }
    public void blockMoveRight(){
        if(currentBlock == null) return;

        currentBlock.moveRight();
        if(isClash(currentBlock)) currentBlock.moveLeft();
        else iTetrisEvent.redraw();
    }

    private boolean isDowning = false;
    public void blockMoveDown(){
        if(currentBlock == null) return;
        if(isDowning) return;

        isDowning = true;
        currentBlock.moveDown();
        if(isClash(currentBlock)) {
            currentBlock.moveUp();
            addDummy(currentBlock);
            iTetrisEvent.postRedraw();

            int idx = getCompletedRow();
            while (idx >= 0){
                removeCompletedRow(idx);
                iTetrisEvent.postRedraw();
                idx = getCompletedRow();
            }

            iTetrisEvent.addBlock();
        }
        iTetrisEvent.postRedraw();

        isDowning = false;
    }
}
