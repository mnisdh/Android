package android.daehoshin.com.permission;

import android.os.Bundle;

/**
 * 안드로이드 권한요청
 * 가 일반적인 권한 요청 -> manifests에 설정
 *   - 디스크 읽기, 쓰기
 */
public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    void init() {
        setContentView(R.layout.activity_main);
    }


}
