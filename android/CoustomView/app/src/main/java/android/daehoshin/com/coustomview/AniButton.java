package android.daehoshin.com.coustomview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 *
 * animation 속성이 true일 경우
 *
 * scale 애니메이션을 사용해서
 * 클릭시 살짝 커졌다 작아지는 애니메이션 동작
 *
 * Created by daeho on 2017. 9. 18..
 */

public class AniButton extends AppCompatButton {
    private boolean useAnimation = false;
    public boolean getUseAnimation(){
        return useAnimation;
    }
    public void setUseAnimation(boolean use){
        useAnimation = use;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(useAnimation) runScaleAni(v);
        }
    };

    View.OnTouchListener onTouchListener = new View.OnTouchListener(){

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    runScaleAni(v);
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }

            return false;
        }
    };

    private void runScaleAni(View v){
        ObjectAnimator aniX = ObjectAnimator.ofFloat(v, View.SCALE_X, 1.1f, 1.0f);
        ObjectAnimator aniY = ObjectAnimator.ofFloat(v, View.SCALE_Y, 1.1f, 1.0f);

        //aniX.setRepeatCount(1);
        //aniX.setRepeatMode(ObjectAnimator.REVERSE);
        //aniY.setRepeatCount(1);
        //aniY.setRepeatMode(ObjectAnimator.REVERSE);

        AnimatorSet aniSet = new AnimatorSet();
        aniSet.playTogether(aniX,aniY);
        aniSet.setDuration(100);

        aniSet.start();
    }

    public AniButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        //this.setOnClickListener(onClickListener);
        this.setOnTouchListener(onTouchListener);

        // 1. attrs.xml 에 정의된 속성을 가져온다
        TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.AniButton);

        // 2. 해당 이름으로 정의된 속성의 개수를 가져온다
        int size = typed.getIndexCount();
        Log.d("AniButton", "size=" + size);

        // 3. 반복문을 돌면서 해당 속성에 대한 처리를 해준다
        for(int i = 0; i < size; i++){
            // 3.1 현재 배열에 있는 속성 아이디 가져오기
            int current_attr = typed.getIndex(i);

            switch (current_attr){
                case R.styleable.AniButton_use_animation:
                    if("true".equals(typed.getString(current_attr).toLowerCase())) setUseAnimation(true);
                    else setUseAnimation(false);

                    break;
            }
        }
    }
}
