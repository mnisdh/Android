package android.daehoshin.com.raindrop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static android.daehoshin.com.raindrop.MainActivity.runFlag;

/**
 * Created by daeho on 2017. 10. 10..
 */

public class CustomView extends View {
    Paint p;
    List<RainDrop> rainDrops = new ArrayList<>();

    public CustomView(Context context) {
        super(context);

        init();
    }

    private void init(){
        // Paint 설정
        p = new Paint();
        p.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i = rainDrops.size() - 1; i >= 0; i--){
            RainDrop rainDrop = rainDrops.get(i);
            if(rainDrop.y >= rainDrop.limit){
                rainDrops.remove(i);
                continue;
            }

            p.setColor(rainDrop.color);
            canvas.drawCircle(rainDrop.x, rainDrop.y += rainDrop.speed, rainDrop.size, p);
        }
    }

    public void add(RainDrop rd){
        rainDrops.add(rd);
    }

    public void run(){
        new Thread(){
            public void run(){
                while (runFlag){
                    postInvalidate();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

}
