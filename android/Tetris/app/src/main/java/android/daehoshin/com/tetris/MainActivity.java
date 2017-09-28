package android.daehoshin.com.tetris;

import android.daehoshin.com.tetris.blocks.Block;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    FrameLayout flStage;
    Stage stage;
    float unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(!stage.getUseSetSize()) {
            stage.setSize(flStage.getWidth(), flStage.getHeight(), 10);
            unit = stage.getUnit();
            stage.invalidate();
        }
    }

    private void init(){
        flStage = (FrameLayout) findViewById(R.id.stage);

//        DisplayMetrics metrics = getResources().getDisplayMetrics();
//        int height = metrics.heightPixels;
//        int width = metrics.widthPixels;

        stage = new Stage(this);
        flStage.addView(stage);
    }

    public void add(View v){
        stage.addBlock();
    }

    public void rotation(View v){
        stage.getCurrentBlock().rotation();
        stage.invalidate();
    }

    public void move(View v){
        Block block = stage.getCurrentBlock();
        if(block == null) return;

        switch (v.getId()){
            case R.id.btnUp: block.moveUp(); break;
            case R.id.btnDown: block.moveDown(); break;
            case R.id.btnLeft: block.moveLeft(); break;
            case R.id.btnRight: block.moveRight(); break;
        }
        stage.invalidate();
    }
}
