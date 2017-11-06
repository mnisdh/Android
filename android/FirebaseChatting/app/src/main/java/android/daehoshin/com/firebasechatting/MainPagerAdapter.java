package android.daehoshin.com.firebasechatting;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daeho on 2017. 11. 2..
 */

public class MainPagerAdapter extends PagerAdapter {
    private Map<String, View> data = new HashMap<>();
    private Context context;

    public MainPagerAdapter(Context context, Map<String, View> data){
        this.context = context;
        this.data = data;
    }

    private String getKey(int position){
        int cnt = 0;
        for(String key : data.keySet()){
            if(cnt == position) return key;
            cnt++;
        }

        return "";
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String key = getKey(position);
        View v = data.get(key);

        if(v != null && !contains(container, v)) {
            container.addView(v);
        }

        return v;
    }

    private boolean contains(ViewGroup container, View findView){
        for(int i = 0; i < container.getChildCount(); i++){
            if(container.getChildAt(i) == findView) return true;
        }
        return false;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //container.removeView((View)object);
    }
}
