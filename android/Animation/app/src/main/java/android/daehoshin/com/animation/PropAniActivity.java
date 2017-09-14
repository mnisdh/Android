package android.daehoshin.com.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

public class PropAniActivity extends AppCompatActivity {
    Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prop_ani);

        btnGo = (Button) findViewById(R.id.btnGo);
    }

    private int y = 0;
    private int x = 0;
    public void move1(View v){
        // 1. 대상을 정의한다 - btnGo
        // 2. 애니메이터를 설정한다
        y += 10;
        ObjectAnimator ani = ObjectAnimator.ofFloat(
                btnGo,          // 움직을 대상
                "translationY", // 애니메이션 속성
                y             // 속성값
                );
        // 3. 애니메이터를 실행한다
        ani.start();
    }

    public void move(View v){
        // 복합애니메이션
        y += 10;
        ObjectAnimator ani = ObjectAnimator.ofFloat(
                btnGo,          // 움직을 대상
                "translationY", // 애니메이션 속성
                y             // 속성값
        );
        x += 10;
        ObjectAnimator anX = ObjectAnimator.ofFloat(
                btnGo,          // 움직을 대상
                "translationX", // 애니메이션 속성
                x             // 속성값
        );

        //애니메이션 셋에 담아서 동시에 실행 할 수 있다
        AnimatorSet aniSet = new AnimatorSet();
        aniSet.playTogether(ani,anX);
        aniSet.setDuration(3000);

        aniSet.setInterpolator(new LinearInterpolator());
        // LinearInterpolator : 일정한 속도를 유지
        // AccelerateInterpolator : 점점빠르게
        // DecelerateInterpolator : 점점느리게
        // AccelerateInterpolator : 위 둘을 동시에
        // anticipateInterpolator : 시작위치에서 조금 뒤로 당겼다 이동
        // OvershootInterpolator : 도착위치를 조금 지나쳤다가 도착위치로 이동
        // AnticipateOvershootInterpolator : 위둘을 동시에
        // BounceInterpolator : 도착위치에서 튕김
        aniSet.start();
    }

    public void goJoystick(View v){
        Intent intent = new Intent(this, JoystickActivity.class);
        startActivity(intent);
    }
}
