package android.daehoshin.com.basiclayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/*
안드로이드 화면 구조
                            Fragment(화면조각)
App(어플) > Activity(화면한개단위)     >     Layout(뷰그룹, 컨테이너) > Widget(뷰)
 */


/**
 *                                Activity 기반클래스를 상속받아서 구성됨
 */
public class MainActivity extends AppCompatActivity {
    // 레이아웃에 정의된 위젯의 아이디로 해당 객체를 변수에 저장해둔다
    Button btnFrame, btnLinear, btnGrid, btnRelative;


    /**
     * 화면이 생성될때 호출되는 메소드
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. 레이아웃 xml 파일을 메모리에 로드
        setContentView(R.layout.activity_main);

        // 2. 전역변수에 실제위젯을 할당
        btnFrame = (Button) findViewById(R.id.btnFrame);
        btnLinear = (Button) findViewById(R.id.btnLinear);
        btnGrid = (Button) findViewById(R.id.btnGrid);
        btnRelative = (Button) findViewById(R.id.btnRelative);

        // 2. 위에서 저장한 변수의 이벤트(클릭 터치 등) 캐치가 필요할 경우 리스너를 달아줌
        btnFrame.setOnClickListener(onClickListener);
        btnLinear.setOnClickListener(onClickListener);
        btnGrid.setOnClickListener(onClickListener);
        btnRelative.setOnClickListener(onClickListener);

        /*
        버튼의 경우 변수로 갖고있을 필요가 거의 없으므로 변수선언없이 바로 이벤트연결 하는 경우도 있음
        findViewById(R.id.btnFrame).setOnClickListener(onClickListener);
        findViewById(R.id.btnLinear).setOnClickListener(onClickListener);
        findViewById(R.id.btnGrid).setOnClickListener(onClickListener);
        findViewById(R.id.btnRelative).setOnClickListener(onClickListener);
         */

        findViewById(R.id.btnCalc).setOnClickListener(onClickListener);
    }

    /**
     * 리스너를 전역변수로 선언할 수 있다
     */
    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            // 액티비티(메이저 컴포넌트) 실행
            // 1. 인텐트(시스템에 전달되는 메시지 객체) 생성
            Intent intent = null;

            switch (v.getId()){
                case R.id.btnFrame:
                    intent = new Intent(MainActivity.this, FrameActivity.class);
                    break;
                case R.id.btnLinear:
                    intent = new Intent(MainActivity.this, LinearActivity.class);
                    break;
                case R.id.btnGrid:
                    intent = new Intent(MainActivity.this, GridActivity.class);
                    break;
                case R.id.btnRelative:
                    intent = new Intent(MainActivity.this, RelativeActivity.class);
                    break;
                case R.id.btnCalc:
                    intent = new Intent(MainActivity.this, CalculatorActivity.class);
                    break;
            }

            startActivity(intent);
        }
    };
}
