package android.daehoshin.com.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * AsyncTask = 세개의 기본함수를 지원하는 Thread
 * 1. onPreExecute : doInBackground함수가 실행되기 전에 실행되는 함수
 * 2. doInBackground : 백그라운드(sub thread)에서 코드를 실행하는 함수 < 이것만 sub thread에서 실행됨
 *                V    onPostExecute는 doInBackground에서 데이터를 전달 받을수 있다
 * 3. onPostExecute : doInBackground함수가 실행된 후 실행되는 함수
 */
public class MainActivity extends AppCompatActivity {
    TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getServer("http://google.co.kr");
    }

    private void init(){
        tvText = (TextView) findViewById(R.id.tvText);
    }

    private void getServer(String url){
        /**
         * new AsyncTask<doInBackground 함수의 파라미터로 사용
         *              , onProgressUpdate 함수의 파라미터로 사용(주로 진행상태값을 사용 int형)
         *              , doInBackground 합수의 리턴값(onPostExecute의 파라미터값)>
         */
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>(){
            @Override
            protected String doInBackground(String... params) {
                String param = params[0];
                String result = Remote.getData(param);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                // 전체 html 코드중에서
                // <title> </title> 안에 있는 내용만 출력
                
                int start = s.indexOf("<title>") + 7;
                int end = s.indexOf("</title>");
                s = s.substring(start, end);

                tvText.setText(s);
            }
        };

        task.execute(url);
    }
}
