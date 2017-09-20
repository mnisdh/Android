package android.daehoshin.com.androidmemo.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by daeho on 2017. 9. 20..
 */

public class FileUtil {
    public static void write(Context context, String fileName, byte[] bytes) throws IOException {
        FileOutputStream fos = null;
        try {
            // 내용을 파일에 쓴다
            // 내부저장소 경로 : /data/data/패키지명/files
            //String filename = System.currentTimeMillis() + ".txt";
            //File file = new File(DIR_INTR + filename);

            // 1. 스트림을 열고
            //FileOutputStream fos = new FileOutputStream(file);
            fos = context.openFileOutput(fileName, MODE_PRIVATE); // 내부저장소에 파일 스트림을 열어준다

            // 3. 내용을 쓴다
            fos.write(bytes);
        }catch (Exception ex){
            throw ex;
        }
        finally {
            // 스트림을 꼭 닫아야한다
            if(fos != null) try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void write(Context context, String fileName, String content) throws IOException {
        write(context, fileName, content.getBytes());
    }

    public static String read(File file) throws FileNotFoundException {
        String s = "";

        FileInputStream fis = null;
        try{
            fis = new FileInputStream(file);

            // 2. 실제 파일 인코딩을 바꿔주는 래퍼 클래스 사용
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");

            // 3. 버퍼처리
            BufferedReader br = new BufferedReader(isr);

            String row;
            boolean isFirst = false;
            while((row = br.readLine()) != null){
                if(isFirst) {
                    s += row;
                    isFirst = false;
                }
                else s += "\n" + row;
            }

            br.close();
            isr.close();
        }catch (Exception ex){
            throw ex;
        }
        finally {
            // 스트림을 꼭 닫아야한다
            if(fis != null) try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return s;
        }
    }


}
