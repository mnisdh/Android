package android.daehoshin.com.musicplayer.player;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.NotificationCompat;

import java.io.IOException;

public class PlayerService extends Service {

    private Bitmap largeIcon = null;
    Player player = null;
    private int FLAG = 987;

    public PlayerService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        player = Player.getInstance();
        player.addPlayerLitener(playerListener);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player = Player.getInstance();

        if(intent != null){
            String action = intent.getAction();
            if(action != null) {
                switch (action) {
                    case "UPDATE":
                        updateNotification();
                        break;
                    case "DELETE":
                        player.close();
                        stopForeground(true);
                        break;
                    default:
                        updateNotification();
                        break;
                }
            }
            else updateNotification();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void updateNotification(){
        // Foreground 서비스에서 보여질 Notification 만들기
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(getSmallIcon())
                .setContentTitle(getTitle())
                .setContentText(getArtist())
                .setLargeIcon(getAlbum());

//        // 노티전체를 클릭했을때 발생하는 액션처리
//        Intent deleteIntent = new Intent(getBaseContext(), PlayerService.class);
//        deleteIntent.setAction("DELETE");
//        PendingIntent deletePendingIntent = PendingIntent.getService(this,1, deleteIntent, 0);
//        builder.setContentIntent(deletePendingIntent);


//        // 클릭을 했을때 noti를 멈추는 명령어를 서비스에서 다시 받아서 처리
//        Intent pauseIntent = new Intent(getBaseContext(), PlayerService.class);
//        pauseIntent.setAction(cmd);
//        PendingIntent pendingIntent = PendingIntent.getService(this,1, pauseIntent, 0);
//
//        // Notification에 들어가는 버튼을 만드는 명령
//        int pauseIcon = android.R.drawable.ic_media_pause;
//        if(cmd.equals("START")) pauseIcon = android.R.drawable.ic_media_play;
//        NotificationCompat.Action pauseAction = new NotificationCompat.Action.Builder(pauseIcon, cmd, pendingIntent).build();
//        builder.addAction(pauseAction);


        Notification notification = builder.build();

        // 노티바 노출시키기
        //NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        startForeground(FLAG, notification);
    }

    private Player.ItemData getItemData(){
        return player.getCurrentData();
    }
    private int getSmallIcon(){
        if(player.isPlaying()) return android.R.drawable.ic_media_play;
        else return android.R.drawable.ic_media_pause;
    }
    private String getTitle(){
        if(getItemData() == null) return "";
        else return getItemData().getTitle();
    }
    private String getArtist(){
        if(getItemData() == null) return "";
        else return getItemData().getArtist();
    }
    private Bitmap getAlbum(){
        if(largeIcon != null) {
            largeIcon.recycle();
            largeIcon = null;
        }

        if(getItemData() == null) return largeIcon;

        try {
            largeIcon = MediaStore.Images.Media.getBitmap(getContentResolver(), player.getCurrentData().getAlbumUri());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return largeIcon;
    }

    private Player.PlayerListener playerListener = new Player.PlayerListener() {
        @Override
        public void playerSeted() {
            updateNotification();
        }

        @Override
        public void playerStarted() {
            updateNotification();
        }

        @Override
        public void playerProgressThread() {
            updateNotification();
        }

        @Override
        public void playerPaused() {
            updateNotification();
        }

        @Override
        public void playerClosed() {
            updateNotification();
        }
    };

    @Override
    public void onDestroy() {
        if(largeIcon != null) {
            largeIcon.recycle();
            largeIcon = null;
        }

        player.removePlayerListener(playerListener);

        super.onDestroy();
    }

}
