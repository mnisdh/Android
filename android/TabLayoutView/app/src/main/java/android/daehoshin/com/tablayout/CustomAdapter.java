package android.daehoshin.com.tablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 페이저 아답터에 프래그먼트 배열을 넘겨서 동작하게 한다
 * Created by daeho on 2017. 9. 27..
 */

public class CustomAdapter extends FragmentStatePagerAdapter{
    private List<Fragment> data;

    public CustomAdapter(FragmentManager fm, List<Fragment> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
