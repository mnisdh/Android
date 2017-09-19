package android.daehoshin.com.basiclist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 리스트뷰 사용하기
 */
public class MainActivity extends AppCompatActivity {
    // 1.데이터를 정의
    List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1.데이터를 정의100개의 가상값을 담는다
        for(int i = 0; i < 100; i++) data.add("임시값 " + i);

        // 2.데이터와 리스트뷰를 연결하는 아답터를 생성
        CustomAdapter adapter = new CustomAdapter(this, data);

        // 3.아답터와 리스트뷰를 연결
        ((ListView) findViewById(R.id.lvId)).setAdapter(adapter);
    }
}