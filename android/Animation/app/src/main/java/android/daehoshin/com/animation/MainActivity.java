package android.daehoshin.com.animation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnMove, btnRotate, btnScale, btnAlpha, btnObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVew();
        initListener();
    }

    private void initVew(){
        btnMove = (Button) findViewById(R.id.btnMove);
        btnRotate = (Button) findViewById(R.id.btnRotate);
        btnScale = (Button) findViewById(R.id.btnScale);
        btnAlpha = (Button) findViewById(R.id.btnAlpha);
        btnObject = (Button) findViewById(R.id.btnObject);
    }

    private void initListener(){
        btnMove.setOnClickListener(this);
        btnRotate.setOnClickListener(this);
        btnScale.setOnClickListener(this);
        btnAlpha.setOnClickListener(this);
        btnObject.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMove: move(); break;
            case R.id.btnRotate: rotate(); break;
            case R.id.btnScale: scale(); break;
            case R.id.btnAlpha: alpha(); break;
            case R.id.btnObject:
                Intent intent = new Intent(this, PropAniActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void move(){
        // view 애니메이션 실행
        // 1. 애니메이션 xml 정의
        // 2. AnimationUtil로 정의된 애니메이션을 로드
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.move);
        // 3. 로드된 애니메이션을 실제 위젯에 적용
        btnObject.startAnimation(animation);
    }

    private void rotate(){
        startAnimation(btnObject, R.anim.rotate);
    }

    private void scale(){
        startAnimation(btnObject, R.anim.scale);
    }

    private void alpha(){
        startAnimation(btnObject, R.anim.alpha);
    }

    private void startAnimation(Button btn, int animID){
        Animation animation = AnimationUtils.loadAnimation(this, animID);
        btn.startAnimation(animation);
    }
}
