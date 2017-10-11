package android.daehoshin.com.musicplayer;

import android.content.Intent;
import android.daehoshin.com.musicplayer.domain.Music;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import static android.daehoshin.com.musicplayer.MusicFragment.OnListFragmentInteractionListener;

public class MainActivity extends BaseActivity implements OnListFragmentInteractionListener {
    TabLayout tlType;
    ViewPager vpList;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    void init() {
        setContentView(R.layout.activity_main);

        loadData();
        initControl();
    }

    private void loadData(){
        Music.getInstance().load(this);
    }

    private void initControl(){
        tlType = (TabLayout) findViewById(R.id.tlType);
        vpList = (ViewPager) findViewById(R.id.vpList);

        tlType.addTab(tlType.newTab().setText(getString(R.string.tab_title)));
        tlType.addTab(tlType.newTab().setText(getString(R.string.tab_artist)));
        tlType.addTab(tlType.newTab().setText(getString(R.string.tab_album)));
        tlType.addTab(tlType.newTab().setText(getString(R.string.tab_favorite)));

        fragments.add(MusicFragment.newInstance(1, 0));
        fragments.add(MusicFragment.newInstance(1, 1));
        fragments.add(MusicFragment.newInstance(1, 0));
        fragments.add(MusicFragment.newInstance(1, 0));

        MusicListPagerAdapter adapter = new MusicListPagerAdapter(getSupportFragmentManager(), fragments);
        vpList.setAdapter(adapter);

        // 탭레이아웃과 뷰페이저를 연결
        tlType.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(vpList)
        );
        vpList.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tlType)
        );
    }

    @Override
    public void onClick(int position, int musicListType) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(Const.KEY_POSITION, position);
        intent.putExtra(Const.KEY_MUSICLISTTYPE, musicListType);

        startActivity(intent);
    }

    @Override
    public List<Music.Item> getList(int musicListType) {
        return Music.getInstance().getData(musicListType);
    }
}
