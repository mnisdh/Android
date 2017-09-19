package android.daehoshin.com.basiclist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 인텐트를 통해 넘어온 값 꺼내기
        Intent intent = getIntent(); // startActivity를 통해 넘어온 intent를 꺼낸다
        //// 인텐트에서 값의 묶음인 번들을 꺼내고
        //Bundle bundle = intent.getExtras();
        //// 번들에서 최종 값을 꺼낸다
        //String result = bundle.getString("valueKey");

        // 인텐트에서 바로 값을 꺼내기
        String result = intent.getStringExtra("ValueKey");

        ((TextView) findViewById(R.id.tvText)).setText(result);
    }
}
