package android.daehoshin.com.tablayout;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 페이저 아답터에 프래그먼트 배열을 넘겨서 동작하게 한다
 * Created by daeho on 2017. 9. 27..
 */

public class CustomAdapter extends PagerAdapter{
    private static final int COUNT = 4;
    List<View> views;

    public CustomAdapter(Context context){
        views = new ArrayList<>();
        views.add(new One(context));
        views.add(new Two(context));
        views.add(new Three(context));
        views.add(new Four(context));
        for(View v : views) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                v.setId(View.generateViewId());
            }
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        if(container.getChildCount() == COUNT) return view;

        View temp = container.findViewById(view.getId());
        if(temp == null) container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //container.removeView((View) object);
        //super.destroyItem(container, position, object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
