package android.daehoshin.com.dhlibrary.http;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.Primitives;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by daeho on 2017. 10. 30..
 */

public class DHttp {
    private static final String attachmentName = "bitmap";
    private static final String attachmentFileName = "bitmap.bmp";
    private static final String crlf = "\r\n";
    private static final String twoHyphens = "--";
    private static final String boundary =  "*****";

    private static void writeFile(DataOutputStream request, File file) throws IOException {

        request.writeBytes(twoHyphens + boundary + crlf);
        request.writeBytes("Content-Disposition: form-data; name=\"" +
                file.getName() + "\";filename=\"" +
                file.getName() + "\"" + crlf);
        request.writeBytes(crlf);

        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        request.write(bytes);

        request.writeBytes(crlf);
        request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);

        request.flush();
        request.close();
    }

    public static String send(String address, List<File> files){
        StringBuilder result = new StringBuilder();
        try {
            URL url =  new URL(address);

            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setDoOutput(true);

            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
            httpUrlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream request = new DataOutputStream(httpUrlConnection.getOutputStream());

            for(File file : files) writeFile(request, file);

            if(httpUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader isr = new InputStreamReader(httpUrlConnection.getInputStream());
                BufferedReader br = new BufferedReader(isr);

                String temp = "";
                while ((temp = br.readLine()) != null) {
                    result.append(temp);
                }

                br.close();
                isr.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public static String sendData(String address, byte[] data){
        StringBuilder result = new StringBuilder();

        try{
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            // Header 작성
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=\"+boundary");


            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();

//            os.write((twoHyphens + boundary + lineEnd).getBytes());
//            os.write(("Content-Disposition: form-data; name=\"file\";filename=\"test.jpg\"" + lineEnd).getBytes());
//            os.write(("Content-Type: image/jpg" + lineEnd).getBytes());
//            os.write(("Content-Transfer-Encoding: binary"+lineEnd).getBytes());
//            os.flush();

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

    public static String getStringData(String string){
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

    public static <T>T getData(String string, Class<T> cls){
        String result = getData(string);

        Gson gson = new Gson();

        Object object = gson.fromJson(result, (Type) cls);
        return Primitives.wrap(cls).cast(object);
    }
}
