package android.daehoshin.com.musicplayer;

import android.content.Intent;
import android.daehoshin.com.musicplayer.domain.Album;
import android.daehoshin.com.musicplayer.domain.Artist;
import android.daehoshin.com.musicplayer.domain.IMusicItem;
import android.daehoshin.com.musicplayer.domain.Music;
import android.daehoshin.com.musicplayer.list.ListFragment;
import android.daehoshin.com.musicplayer.list.ListPagerAdapter;
import android.daehoshin.com.musicplayer.player.Player;
import android.daehoshin.com.musicplayer.player.PlayerService;
import android.daehoshin.com.musicplayer.playlist.PlayListFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.daehoshin.com.musicplayer.list.ListFragment.ListType;
import static android.daehoshin.com.musicplayer.list.ListFragment.newInstance;

public class MainActivity extends BaseActivity implements ListFragment.OnListFragmentListener, PlayListFragment.OnPlayListFragmentListener, View.OnClickListener, Player.PlayerListener {
    TabLayout tlType;
    ViewPager vpList;
    ImageButton btnPlay, btnPlayList;
    FloatingActionButton btnAdd;

    private List<ListFragment> fragments = new ArrayList<>();
    Intent intent;
    Player player;

    @Override
    public void init() {
        setContentView(R.layout.activity_main);

        loadData();
        initControl();
        initService();

        player = Player.getInstance();
        player.setMainContext(this);
        player.addPlayerLitener(this);
    }

    @Override
    protected void onDestroy() {
        if(intent != null) {
            intent = new Intent(this, PlayerService.class);
            intent.setAction("DELETE");
            startService(intent);
        }

        super.onDestroy();
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

        ListPagerAdapter fragmentAdapter = new ListPagerAdapter(getSupportFragmentManager(), fragments);
        vpList.setAdapter(fragmentAdapter);

        // 탭레이아웃과 뷰페이저를 연결
        tlType.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(vpList)
        );
        vpList.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tlType)
        );

        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(this);

        btnPlayList = (ImageButton) findViewById(R.id.btnPlayList);
        btnPlayList.setOnClickListener(this);

        btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);


        findViewById(R.id.btnRew).setOnClickListener(this);
        findViewById(R.id.btnPrevious).setOnClickListener(this);
        findViewById(R.id.btnNext).setOnClickListener(this);
        findViewById(R.id.btnFF).setOnClickListener(this);
    }

    private void initService(){
        intent = new Intent(this, PlayerService.class);
        startService(intent);
    }

    @Override
    public void onListItemClick(IMusicItem item) {

    }

    @Override
    public List<IMusicItem> getList(ListType musicListType) {
        switch (musicListType){
            case TITLE: return Music.getInstance().getData();
            case ALBUM: return Album.getInstance().getData();
            case ARTIST: return Artist.getInstance().getData();
            case FAVORITE:
                break;
        }

        return new ArrayList<IMusicItem>();
    }

    @Override
    public void playerSeted() {

    }

    @Override
    public void playerStarted() {
        btnPlay.setImageResource(android.R.drawable.ic_media_pause);
    }

    @Override
    public void playerProgressThread() {

    }

    @Override
    public void playerPaused() {
        btnPlay.setImageResource(android.R.drawable.ic_media_play);
    }

    @Override
    public void playerClosed() {

    }

    private boolean isRunningPlayList = false;
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnPlayList:
                if(isRunningPlayList) {
                    onBackPressed();

                    btnPlayList.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                    isRunningPlayList = false;
                }
                else{
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.listStage, PlayListFragment.newInstance(1))
                            .addToBackStack(null)
                            .commit();

                    btnPlayList.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                    isRunningPlayList = true;
                }

                break;
            case R.id.btnAdd:
                List<Player.ItemData> data = Music.getInstance().getCheckedData();
                if(data.size() == 0){
                    Toast.makeText(this, "선택된 노래가 없습니다.", Toast.LENGTH_LONG).show();
                }
                else{
                    player.insertData(0, data);
                    Toast.makeText(this, data.size() + "곡을 추가하였습니다.", Toast.LENGTH_LONG).show();

                    fragments.get(vpList.getCurrentItem()).refresh();
                }

                break;
            case R.id.btnPrevious:
                player.previous();
                break;
            case R.id.btnRew:
                break;
            case R.id.btnPlay:
                if(player.getCurrent() < 0) player.setPlayer(this, 0);

                if(player.isPlaying()) player.pause();
                else player.start();
                break;
            case R.id.btnFF:
                break;
            case R.id.btnNext:
                player.next();
                break;
        }
    }


    @Override
    public void onPlayListItemClick(int current) {
        player.setPlayer(this, current);


        if(isRunningPlayList) {



            onBackPressed();

            btnPlayList.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            isRunningPlayList = false;
        }
        else{
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.listStage, PlayListFragment.newInstance(1))
                    .addToBackStack(null)
                    .commit();

            btnPlayList.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            isRunningPlayList = true;
        }
    }
}
