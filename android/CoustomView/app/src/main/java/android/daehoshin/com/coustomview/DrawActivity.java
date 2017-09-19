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

        init();
    }

    private void init(){
        // drawview를 추가할 레이아웃 설정
        stage = (FrameLayout) findViewById(R.id.stage);

        // drawview를 생성해서 레이아웃에 추가
        dv = new DrawView(this);
        stage.addView(dv);

        // 사이즈 설정을 위한 seekbar 설정 / 리스너 연결
        sbSize = (SeekBar)findViewById(R.id.sbSize);
        sbSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 사이즈 변경 메소드 호출
                dv.setSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // 컬러 설정을 위한 라디오그룹 설정 / 리스너 연결
        RadioGroup rgColor =(RadioGroup) findViewById(R.id.rgColor);
        rgColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int color = Color.BLACK;
                int size = sbSize.getProgress();

                switch (checkedId){
                    case R.id.rbtBlack: color = Color.BLACK; break;
                    case R.id.rbtCyan: color = Color.CYAN; break;
                    case R.id.rbtMagenta: color = Color.MAGENTA; break;
                    case R.id.rbtYellow: color = Color.YELLOW; break;
                }

                // 컬러 변경 메소드 호출
                dv.setColor(color, size);
            }
        });
    }
}
