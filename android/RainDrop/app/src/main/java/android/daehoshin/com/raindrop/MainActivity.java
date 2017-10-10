package android.daehoshin.com.raindrop;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    FrameLayout stage;
    CustomView cv;
    public static boolean runFlag = true;

    int width = 0;
    int height = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onDestroy() {
        runFlag = false;

        super.onDestroy();
    }

    private void init(){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        stage = (FrameLayout) findViewById(R.id.stage);
        cv = new CustomView(this);
        stage.addView(cv);

        Switch swDrop =(Switch) findViewById(R.id.swDrop);
        swDrop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    runFlag = true;
                    cv.run();
                    runRainDrop();
                }
                else {
                    runFlag = false;
                }
            }
        });
    }

    public void runRainDrop(){
        new Thread(){
            public void run(){
                while (runFlag){
                    addRainDrop();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void addRainDrop(){
        Random r = new Random();
        cv.add(new RainDrop(r.nextInt(width), 0, r.nextInt(10) + 1, r.nextInt(25) + 10, Color.BLUE, height));
    }
}

class RainDrop{
    // 속성
    float x;
    float y;
    float speed;
    float size;
    int color;

    boolean runFlag = true;

    // 생명주기 : 바닥에 닿을때 까지
    float limit;

    public RainDrop(float x, float y, float speed, float size, int color, float limit){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
        this.color = color;

        this.limit = limit;
    }
}

//class RainDrop extends Thread{
//    // 속성
//    float x;
//    float y;
//    float speed;
//    float size;
//    int color;
//
//    boolean runFlag = true;
//
//    // 생명주기 : 바닥에 닿을때 까지
//    float limit;
//
//    // 기능
//    public void run(){
//        runFlag = true;
//        while (y < limit && runFlag) {
//            y += speed;
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public RainDrop(float x, float y, float speed, float size, int color, float limit){
//        this.x = x;
//        this.y = y;
//        this.speed = speed;
//        this.size = size;
//        this.color = color;
//
//        this.limit = limit;
//    }
//}