package android.daehoshin.com.dhlibrary.dir;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 10. 30..
 */

public class DDir {
    public static File[] getFiles(String path){
        List<String> list = new ArrayList<>();

        File dir = new File(path);
        if(dir.exists()) return dir.listFiles();

        return new File[]{};
    }
    public static void removeFiles(String path){
        File dir = new File(path);
        if(!dir.exists()) return;

        for(File file : dir.listFiles()){
            file.delete();
        }
    }
}
