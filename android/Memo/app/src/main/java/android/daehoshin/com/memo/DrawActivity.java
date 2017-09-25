package android.daehoshin.com.memo;

import android.content.Intent;
import android.daehoshin.com.memo.domain.MemoDAO;
import android.daehoshin.com.memo.draw.DrawView;
import android.daehoshin.com.memo.util.FileUtil;
import android.daehoshin.com.memo.util.TypeUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class DrawActivity extends AppCompatActivity {
    private MemoDAO dao;

    FrameLayout stage;
    DrawView dv;
    SeekBar sbSize;
    EditText etTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        init();
    }

    private void init(){
        dao = new MemoDAO(this);

        // drawview를 추가할 레이아웃 설정
        stage = (FrameLayout) findViewById(R.id.stage);

        // drawview를 생성해서 레이아웃에 추가
        dv = new DrawView(this);
        stage.addView(dv);

        etTitle = (EditText) findViewById(R.id.etTitle);

        // 사이즈 설정을 위한 seekbar 설정 / 리스너 연결
        sbSize = (SeekBar)findViewById(R.id.sbSize);
        sbSize.setOnSeekBarChangeListener(seekBarChangeListener);

        // 컬러 설정을 위한 라디오그룹 설정 / 리스너 연결
        RadioGroup rgColor =(RadioGroup) findViewById(R.id.rgColor);
        rgColor.setOnCheckedChangeListener(radioGroupCheckedChangeListener);
    }


    public void clear(View v){
        dv.clear();
    }

    /**
     * 그림을 그린 stage를 캡쳐
     *
     * @param v
     */
    public void captureCanvas(View v){
        // 0. 드로잉 캐쉬를 먼저 지워준다
        stage.destroyDrawingCache();

        // 1. 다시 만든다
        stage.buildDrawingCache();

        // 2. 레이아웃에서 그려진 내용을 bitmap 형식으로 가져옴
        Bitmap bitmap = stage.getDrawingCache();

        String fileName = System.currentTimeMillis() + ".png";
        // 이미지 파일을 저장하고
        try {
            // /data/data/패키지/files 안에 저장
            FileUtil.save(MemoDAO.getImagePath(), fileName, TypeUtil.toByteArray(bitmap));
        } catch (IOException e) {
            Toast.makeText(this, "저장실패", Toast.LENGTH_LONG).show();
        }
        finally {
            // Native에 다 썻다고 알려준다
            bitmap.recycle();
        }

        Intent intent = new Intent();
        intent.putExtra("fileName", fileName);
        setResult(RESULT_OK, intent);

        finish();
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
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
    };

    RadioGroup.OnCheckedChangeListener radioGroupCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
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
    };



}
