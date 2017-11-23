package android.daehoshin.com.mvp.view;

import android.daehoshin.com.mvp.presenter.IPresenter;
import android.view.View;

/**
 * Created by daeho on 2017. 11. 23..
 */

public interface IView {
    View getView();
    void setText(String s);
    void setPresenter(IPresenter presenter);
}
