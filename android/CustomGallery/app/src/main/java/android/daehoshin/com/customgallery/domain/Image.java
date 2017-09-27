package android.daehoshin.com.customgallery.domain;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daeho on 2017. 9. 26..
 */

public class Image {
    private String id;
    private Long date;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Uri getOriUri() { return Uri.parse(path); }

    // Content Resolver를 통해서 이미지 Uri를 가져온다
    public Uri getThumbnailUri(Context context){
        ContentResolver resolver = context.getContentResolver();
        Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        String proj[] = { MediaStore.Images.Thumbnails.DATA };

        int tempId = Integer.parseInt(id) * -1;

        Cursor cursor = resolver.query(uri, proj, MediaStore.Images.Thumbnails.IMAGE_ID + "=?", new String[]{ id }, null);
        String temp = "";
        if(cursor != null){
            if(cursor.moveToFirst() && cursor.getCount() > 0){
                temp = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
            }
        }

        if(temp.equals("")){
            Cursor cursor2 = resolver.query(uri, proj, MediaStore.Images.Thumbnails._ID + "=?", new String[]{ id }, null);
            if(cursor2 != null){
                if(cursor2.moveToFirst() && cursor2.getCount() > 0){
                    temp = cursor2.getString(cursor2.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
                }
            }
        }

        return Uri.parse(temp);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getFormatedDatetime(){
        Date temp = new Date(this.date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(temp);
    }
}
