package android.daehoshin.com.tetris;

import android.daehoshin.com.tetris.blocks.Block;
import android.daehoshin.com.tetris.blocks.IBlock;
import android.daehoshin.com.tetris.blocks.JBlock;
import android.daehoshin.com.tetris.blocks.LBlock;
import android.daehoshin.com.tetris.blocks.OBlock;
import android.daehoshin.com.tetris.blocks.SBlock;
import android.daehoshin.com.tetris.blocks.TBlock;
import android.daehoshin.com.tetris.blocks.ZBlock;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.Random;

/**
 * Created by daeho on 2017. 9. 29..
 */

public class Preview {
    iTetrisEvent iTetrisEvent;
    iTetrisAttribute iTetrisAttribute;
    int locX, locY;
    int cols, rows;

    private Paint paintBorderFill = new Paint();
    private Paint paintBorderLine = new Paint();

    private Block currentBlock = null;

    public Preview(iTetrisEvent iTetrisEvent, iTetrisAttribute iTetrisAttribute, int locX, int locY, int cols, int rows) {
        this.iTetrisEvent = iTetrisEvent;
        this.iTetrisAttribute = iTetrisAttribute;

        this.locX = locX;
        this.locY = locY;
        this.cols = cols;
        this.rows = rows;

        initPaint();

        currentBlock = createBlock();
    }

    private void initPaint(){
        paintBorderLine.setColor(Color.RED);
        paintBorderLine.setStrokeWidth(1);
        paintBorderLine.setStyle(Paint.Style.STROKE);

        paintBorderFill.setColor(Color.BLUE);
        paintBorderFill.setStyle(Paint.Style.FILL);
    }

    public float getUnit(){
        return iTetrisAttribute.getUnit();
    }

    public Block getCurrentBlock(){
        Block result = currentBlock;

        currentBlock = createBlock();

        return result;
    }

    public Block createBlock(){
        Block block = null;

        float unit = getUnit();
        float x = (locX + 1) * unit;
        float y = (locY + 2) * unit;

        Random random = new Random();
        switch (random.nextInt(7)){
            case 0: block = new IBlock(x, y, unit); break;
            case 1: block = new JBlock(x, y, unit); break;
            case 2: block = new LBlock(x, y, unit); break;
            case 3: block = new OBlock(x, y, unit); break;
            case 4: block = new SBlock(x, y, unit); break;
            case 5: block = new TBlock(x, y, unit); break;
            case 6: block = new ZBlock(x, y, unit); break;
        }

        return block;
    }

    public void draw(Canvas canvas){
        drawPreview(canvas);
        drawBlock(canvas);
    }

    private void drawBlock(Canvas canvas){
        currentBlock.draw(canvas);
    }

    private void drawPreview(Canvas canvas){
        float unit = getUnit();
        Path path = new Path();
        float x = locX * unit;
        float y = locY * unit;

//        path.moveTo(x, y);
//        path.lineTo(x + (unit * cols), y);
//        path.lineTo(x + (unit * cols), y + (unit * rows));
//        path.lineTo(x, y + (unit * rows));
//        path.close();
//
//        canvas.drawPath(path, paint);

        TetrisDraw.drawOuterBorder(canvas, 10, x, y, x + (unit * cols), y + (unit * rows), paintBorderFill, paintBorderLine);
    }
}
