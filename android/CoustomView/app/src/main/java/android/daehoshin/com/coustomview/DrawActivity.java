package android.daehoshin.com.coustomview;

import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;

public class DrawActivity extends AppCompatActivity {
    FrameLayout stage;
    DrawView dv;
    SeekBar sbSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        stage = (FrameLayout) findViewById(R.id.stage);
        dv = new DrawView(this);
        stage.addView(dv);

        sbSize = (SeekBar)findViewById(R.id.sbSize);
        sbSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dv.setSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        RadioGroup rgColor =(RadioGroup) findViewById(R.id.rgColor);
        rgColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rbtBlack:
                        dv.setColor(Color.BLACK, sbSize.getProgress());
                        break;
                    case R.id.rbtCyan:
                        dv.setColor(Color.CYAN, sbSize.getProgress());
                        break;
                    case R.id.rbtMagenta:
                        dv.setColor(Color.MAGENTA, sbSize.getProgress());
                        break;
                    case R.id.rbtYellow:
                        dv.setColor(Color.YELLOW, sbSize.getProgress());
                        break;
                }
            }
        });
    }
}
