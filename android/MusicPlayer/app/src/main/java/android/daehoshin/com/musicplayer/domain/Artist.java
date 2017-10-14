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

public class Artist {
    private static Artist artist = null;
    public static Artist getInstance(){
        if(artist == null) artist = new Artist();

        return artist;
    }

    private Artist(){}; // 생성할 수 없도록 방지

    private Map<String, Item> data = new HashMap<>();

    public List<IMusicItem> getData(){
        List<IMusicItem> items = new ArrayList<>();

        for(Item item : data.values()) items.add(item);

        return items;
    }

    public Item getData(String artistKey){
        return data.get(artistKey);
    }

    /**
     * 가수 데이터를 불러오는 함수
     * @param context
     */
    public void load(Context context) {
        ContentResolver resolver = context.getContentResolver();

        loadData(resolver);
    }

    private void loadData(ContentResolver resolver){
        // 1. 테이블명 정의
        Uri uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;

        // 2. 불러올 컬럼명 정의
        String[] proj = { MediaStore.Audio.Artists._ID
                        , MediaStore.Audio.Artists.ARTIST
                        , MediaStore.Audio.Artists.NUMBER_OF_TRACKS
                        , MediaStore.Audio.Artists.NUMBER_OF_ALBUMS};

        // 3. 쿼리
        Cursor cursor = resolver.query(uri, proj, null, null, null);

        // 4. 쿼리결과가 담긴 커서를 통해 데이터 꺼내기
        if(cursor != null) {
            while (cursor.moveToNext()) {
                String key = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists._ID));

                if(!data.containsKey(key)) {
                    Item item = new Item();
                    item.key = key;
                    item.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
                    item.albumKeys = getAlbumKeys(resolver, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists._ID)));

                    data.put(key, item);
                }
            }

            cursor.close();
        }
    }

    private List<String> getAlbumKeys(ContentResolver resolver, String artistId){
        List<String> albumKeys = new ArrayList<>();

        // 1. 테이블명 정의
        Uri uri = MediaStore.Audio.Artists.Albums.getContentUri("external", Long.parseLong(artistId));

        // 2. 불러올 컬럼명 정의
        String[] proj = { MediaStore.Audio.Artists.Albums.ALBUM_KEY };

        // 3. 쿼리
        Cursor cursor = resolver.query(uri, proj, null, null, null);

        // 4. 쿼리결과가 담긴 커서를 통해 데이터 꺼내기
        if(cursor != null) {
            while (cursor.moveToNext()) {
                String key = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.Albums.ALBUM_KEY));
                albumKeys.add(key);
            }

            cursor.close();
        }

        return albumKeys;
    }

    /**
     * 실제 가수 데이터
     */
    public class Item implements IMusicItem{
        public String key;
        public String artist;
        public List<String> albumKeys = new ArrayList<>();

        @Override
        public ItemType getItemType() {
            return ItemType.ARTIST;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getTitle() {
            return artist;
        }

        public List<Album.Item> getAlbums(){
            return Album.getInstance().getData(albumKeys);
        }

        public List<IMusicItem> getTitles(){
            return Music.getInstance().getArtistData(key);
        }

        public Uri getLastAlbumArt(){
            if(albumKeys.size() == 0) return null;
            return Album.getInstance().getData(albumKeys.get(0)).albumArt;
        }

        public int getAllTitleCount(){
            int count = 0;
            for(Album.Item item : getAlbums()){
                count += item.numberOfSongs;
            }

            return count;
        }
    }
}
