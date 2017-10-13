package android.daehoshin.com.musicplayer.list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by daeho on 2017. 10. 11..
 */

public class ListPagerAdapter extends FragmentStatePagerAdapter {
    private List<ListFragment> fragments;

    public ListPagerAdapter(FragmentManager fm, List<ListFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
