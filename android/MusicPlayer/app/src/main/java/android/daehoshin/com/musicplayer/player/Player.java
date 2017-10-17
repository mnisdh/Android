package android.daehoshin.com.musicplayer.player;

import android.content.Context;
import android.daehoshin.com.musicplayer.util.TypeUtil;
import android.media.MediaPlayer;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by daeho on 2017. 10. 12..
 */

public class Player {
    private static Player p = null;
    public static Player getInstance(){
        if(p == null) p = new Player();

        return p;
    }

    private MediaPlayer mPlayer = null;
    private int current = -1;
    public int getCurrent(){
        return current;
    }
    public ItemData getCurrentData(){
        if(current == -1) return null;
        if(current >= data.size()) return null;

        return data.get(current);
    }
    private List<ItemData> data = new ArrayList<>();
    public List<ItemData> getData(){
        return data;
    }

    public boolean useLooping = false;
    public boolean isPlaying(){
        if(mPlayer == null) return false;

        return mPlayer.isPlaying();
    }
    public String getMaxTimeDuration(){
        if(mPlayer == null) return "0:00";

        return TypeUtil.miliToSec(mPlayer.getDuration());
    }
    public int getDuration(){
        if(mPlayer == null) return 0;

        return mPlayer.getDuration();
    }

    public int getProgress(){
        if(mPlayer == null) return 0;

        return mPlayer.getCurrentPosition();
    }

    private List<PlayerListener> playerListeners = new CopyOnWriteArrayList<>();
    public void addPlayerLitener(PlayerListener playerListener){
        playerListeners.add(playerListener);
    }
    public void removePlayerListener(PlayerListener playerListener){
        if(playerListeners.contains(playerListener)) playerListeners.remove(playerListener);
    }

    boolean usePlayTimeThread = true;

    private Player(){
        startProgressThread();
    }

    private Context context = null;
    public void setMainContext(Context context){
        this.context = context;
    }

    public void addData(ItemData item){
        if(current < 0) current = 0;

        data.add(item);
    }
    public void addData(List<ItemData> items){
        if(current < 0) current = 0;

        for(ItemData item : items) data.add(item);
    }

    public void insertData(int index, ItemData item){
        if(current < 0) current = 0;

        if(isPlaying() && index <= current) current++;

        data.add(index, item);
    }
    public void insertData(int index, List<ItemData> items){
        if(current < 0) current = 0;

        if(isPlaying() && index <= current) current += items.size();

        for(int i = items.size() - 1; i >= 0; i--) data.add(index, items.get(i));
    }

    public void setPlayer(Context context){
        setPlayer(context, 0);
    }
    public void setPlayer(Context context, int current){
        this.current = current;

        boolean runPlay = false;
        if(mPlayer != null) {
            runPlay = mPlayer.isPlaying();

            mPlayer.release();
            mPlayer = null;
        }

        mPlayer = MediaPlayer.create(context, data.get(current).getTitleUri());
        mPlayer.setLooping(useLooping);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                next();
            }
        });

        for(PlayerListener listener : playerListeners) listener.playerSeted();

        if(runPlay) start();
    }

    public void previous(){
        int preCurrent = current - 1;
        if(preCurrent < 0) preCurrent = data.size() - 1;

        setPlayer(context, preCurrent);
    }

    public void next(){
        int nextCurrent = current + 1;
        if(nextCurrent >= data.size()) nextCurrent = 0;

        setPlayer(context, nextCurrent);
    }

    public void start(){
        if(mPlayer == null){
            return;
        }

        mPlayer.start();

        for(PlayerListener listener : playerListeners) listener.playerStarted();
    }

    public void pause(){
        mPlayer.pause();

        for(PlayerListener listener : playerListeners) listener.playerPaused();
    }

    public void close(){
        if(mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void startProgressThread() {
        new Thread() {
            public void run() {
                while (usePlayTimeThread) {

                    for(PlayerListener listener : playerListeners) listener.playerProgressThread();

//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            int cp = player.getCurrentPosition();
//                            sbPlayTime.setProgress(cp);
//                            tvPlayTime.setText(TypeUtil.miliToSec(cp));
//                        }
//                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public interface PlayerListener{
        void playerSeted();
        void playerStarted();
        void playerProgressThread();
        void playerPaused();
        void playerClosed();
    }

    public interface ItemData{
        Uri getTitleUri();
        Uri getAlbumUri();
        String getTitle();
        String getArtist();
        String getAlbum();
        String getDuration();
    }
}
