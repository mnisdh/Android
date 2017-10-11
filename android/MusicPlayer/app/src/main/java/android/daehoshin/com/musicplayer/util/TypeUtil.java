package android.daehoshin.com.musicplayer.util;

/**
 * Created by daeho on 2017. 10. 11..
 */

public class TypeUtil {
    public static String miliToSec(int milisecond){
        int sec = milisecond / 1000;
        int min = sec / 60;
        sec = sec % 60;

        return String.format("%02d", min) + ":" + String.format("%02d", sec);
    }
}
