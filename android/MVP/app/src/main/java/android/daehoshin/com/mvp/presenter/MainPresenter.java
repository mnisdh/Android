package android.daehoshin.com.mvp.presenter;

import android.daehoshin.com.mvp.view.IView;

/**
 * Created by daeho on 2017. 11. 23..
 */

public class MainPresenter implements IPresenter {
    public MainPresenter(IView view){
        view.setPresenter(this);
    }


}
