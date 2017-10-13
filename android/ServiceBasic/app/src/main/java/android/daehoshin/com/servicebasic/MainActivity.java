package android.daehoshin.com.servicebasic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, MyService.class);

    }

    /**
     * 서비스 시작
     * @param v
     */
    public void start(View v){
        intent.setAction("START");
        startService(intent);
    }

    /**
     * 서비스 종료
     * @param v
     */
    public void stop(View v){
        intent.setAction("STOP");
        stopService(intent);
    }

    boolean isService = false;
    MyService service;
    // 서비스와읙 연결 통로
    ServiceConnection con = new ServiceConnection() {
        /**
         * 서비스와 연결되는 순간 호출되는 함수
         * @param name
         * @param iBinder
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            isService = true;
            service = ((MyService.CustomBinder)iBinder).getService();
            Toast.makeText(MainActivity.this, service.getTotal(), Toast.LENGTH_SHORT).show();
        }

        /**
         * 서비스가 중단되거나 연결이 도중에 끊겼을때 발생한다
         * 예) 정상적으로 stop이 호출되고 onDestroy가 발생하면 호출되지 않는다
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            isService = false;
        }
    };
    public void bind(View v){
        bindService(intent, con, Context.BIND_AUTO_CREATE);
    }

    public void unbind(View v){
        unbindService(con);
    }

    public void startForeground(){

    }

    public void stopForeground(){

    }
}
