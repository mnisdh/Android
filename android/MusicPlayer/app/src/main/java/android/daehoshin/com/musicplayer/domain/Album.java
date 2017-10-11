package android.daehoshin.com.musicplayer.domain;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 10. 11..
 */

public class Album {
    private static Album album = null;
    public static Album getInstance(){
        if(album == null) album = new Album();

        return album;
    }

    private Album(){}; // 생성할 수 없도록 방지

    private List<Item> data = new ArrayList<>();

    public List<Item> getData(){
        return data;
    }

    /**
     * 앨범 데이터를 불러오는 함수
     * @param context
     */
    public void load(Context context) {
        ContentResolver resolver = context.getContentResolver();

        // 1. 테이블명 정의
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

        // 2. 불러올 컬럼명 정의
        String[] proj = {MediaStore.Audio.Albums.ALBUM_ID
                , MediaStore.Audio.Albums.ALBUM_KEY
                , MediaStore.Audio.Albums.ALBUM
                , MediaStore.Audio.Albums.ARTIST
                , MediaStore.Audio.Albums.NUMBER_OF_SONGS
                , MediaStore.Audio.Albums.FIRST_YEAR
                , MediaStore.Audio.Albums.LAST_YEAR};

        // 3. 쿼리
        Cursor cursor = resolver.query(uri, proj, null, null, MediaStore.Audio.Albums.ALBUM + " ASC");

        // 4. 쿼리결과가 담긴 커서를 통해 데이터 꺼내기
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Item item = new Item();
                item.id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID));
                item.key = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_KEY));
                item.key = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                item.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
                item.songCount = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
                item.songCount = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR));
                item.songCount = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums.LAST_YEAR));

                item.albumUri = Uri.withAppendedPath(uri, item.id);

                data.add(item);
            }

            cursor.close();
        }
    }

    /**
     * 실제 앨범 데이터
     */
    public class Item{
        public String id;
        public String key;
        public String name;
        public String artist;
        public int songCount;
        public int firstYear;
        public int lastYear;

        public Uri albumUri; // 앨범이미지 주소
    }
}
