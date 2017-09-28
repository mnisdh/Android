package android.daehoshin.com.threadbasic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static android.daehoshin.com.threadbasic.MainActivity.ACTION_SET;

public class MainActivity extends AppCompatActivity {
    Rotator rotator;
    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        btnTest = (Button) findViewById(R.id.btnTest);
        rotator = new Rotator(handler);

        rotator.start();
    }

    public void stop(View v){
        if(rotator.isRunning()) rotator.setStop();
        else rotator.start();
    }

    public static final int ACTION_SET = 999;
    // seekbar를 변경하는 Handler 작성
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ACTION_SET:
                    float currentRot = btnTest.getRotation();
                    btnTest.setRotation(currentRot + 6);
                    break;
            }
        }
    };
}


class Rotator extends Thread{
    Handler handler;
    boolean isRun = true;

    public boolean isRunning(){
        return isRun;
    }

    public Rotator(Handler handler) { this.handler = handler; }

    /**
     * start 메서드가 호출되면 실행된다 이것만 sub thread에서 실행
     */
    @Override
    public void run() {
        isRun = true;
        while(isRun){
            // 매초 button의 회전값을 변경한다
            Message msg = new Message();
            msg.what = ACTION_SET;
            handler.sendMessage(msg);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * run 이외의 함수는 sub thread 실행되지 않는다
     */
    public void setStop(){
        isRun = false;
    }
}