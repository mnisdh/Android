package android.daehoshin.com.coustomview;

import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

public class DrawActivity extends AppCompatActivity {
    FrameLayout stage;
    DrawView dv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        stage = (FrameLayout) findViewById(R.id.stage);
        dv = new DrawView(this);
        stage.addView(dv);

        RadioGroup rgColor =(RadioGroup) findViewById(R.id.rgColor);
        rgColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rbtBlack:
                        dv.setColor(Color.BLACK);
                        break;
                    case R.id.rbtCyan:
                        dv.setColor(Color.CYAN);
                        break;
                    case R.id.rbtMagenta:
                        dv.setColor(Color.MAGENTA);
                        break;
                    case R.id.rbtYellow:
                        dv.setColor(Color.YELLOW);
                        break;
                }
            }
        });
    }
}
