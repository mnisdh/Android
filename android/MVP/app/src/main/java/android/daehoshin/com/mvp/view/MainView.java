package android.daehoshin.com.mvp.view;

import android.content.Context;
import android.daehoshin.com.mvp.R;
import android.daehoshin.com.mvp.presenter.IPresenter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by daeho on 2017. 11. 23..
 */

public class MainView implements IView{
    View view;
    IPresenter presenter;

    TextView tvTest;
    Button btnTest;

    public MainView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.activity_main, null);

        tvTest = view.findViewById(R.id.tvTest);
        btnTest = view.findViewById(R.id.btnTest);
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void setText(String s) {

    }

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
    }
}
