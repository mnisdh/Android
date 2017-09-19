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
