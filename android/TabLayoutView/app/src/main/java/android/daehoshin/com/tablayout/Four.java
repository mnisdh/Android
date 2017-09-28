package android.daehoshin.com.tablayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by daeho on 2017. 9. 28..
 */

public class Four extends FrameLayout {
    public Four(Context context) {
        super(context);
        initView();
    }

    public Four(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * 여기서 사용할 레이아웃을 inflate하고
     * 나 자신에게 add 한다
     */
    private void initView(){
        // 1. 레이아웃 파일로 뷰를 만들고
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_four, null);

        addView(view);
    }
}
