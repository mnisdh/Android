package android.daehoshin.com.musicplayer.player;

import android.content.Intent;
import android.daehoshin.com.musicplayer.BaseActivity;
import android.daehoshin.com.musicplayer.MusicFragment;
import android.daehoshin.com.musicplayer.R;
import android.daehoshin.com.musicplayer.domain.Music;
import android.daehoshin.com.musicplayer.util.Const;
import android.daehoshin.com.musicplayer.util.TypeUtil;
import android.media.MediaPlayer;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlayerActivity extends BaseActivity implements View.OnClickListener {
    ViewPager vpContent;
    ImageButton btnPlay;
    TextView tvPlayTime, tvMusicTime;
    SeekBar sbPlayTime;

    Music.Item item = null;
    MediaPlayer player = null;
    int current = -1;
    MusicFragment.ListType musicListType;

    boolean usePlayTimeThread = true;

    @Override
    public void init() {
        setContentView(R.layout.activity_player);

        Intent intent = getIntent();
        current = intent.getIntExtra(Const.KEY_POSITION, -1);
        musicListType = MusicFragment.ListType.valueOf(intent.getStringExtra(Const.KEY_MUSICLISTTYPE));

        bindControl();

        loadItem();
        initPlayer();
        initViewPager();
        initListener();

        initPlayTimeThread();

        start();
    }

    private void bindControl() {
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        tvPlayTime = (TextView) findViewById(R.id.tvPlayTime);
        tvMusicTime = (TextView) findViewById(R.id.tvMusicTime);
        sbPlayTime = (SeekBar) findViewById(R.id.sbPlayTime);
    }

    private void loadItem(){
        item = (Music.Item)Music.getInstance().getData(musicListType).get(current);
    }

    private void initPlayer(){
        if(player != null) {
            player.release();
            player = null;
        }

        player = MediaPlayer.create(this, item.titleUri);
        player.setLooping(false);

        tvMusicTime.setText(TypeUtil.miliToSec(player.getDuration()));
        sbPlayTime.setMax(player.getDuration());
    }

    private void initViewPager(){
        vpContent = (ViewPager) findViewById(R.id.vpContent);

        PlayerPagerAdapter adapter = new PlayerPagerAdapter(this, musicListType);
        vpContent.setAdapter(adapter);
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                boolean isPlaying = player.isPlaying();

                current = position;
                loadItem();

                initPlayer();

                if(isPlaying) start();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if(current > -1) vpContent.setCurrentItem(current);
    }

    private void initListener() {
        btnPlay.setOnClickListener(this);
        findViewById(R.id.btnFF).setOnClickListener(this);
        findViewById(R.id.btnNext).setOnClickListener(this);
        findViewById(R.id.btnPrevious).setOnClickListener(this);
        findViewById(R.id.btnRew).setOnClickListener(this);
    }

    private void initPlayTimeThread() {
        new Thread() {
            public void run() {
                while (usePlayTimeThread) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int cp = player.getCurrentPosition();
                            sbPlayTime.setProgress(cp);
                            tvPlayTime.setText(TypeUtil.miliToSec(cp));
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void start(){
        player.start();
        btnPlay.setImageResource(android.R.drawable.ic_media_pause);
    }

    private void pause(){
        player.pause();
        btnPlay.setImageResource(android.R.drawable.ic_media_play);
    }

    @Override
    protected void onDestroy() {
        usePlayTimeThread = false;
        if(player != null) player.release();

        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFF:
                break;
            case R.id.btnNext:
                vpContent.setCurrentItem(current + 1);
                break;
            case R.id.btnPlay:
                if(player.isPlaying()) pause();
                else start();
                break;
            case R.id.btnPrevious:
                vpContent.setCurrentItem(current - 1);
                break;
            case R.id.btnRew:
                break;
        }
    }

}
