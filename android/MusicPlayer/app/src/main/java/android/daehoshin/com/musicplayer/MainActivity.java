package android.daehoshin.com.musicplayer;

import android.content.Intent;
import android.daehoshin.com.musicplayer.domain.Album;
import android.daehoshin.com.musicplayer.domain.Artist;
import android.daehoshin.com.musicplayer.domain.IMusicItem;
import android.daehoshin.com.musicplayer.domain.Music;
import android.daehoshin.com.musicplayer.player.PlayerActivity;
import android.daehoshin.com.musicplayer.util.Const;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import static android.daehoshin.com.musicplayer.MusicFragment.ListType;
import static android.daehoshin.com.musicplayer.MusicFragment.OnListFragmentInteractionListener;
import static android.daehoshin.com.musicplayer.MusicFragment.newInstance;

public class MainActivity extends BaseActivity implements OnListFragmentInteractionListener {
    TabLayout tlType;
    ViewPager vpList;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public void init() {
        setContentView(R.layout.activity_main);

        loadData();
        initControl();
    }

    private void loadData(){
        Album.getInstance().load(this);
        Artist.getInstance().load(this);
        Music.getInstance().load(this);
    }

    private void initControl(){
        tlType = (TabLayout) findViewById(R.id.tlType);
        vpList = (ViewPager) findViewById(R.id.vpList);

        tlType.addTab(tlType.newTab().setText(getString(R.string.tab_title)));
        tlType.addTab(tlType.newTab().setText(getString(R.string.tab_artist)));
        tlType.addTab(tlType.newTab().setText(getString(R.string.tab_album)));
        tlType.addTab(tlType.newTab().setText(getString(R.string.tab_favorite)));

        fragments.add(newInstance(1, ListType.TITLE));
        fragments.add(newInstance(1, ListType.ARTIST));
        fragments.add(newInstance(1, ListType.ALBUM));
        fragments.add(newInstance(1, ListType.FAVORITE));

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
    public void onClick(int position, ListType musicListType) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(Const.KEY_POSITION, position);
        intent.putExtra(Const.KEY_MUSICLISTTYPE, musicListType.toString());

        startActivity(intent);
    }

    @Override
    public List<IMusicItem> getList(ListType musicListType) {
        switch (musicListType){
            case TITLE:
                return Music.getInstance().getData(musicListType);
            case ALBUM:
                return Album.getInstance().getData();
            case ARTIST:
                return Artist.getInstance().getData();
            case FAVORITE:
                break;
        }

        return new ArrayList<IMusicItem>();
    }
}
