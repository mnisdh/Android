package android.daehoshin.com.transstation;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by daeho on 2017. 10. 16..
 */

public class Remote {
    public static String getData(String string){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(string);
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

        return sb.toString();
    }

}
