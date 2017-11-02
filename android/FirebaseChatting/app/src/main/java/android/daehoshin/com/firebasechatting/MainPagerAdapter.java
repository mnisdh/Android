package android.daehoshin.com.firebasechatting;

import android.content.Context;
import android.daehoshin.com.firebasechatting.friend.FriendRecyclerView;
import android.daehoshin.com.firebasechatting.room.RoomListRecyclerView;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by daeho on 2017. 11. 2..
 */

public class MainPagerAdapter extends PagerAdapter {
    private Map<String, View> data = new HashMap<>();
    private Context context;

    public MainPagerAdapter(Context context, List<String> data){
        this.context = context;

        this.data.clear();
        for(String item : data) this.data.put(item, null);
    }

    private String getKey(int position){
        int cnt = 0;
        for(String key : data.keySet()){
            if(cnt == position) return key;
        }

        return "";
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = null;

        String key = getKey(position);
        View value = data.get(key);
        if(value == null) {
            if (context.getResources().getString(R.string.tab_friend).equals(key)) {
                v = new FriendRecyclerView(context);
            } else if (context.getResources().getString(R.string.tab_rooms).equals(key)) {
                v = new RoomListRecyclerView(context);
            }

            if(v != null) container.addView(v);

            return v;
        }
        else return value;
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
        container.removeView((View)object);
    }
}
