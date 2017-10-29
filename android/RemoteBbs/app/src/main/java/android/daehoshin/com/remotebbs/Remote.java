package android.daehoshin.com.remotebbs;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by daeho on 2017. 10. 16..
 */

public class Remote {
    public static String sendData(String address, byte[] data){
        StringBuilder result = new StringBuilder();

        try{
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            // Header 작성
            conn.setRequestProperty("Content-Type", "multipart/form-data; charset=utf8");
            //conn.addRequestProperty("Authorization", "token=나중에서버랑할때사용될거임");

            // Post 데이터를 전송
//            String q = "";
//            for(String key : postData.keySet()) q += "&" + key + "=" + postData.get(key);
//
//            q = q.substring(1);

            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(data);
            os.flush();
            os.close();

            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                BufferedReader br = new BufferedReader(isr);

                String temp = "";
                while ((temp = br.readLine()) != null) {
                    result.append(temp);
                }

                br.close();
                isr.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return result.toString();
    }

    public static String sendPost(String address, String json){
        StringBuilder result = new StringBuilder();

        try{
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            // Header 작성
            conn.setRequestProperty("Content-Type", "application/json; charset=utf8");
            //conn.addRequestProperty("Authorization", "token=나중에서버랑할때사용될거임");

            // Post 데이터를 전송
//            String q = "";
//            for(String key : postData.keySet()) q += "&" + key + "=" + postData.get(key);
//
//            q = q.substring(1);

            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                BufferedReader br = new BufferedReader(isr);

                String temp = "";
                while ((temp = br.readLine()) != null) {
                    result.append(temp);
                }

                br.close();
                isr.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return result.toString();
    }

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
                    sb.append(temp);
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
