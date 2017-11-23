package android.daehoshin.com.mvvm;

import android.daehoshin.com.mvvm.databinding.MainBinding;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        MainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.main);
        mainBinding.setUser(new User("daeho", "shin"));
    }
}
