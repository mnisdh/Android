# SpreadCubes

애니메이션을 사용하여 버튼 클릭시 네개의 버튼이 회전하며 위치 이동하는 기능

```java
package android.daehoshin.com.spreadcubes;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnSpread, btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButton();
        initListener();
    }

    private void initButton(){
        btnSpread = (Button) findViewById(R.id.btnSpread);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
    }

    private void initListener(){
        btnSpread.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSpread: spread(6000); break;
        }
    }

    boolean isSpread = false;
    private void spread(int duration){
        AnimatorSet aniSet = new AnimatorSet();

        if(!isSpread) {
            aniSet.playTogether(
                    ObjectAnimator.ofFloat(btn1, View.TRANSLATION_X, -200),
                    ObjectAnimator.ofFloat(btn1, View.TRANSLATION_Y, -200),
                    ObjectAnimator.ofFloat(btn1, View.ROTATION, 360 * duration / 100),

                    ObjectAnimator.ofFloat(btn2, View.TRANSLATION_X, 200),
                    ObjectAnimator.ofFloat(btn2, View.TRANSLATION_Y, -200),
                    ObjectAnimator.ofFloat(btn2, View.ROTATION, 360 * duration / 100),

                    ObjectAnimator.ofFloat(btn3, View.TRANSLATION_X, -200),
                    ObjectAnimator.ofFloat(btn3, View.TRANSLATION_Y, 200),
                    ObjectAnimator.ofFloat(btn3, View.ROTATION, 360 * duration / 100),

                    ObjectAnimator.ofFloat(btn4, View.TRANSLATION_X, 200),
                    ObjectAnimator.ofFloat(btn4, View.TRANSLATION_Y, 200),
                    ObjectAnimator.ofFloat(btn4, View.ROTATION, 360 * duration / 100)
            );
        }
        else{
            aniSet.playTogether(
                    ObjectAnimator.ofFloat(btn1, View.TRANSLATION_X, 0),
                    ObjectAnimator.ofFloat(btn1, View.TRANSLATION_Y, 0),
                    ObjectAnimator.ofFloat(btn1, View.ROTATION, 0),

                    ObjectAnimator.ofFloat(btn2, View.TRANSLATION_X, 0),
                    ObjectAnimator.ofFloat(btn2, View.TRANSLATION_Y, 0),
                    ObjectAnimator.ofFloat(btn2, View.ROTATION, 0),

                    ObjectAnimator.ofFloat(btn3, View.TRANSLATION_X, 0),
                    ObjectAnimator.ofFloat(btn3, View.TRANSLATION_Y, 0),
                    ObjectAnimator.ofFloat(btn3, View.ROTATION, 0),

                    ObjectAnimator.ofFloat(btn4, View.TRANSLATION_X, 0),
                    ObjectAnimator.ofFloat(btn4, View.TRANSLATION_Y, 0),
                    ObjectAnimator.ofFloat(btn4, View.ROTATION, 0)
            );
        }
        aniSet.setDuration(duration);
        aniSet.setInterpolator(new AccelerateDecelerateInterpolator());
        aniSet.start();

        isSpread = !isSpread;
    }

}
```
