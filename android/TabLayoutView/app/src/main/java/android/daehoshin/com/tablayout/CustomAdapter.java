package android.daehoshin.com.tablayout;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 페이저 아답터에 프래그먼트 배열을 넘겨서 동작하게 한다
 * Created by daeho on 2017. 9. 27..
 */

public class CustomAdapter extends PagerAdapter{
    private static final int COUNT = 4;

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view;

        switch (position){
            case 1: view = new Two(container.getContext()); break;
            case 2: view = new Three(container.getContext()); break;
            case 3: view = new Four(container.getContext()); break;
            default: view = new One(container.getContext()); break;
        }
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        //super.destroyItem(container, position, object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
