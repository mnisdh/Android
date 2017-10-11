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

public class Artist {
    private static Artist artist = null;
    public static Artist getInstance(){
        if(artist == null) artist = new Artist();

        return artist;
    }

    private Artist(){}; // 생성할 수 없도록 방지

    private List<Item> data = new ArrayList<>();

    public List<Item> getData(){
        return data;
    }

    /**
     * 가수 데이터를 불러오는 함수
     * @param context
     */
    public void load(Context context) {
        ContentResolver resolver = context.getContentResolver();

        // 1. 테이블명 정의
        Uri uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;

        // 2. 불러올 컬럼명 정의
        String[] proj = {MediaStore.Audio.Artists.ARTIST_KEY
                , MediaStore.Audio.Artists.ARTIST};

        // 3. 쿼리
        Cursor cursor = resolver.query(uri, proj, null, null, MediaStore.Audio.Artists.ARTIST + " ASC");

        // 4. 쿼리결과가 담긴 커서를 통해 데이터 꺼내기
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Item item = new Item();
                item.key = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST_KEY));
                item.name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));


                


                //item.albumKeys = Album.getInstance().getAlbumKey(item.key);
                //item.albumUri = Album.getInstance().getAlbumKey(item.key);
                //item.albumUri = Uri.withAppendedPath(uri, item.id);

                data.add(item);
            }

            cursor.close();
        }
    }

    /**
     * 실제 가수 데이터
     */
    public class Item{
        public String key;
        public String name;
        public List<String> albumKeys = new ArrayList<>();

        public Uri albumUri; // 앨범이미지 주소
    }
}
