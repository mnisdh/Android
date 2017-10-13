package android.daehoshin.com.servicebasic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    /**
     * 컴포넌트는 바인더를 통해 서비스에 접근할 수 있다
     */
    class CustomBinder extends Binder{
        public CustomBinder(){

        }

        public MyService getService(){
            return MyService.this;
        }
    }

    IBinder binder = new CustomBinder();

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MyService", "-----------------------onBind()------");

        return binder;
    }

    public int getTotal(){
        return total;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("MyService", "-----------------------onUnbind()------");

        return super.onUnbind(intent);
    }

    private int total = 0;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        startFg();
//
//        Log.d("MyService", "-----------------------onStartCommand()------");
//        for(int i = 1; i < 1001; i++) {
//            total++;
//            System.out.println("서비스에서 동작정입니다." + i);
//        }

        if(intent != null){
            String action = intent.getAction();
            switch (action){
                case "START":
                    setNotification("PAUSE");
                    break;
                case "PAUSE":
                    setNotification("START");
                    break;
                case "DELETE":
                    stopForeground(true);
                    break;
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    // Foreground 서비스하기
    // Foreground 서비스 번호
    public static final int FLAG = 4342;

    private Bitmap largeIcon = null;
    private void setNotification(String cmd){
        int icon = android.R.drawable.ic_media_pause;
        if(cmd.equals("PAUSE")) icon = android.R.drawable.ic_media_play;

        // Foreground 서비스에서 보여질 Notification 만들기
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(icon)
                .setContentTitle("title")
                .setContentText("내용");
        if(largeIcon != null) largeIcon.recycle();
        largeIcon = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round);
        builder.setLargeIcon(largeIcon);



        // 노티전체를 클릭했을때 발생하는 액션처리
        Intent deleteIntent = new Intent(getBaseContext(), MyService.class);
        deleteIntent.setAction("DELETE");
        PendingIntent deletePendingIntent = PendingIntent.getService(this,1, deleteIntent, 0);
        builder.setContentIntent(deletePendingIntent);





        // 클릭을 했을때 noti를 멈추는 명령어를 서비스에서 다시 받아서 처리
        Intent pauseIntent = new Intent(getBaseContext(), MyService.class);
        pauseIntent.setAction(cmd);
        PendingIntent pendingIntent = PendingIntent.getService(this,1, pauseIntent, 0);

        // Notification에 들어가는 버튼을 만드는 명령
        int pauseIcon = android.R.drawable.ic_media_pause;
        if(cmd.equals("START")) pauseIcon = android.R.drawable.ic_media_play;
        NotificationCompat.Action pauseAction = new NotificationCompat.Action.Builder(pauseIcon, cmd, pendingIntent).build();
        builder.addAction(pauseAction);






        Notification notification = builder.build();

        // 노티바 노출시키기
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        startForeground(FLAG, notification);
    }

    private void stopFg(){
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(FLAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService", "-----------------------onCreate()------");
    }

    @Override
    public void onDestroy() {
        stopForeground(true); // 포그라운드 상태에서 해제된다 서비스는 유지

        super.onDestroy();
        Log.d("MyService", "-----------------------onDestroy()------");
    }
}
