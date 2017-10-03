package android.daehoshin.com.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by daeho on 2017. 9. 29..
 */

public class Tetris extends View implements iTetrisEvent, iTetrisAttribute{
    private Stage stage;
    private Preview preview;

    private boolean RUNNING = true;

    private float unit;
    private int widthCount = 22;
    private int heightCount = 22;
    private float width, height;

    public Tetris(Context context, float width, float height) {
        super(context);

        this.unit = width / 22;

        this.width = this.unit * this.widthCount;
        this.height = this.unit * this.heightCount;

        stage = new Stage(this, this, 2, 3, 10, 20);
        preview = new Preview(this, this, 14, 3, 6, 6);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        stage.draw(canvas);
        preview.draw(canvas);
    }

    @Override
    public void redraw() {
        invalidate();
    }

    @Override
    public void postRedraw() {
        postInvalidate();
    }

    @Override
    public void addBlock() {
        stage.addBlock(preview.getCurrentBlock());
    }

    public void start(){
        RUNNING = true;

        if(stage.getCurrentBlock() == null) addBlock();

        new Thread(){
            public void run(){
                while(RUNNING) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    down();
                }
            }
        }.start();
    }

    public void stop(){
        RUNNING = false;
    }

    public void rotation(){
        stage.blockRotation();
    }
    public void left(){
        stage.blockMoveLeft();
    }
    public void right(){
        stage.blockMoveRight();
    }
    public void down(){
        stage.blockMoveDown();
    }

    @Override
    public float getUnit() {
        return unit;
    }
}

interface iTetrisEvent{
    void redraw();
    void postRedraw();
    void addBlock();
}

interface iTetrisAttribute{
    float getUnit();

}
