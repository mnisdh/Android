package a.mnisdh.com.kotlinbasic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class JaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jave);

        KotlinTwo kotlinTwo = new KotlinTwo("");
        int value = kotlinTwo.getValue();
        String value2 = kotlinTwo.getValue2();
    }
}
