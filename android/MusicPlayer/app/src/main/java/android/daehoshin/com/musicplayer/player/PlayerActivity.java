package android.daehoshin.com.musicplayer.player;

import android.content.Intent;
import android.daehoshin.com.musicplayer.BaseActivity;
import android.daehoshin.com.musicplayer.R;
import android.daehoshin.com.musicplayer.util.Const;
import android.daehoshin.com.musicplayer.util.TypeUtil;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlayerActivity extends BaseActivity implements View.OnClickListener, Player.PlayerListener {
    ViewPager vpContent;
    ImageButton btnPlay;
    TextView tvPlayTime, tvMusicTime;
    SeekBar sbPlayTime;

    Player player = null;
    boolean usePlayTimeThread = true;

    @Override
    public void init() {
        setContentView(R.layout.activity_player);

        bindControl();
        initViewPager();
        initListener();

        player = Player.getInstance();
        player.addPlayerLitener(this);

        Intent intent = getIntent();
        player.setPlayer(this, intent.getIntExtra(Const.KEY_POSITION, 0));
    }

    private void bindControl() {
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        tvPlayTime = (TextView) findViewById(R.id.tvPlayTime);
        tvMusicTime = (TextView) findViewById(R.id.tvMusicTime);
        sbPlayTime = (SeekBar) findViewById(R.id.sbPlayTime);
    }

    private void initViewPager(){
        vpContent = (ViewPager) findViewById(R.id.vpContent);

        PlayerPagerAdapter adapter = new PlayerPagerAdapter(this);
        vpContent.setAdapter(adapter);
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                boolean isPlaying = player.isPlaying();

                player.setPlayer(getBaseContext(), position);
                if(isPlaying) player.start();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if(player.getCurrent() > -1) vpContent.setCurrentItem(player.getCurrent());
    }

    private void initListener() {
        btnPlay.setOnClickListener(this);
        findViewById(R.id.btnFF).setOnClickListener(this);
        findViewById(R.id.btnNext).setOnClickListener(this);
        findViewById(R.id.btnPrevious).setOnClickListener(this);
        findViewById(R.id.btnRew).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        usePlayTimeThread = false;

        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFF:
                break;
            case R.id.btnNext:
                vpContent.setCurrentItem(player.getCurrent() + 1);
                break;
            case R.id.btnPlay:
                if(player.getCurrent() < 0) player.setPlayer(this, 0);

                if(player.isPlaying()) player.pause();
                else  player.start();
                break;
            case R.id.btnPrevious:
                vpContent.setCurrentItem(player.getCurrent() - 1);
                break;
            case R.id.btnRew:
                break;
        }
    }

    @Override
    public void playerSeted() {
        tvMusicTime.setText(player.getMaxTimeDuration());
        sbPlayTime.setMax(player.getDuration());
    }

    @Override
    public void playerStarted() {
        btnPlay.setImageResource(android.R.drawable.ic_media_pause);
    }

    @Override
    public void playerProgressThread() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int cp = player.getProgress();
                sbPlayTime.setProgress(cp);
                tvPlayTime.setText(TypeUtil.miliToSec(cp));
            }
        });
    }

    @Override
    public void playerPaused() {
        btnPlay.setImageResource(android.R.drawable.ic_media_play);
    }

    @Override
    public void playerClosed() {

    }
}
