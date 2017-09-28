package android.daehoshin.com.tetris;

import android.content.Context;
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
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by daeho on 2017. 9. 28..
 */

public class Stage extends View {
    private float unit;
    private int widthCount, heightCount;
    private float width, height;
    private Paint paint = new Paint();

    private Block currentBlock = null;

    private boolean useSetSize = false;

    List<Block> blocks = new ArrayList<>();

    public Stage(Context context) {
        super(context);

        initPaint();
    }

    public void setSize(float stage_width, float stage_height, float col_count){
        useSetSize = true;
        col_count++;

        stage_width -= 10;
        stage_height -= 10;

        float min = stage_width;
        if(min > stage_height) min = stage_height;
        this.unit = min / col_count;

        this.width = stage_width - (stage_width % this.unit);
        this.height = stage_height - (stage_height % this.unit);

        this.widthCount = (int)(this.width / this.unit);
        this.heightCount = (int)(this.height / this.unit);
    }
    public boolean getUseSetSize(){
        return useSetSize;
    }

    private void initPaint(){
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[] { 4, 4 }, 0));
    }

    public float getUnit(){
        return unit;
    }

    public Block getCurrentBlock(){
        return currentBlock;
    }

    public void addBlock(Block block){
        blocks.add(block);
        currentBlock = block;

        invalidate();
    }

    public void addBlock(){
        Block block = null;

        Random random = new Random();
        switch (random.nextInt(7)){
            case 0: block = new IBlock(0, 0, unit); break;
            case 1: block = new JBlock(0, 0, unit); break;
            case 2: block = new LBlock(0, 0, unit); break;
            case 3: block = new OBlock(0, 0, unit); break;
            case 4: block = new SBlock(0, 0, unit); break;
            case 5: block = new TBlock(0, 0, unit); break;
            case 6: block = new ZBlock(0, 0, unit); break;
        }

        if(block != null) addBlock(block);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(!useSetSize) return;

        drawStage(canvas);
        drawBlocks(canvas);
    }

    private void drawBlocks(Canvas canvas){
        for(Block block : blocks) block.draw(canvas);
    }

    private void drawStage(Canvas canvas){
        Path path = new Path();

        float tempX = 0;
        while (tempX <= width){
            //canvas.drawLine(tempX, 0, tempX, height, paint);
            path.moveTo(tempX, 0);
            path.lineTo(tempX, height);
            canvas.drawPath(path, paint);
            tempX += unit;
        }

        float tempY = 0;
        while (tempY <= height){
            //canvas.drawLine(0, tempY, width, tempY, paint);
            path.moveTo(0, tempY);
            path.lineTo(width, tempY);
            canvas.drawPath(path, paint);
            tempY += unit;
        }
    }
}
