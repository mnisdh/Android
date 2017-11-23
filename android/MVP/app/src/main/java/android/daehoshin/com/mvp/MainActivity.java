package android.daehoshin.com.mvp;

import android.daehoshin.com.mvp.presenter.IPresenter;
import android.daehoshin.com.mvp.presenter.MainPresenter;
import android.daehoshin.com.mvp.view.IView;
import android.daehoshin.com.mvp.view.MainView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IView mainView = new MainView(this);
        setContentView(mainView.getView());

        IPresenter presenter = new MainPresenter(mainView);

    }
}
