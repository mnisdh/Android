package android.daehoshin.com.musicplayer.domain;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Map<String, Item> data = new HashMap<>();

    public List<IMusicItem> getData(){
        List<IMusicItem> items = new ArrayList<>();

        for(Item item : data.values()) items.add(item);

        return items;
    }

    public List<Item> getData(List<String> albumKeys){
        List<Item> items = new ArrayList<>();
        for(String key : albumKeys){
            if(data.containsKey(key)) items.add(data.get(key));
        }

        return items;
    }

    public Item getData(String albumKey){
        return data.get(albumKey);
    }


    /**
     * 앨범 데이터를 불러오는 함수
     * @param context
     */
    public void load(Context context) {
        ContentResolver resolver = context.getContentResolver();

        loadData(resolver);
    }

    private void loadData(ContentResolver resolver){
        // 1. 테이블명 정의
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

        // 2. 불러올 컬럼명 정의
        String[] proj = { MediaStore.Audio.Albums.ALBUM_KEY
                , MediaStore.Audio.Albums.ALBUM
                , MediaStore.Audio.Albums.ALBUM_ART
                , MediaStore.Audio.Albums.ARTIST
                , MediaStore.Audio.Albums.FIRST_YEAR
                , MediaStore.Audio.Albums.LAST_YEAR
                , MediaStore.Audio.Albums.NUMBER_OF_SONGS};

        // 3. 쿼리
        Cursor cursor = resolver.query(uri, proj, null, null, null);

        // 4. 쿼리결과가 담긴 커서를 통해 데이터 꺼내기
        if(cursor != null) {
            while (cursor.moveToNext()) {
                String key = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_KEY));

                if(!data.containsKey(key)) {
                    Album.Item item = new Album.Item();
                    item.key = key;
                    item.album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                    item.albumArt = Uri.parse(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
                    item.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
                    item.firstYear = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR));
                    item.lastYear = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums.LAST_YEAR));
                    item.numberOfSongs = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));

                    data.put(key, item);
                }
            }

            cursor.close();
        }
    }

    /**
     * 실제 앨범 데이터
     */
    public class Item implements IMusicItem{
        public String key;
        public String album;
        public Uri albumArt;
        public String artist;
        public int firstYear;
        public int lastYear;
        public int numberOfSongs;

        @Override
        public ItemType getItemType() {
            return ItemType.ALBUM;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getTitle() {
            return album;
        }

        public List<IMusicItem> getTitles(){
            return Music.getInstance().getAlbumData(key);
        }
    }
}
