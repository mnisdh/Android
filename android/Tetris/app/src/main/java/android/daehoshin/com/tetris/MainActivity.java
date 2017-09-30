package android.daehoshin.com.tetris;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    FrameLayout flStage;
    Tetris tetris;
    float unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        tetris.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        tetris.stop();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
//        if(!flStage.getUseSetSize()) {
//            tetris.setSize(flStage.getWidth(), flStage.getHeight());
//            unit = tetris.getUnit();
//            tetris.invalidate();
//        }
    }

    private void init(){
        flStage = (FrameLayout) findViewById(R.id.stage);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        tetris = new Tetris(this, width, height);
        flStage.addView(tetris);
    }

    public void add(View v){
        tetris.start();
    }

    public void rotation(View v){
        tetris.rotation();
    }

    public void move(View v){
        switch (v.getId()){
            case R.id.btnDown: tetris.down(); break;
            case R.id.btnLeft: tetris.left(); break;
            case R.id.btnRight: tetris.right(); break;
        }
    }
}
