# Custom View

## MainActivity class

```java
package android.daehoshin.com.coustomview;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * 커스텀뷰 만들기
 *
 * 1. 커스텀 속성을 attrs.xml 파일에 정의
 *
 * 2. 커스텀할 객체(위젯)를 상속받은 후 재정의
 *
 * 3. 커스텀한 위젯을 레이아웃.xml에서 태그로 사용
 *
 */
public class MainActivity extends AppCompatActivity {
    ConstraintLayout stage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // view를 추가할 레이아웃 설정
        stage = (ConstraintLayout)findViewById(R.id.stage);

        // 커스텀뷰 생성
        CustomView cv = new CustomView(this);
        // 레이아웃에 커스텀뷰 추가
        stage.addView(cv);

        findViewById(R.id.btnDraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DrawActivity.class);
                startActivity(intent);
            }
        });
    }
}
```


## AniButton class

```java
package android.daehoshin.com.coustomview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
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
    // 애니메이션 사용여부를 체크하는 변수
    private boolean useAnimation = false;
    public boolean getUseAnimation(){
        return useAnimation;
    }
    public void setUseAnimation(boolean use){
        useAnimation = use;
    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener(){
        /**
         * 터치 다운시 애니메이션 동작
         * @param v
         * @param event
         * @return
         */
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

    /**
     * 크기를 커졌다 원사이즈로 돌아오는 애니메이션 동작
     * @param v
     */
    private void runScaleAni(View v){
        ObjectAnimator aniX = ObjectAnimator.ofFloat(v, View.SCALE_X, 1.1f, 1.0f);
        ObjectAnimator aniY = ObjectAnimator.ofFloat(v, View.SCALE_Y, 1.1f, 1.0f);

        AnimatorSet aniSet = new AnimatorSet();
        aniSet.playTogether(aniX,aniY);
        aniSet.setDuration(100);

        aniSet.start();
    }

    /**
     * 생성자
     * @param context
     * @param attrs
     */
    public AniButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 세밀한 이벤트 동작처리를 위해 onClickListener 대신 onTouchListener로 사용
        this.setOnTouchListener(onTouchListener);

        // 1. attrs.xml 에 정의된 속성을 가져온다
        TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.AniButton);

        // 2. 해당 이름으로 정의된 속성의 개수를 가져온다
        int size = typed.getIndexCount();

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
```


## CustomView class

```java
package android.daehoshin.com.coustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by daeho on 2017. 9. 18..
 */

public class CustomView extends View {
    /**
     * 코드로 생성할때 호출하는 생성자
     * @param context
     */
    public CustomView(Context context){
        super(context);
    }

    /**
     * xml 에서 태그로 사용할 때 시스템에서 호출해주는 생성자
     * @param context
     * @param attrs
     */
    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 컨트롤이 그려질때 동작하는 메소드에 사각형을 그리는 코드 추가
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 물감 - 겉모양을 결정하는 도구
        Paint paint = new Paint();
        paint.setColor(Color.CYAN);
        paint.setStrokeWidth(2);

        // 종이 - 무엇을 그릴지 결정하는 도구
        canvas.drawRect(100, 100, 200, 200, paint);
    }
}
```


## DrawActivity class

```java
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
```


## DrawView class

```java
radioGroup.setOnCheckedChangeListener(radioCheckedChangeListener);

RadioGroup.OnCheckedChangeListener radioCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.rbtRed:
                textView.setText("빨간불이 켜졌습니다");
                break;
            case R.id.rbtGreen:
                textView.setText("초록불이 켜졌습니다");
                break;
            case R.id.rbtBlue:
                textView.setText("파란불이 켜졌습니다");
                break;
            case R.id.rbtSpinner:
                Intent intent = new Intent(getApplicationContext(), SpinnerActivity.class);
                startActivity(intent);
                break;
        }
    }
}
```


## PathTool class

```java
spackage android.daehoshin.com.coustomview;

import android.graphics.Path;

/**
 * Path에 color와 size를 포함하기위해 path를 상속받아 만든 클래스
 *
 * Created by daeho on 2017. 9. 18..
 */

public class PathTool extends Path{
    private int color;
    private float size;

    /**
     * 생성자에서 초기값을 받아 셋팅
     * @param color
     * @param size
     */
    public PathTool(int color, float size){
        this.color = color;
        this.size = size;
    }

    /**
     * 컬러를 받아오는 메소드
      * @return
     */
    public int getColor(){
        return this.color;
    }

    /**
     * 사이즈를 받아오는 메소드
     * @return
     */
    public float getSize() { return this.size; }
}
```
