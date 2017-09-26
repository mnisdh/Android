package android.daehoshin.com.camera.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by daeho on 2017. 9. 21..
 */

public class TypeUtil {
    public static byte[] toByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream) ;
        byte[] byteArray = stream.toByteArray() ;
        return byteArray ;
    }

    public static Bitmap toBitmap(byte[] bytes){
        Bitmap bitmap = BitmapFactory.decodeByteArray( bytes, 0, bytes.length ) ;
        return bitmap ;
    }
}
