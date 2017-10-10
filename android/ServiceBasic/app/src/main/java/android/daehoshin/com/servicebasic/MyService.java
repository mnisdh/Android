package android.daehoshin.com.servicebasic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
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
        startFg();

        Log.d("MyService", "-----------------------onStartCommand()------");
        for(int i = 1; i < 1001; i++) {
            total++;
            System.out.println("서비스에서 동작정입니다." + i);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    // Foreground 서비스하기
    // Foreground 서비스 번호
    public static final int FLAG = 4342;

    private void startFg(){
        // Foreground 서비스에서 보여질 Notification 만들기
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Notification notification = builder.setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("title")
                .setContentText("내용")
                .build();

        // 노티바 노출시키기
        // Notification 매니저를 통해서 노티바를 출력
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(FLAG, notification);
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
        super.onDestroy();
        Log.d("MyService", "-----------------------onDestroy()------");
    }
}
