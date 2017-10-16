package android.daehoshin.com.network;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * - 네트워킹
 * 1. 권한 설정 > 런타임권(x)
 * 2. Thread > 네트워크를 통한 데이터 이용은 Sub Thread
 * 3. HttpURLConnection > 안드로이드 기본 Api
 *    > Retrofit (내부에 Thread 포함)
 *    > Rx (내부에 Thread 포함, Thread 관리기능 포함, 예외처리 특화
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkThread nt = new NetworkThread();
        nt.setNetworkEvent(new NetworkEvent() {
            @Override
            public void finishedRequest(final String text) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView)findViewById(R.id.tvText)).setText(text);
                    }
                });
            }
        });

        nt.start();
    }

    /**
     * HttpURLConnection 사용하기
     * 1. URL 객체를 선언 (웹주소를 가지고 생성
     * 2. URL 객체에서 서버연결을 해준다 > HttpURLConnection을 생성 = Stream
     * 3. 커넥션의 방식을 설정(default : GET)
     * 4. 연결되어있는 Stream을 통해서 데이터를 가져온다
     * 5. 연결(Stream)을 닫는다
     */
    public class NetworkThread extends Thread{
        private NetworkEvent networkEvent = null;
        public void setNetworkEvent(NetworkEvent networkEvent){
            this.networkEvent = networkEvent;
        }

        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL("http://google.co.kr");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                // 통신이 성공인지 체크
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // 여기서 부터는 파일에서 데이터를 가져오는 것과 동일
                    InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                    BufferedReader br = new BufferedReader(isr);

                    String temp = "";
                    while ((temp = br.readLine()) != null) {
                        sb.append(temp).append("\n");
                    }

                    br.close();
                    isr.close();

                } else Log.e("ServerError", conn.getResponseCode() + "");

                conn.disconnect();
            }
            catch (Exception e){
                Log.e("Error", e.toString());
            }

            if(networkEvent != null) networkEvent.finishedRequest(sb.toString());
            Log.d("RESULT", sb.toString());
        }


    }

    public interface NetworkEvent{
        void finishedRequest(String content);
    }
}
