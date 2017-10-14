package android.daehoshin.com.musicplayer.domain;

import android.content.ContentResolver;
import android.content.Context;
import android.daehoshin.com.musicplayer.player.Player;
import android.daehoshin.com.musicplayer.util.TypeUtil;
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

public class Music {
    private static Music music = null;
    public static Music getInstance(){
        if(music == null) music = new Music();

        return music;
    }

    private Music(){}; // 생성할 수 없도록 방지

    private Map<String, Item> data = new HashMap<>();

    public List<IMusicItem> getData(){
        List<IMusicItem> items = new ArrayList<>();

        for(Item item : data.values()) items.add(item);

        return items;
    }

    public List<Player.ItemData> getItemDatas(){
        List<Player.ItemData> items = new ArrayList<>();

        for(Item item : data.values()) items.add(item);

        return items;
    }

    public Item getTitle(String titleKey){
        return data.get(titleKey);
    }

    public List<IMusicItem> getArtistData(String artistKey){
        List<IMusicItem> items = new ArrayList<>();

        for(Item item : data.values()){
            if(item.artistKey.equals(artistKey)) items.add(item);
        }

        return items;
    }

    public List<IMusicItem> getAlbumData(String albumKey){
        List<IMusicItem> items = new ArrayList<>();

        for(Item item : data.values()){
            if(item.albumKey.equals(albumKey)) items.add(item);
        }

        return items;
    }

    public List<Player.ItemData> getCheckedData(){
        List<Player.ItemData> items = new ArrayList<>();

        for(Item item : data.values()){
            if(item.isChecked) {
                items.add(item);
                item.isChecked = false;
            }
        }

        return items;
    }

    /**
     * 음악 데이터를 불러오는 함수
     * @param context
     */
    public void load(Context context){
        ContentResolver resolver = context.getContentResolver();

        loadData(resolver);
    }

    private void loadData(ContentResolver resolver){
        // 1. 테이블명 정의
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        // 2. 불러올 컬럼명 정의
        String[] proj = { MediaStore.Audio.Media._ID
                        , MediaStore.Audio.Media.TITLE
                        , MediaStore.Audio.Media.ALBUM_KEY
                        , MediaStore.Audio.Media.ARTIST_ID
                        , MediaStore.Audio.Media.DURATION};

        String selection = MediaStore.Audio.Media.IS_MUSIC + " = ?";
        String[] args = { "1" };

        // 3. 쿼리
        Cursor cursor = resolver.query(uri, proj, selection, args, MediaStore.Audio.Media.TITLE + " ASC");

        // 4. 쿼리결과가 담긴 커서를 통해 데이터 꺼내기
        if(cursor != null) {
            while (cursor.moveToNext()) {
                String key = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));

                if(!data.containsKey(key)) {
                    Item item = new Item();
                    item.key = key;
                    item.title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    item.duration = TypeUtil.miliToSec((int) cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));

                    String titleID = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    item.titleUri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, titleID);


                    item.artistKey = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
                    item.artist = Artist.getInstance().getData(item.artistKey).artist;


                    item.albumKey = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_KEY));
                    Album.Item albumItem = Album.getInstance().getData(item.albumKey);
                    item.album = albumItem.album;
                    item.albumUri = albumItem.albumArt;

                    data.put(key, item);
                }
            }

            cursor.close();
        }
    }
//    private void loadTitle(ContentResolver resolver){
//        titleOrderKeys.clear();
//
//        // 1. 테이블명 정의
//        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//
//        // 2. 불러올 컬럼명 정의
//        String[] proj = { MediaStore.Audio.Media.TITLE_KEY};
//
//        String selection = MediaStore.Audio.Media.IS_MUSIC + " = ?";
//        String[] args = { "1" };
//
//        // 3. 쿼리
//        Cursor cursor = resolver.query(uri, proj, selection, args, MediaStore.Audio.Media.TITLE + " ASC");
//
//        // 4. 쿼리결과가 담긴 커서를 통해 데이터 꺼내기
//        if(cursor != null) {
//            while (cursor.moveToNext()) {
//                String key = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE_KEY));
//                titleOrderKeys.add(key);
//            }
//
//            cursor.close();
//        }
//    }
//    private void loadArtist(ContentResolver resolver){
//        artistOrderKeys.clear();
//
//        // 1. 테이블명 정의
//        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//
//        // 2. 불러올 컬럼명 정의
//        String[] proj = { MediaStore.Audio.Media.TITLE_KEY};
//
//        String selection = MediaStore.Audio.Media.IS_MUSIC + " = ?";
//        String[] args = { "1" };
//
//        // 3. 쿼리
//        Cursor cursor = resolver.query(uri, proj, selection, args, MediaStore.Audio.Media.ARTIST + " ASC");
//
//        // 4. 쿼리결과가 담긴 커서를 통해 데이터 꺼내기
//        if(cursor != null) {
//            while (cursor.moveToNext()) {
//                String key = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE_KEY));
//                artistOrderKeys.add(key);
//            }
//
//            cursor.close();
//        }
//    }
//    private void loadAlbum(ContentResolver resolver){
//        albumOrderKeys.clear();
//
//        // 1. 테이블명 정의
//        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//
//        // 2. 불러올 컬럼명 정의
//        String[] proj = { MediaStore.Audio.Media.TITLE_KEY};
//
//        String selection = MediaStore.Audio.Media.IS_MUSIC + " = ?";
//        String[] args = { "1" };
//
//        // 3. 쿼리
//        Cursor cursor = resolver.query(uri, proj, selection, args, MediaStore.Audio.Media.ALBUM + " ASC");
//
//        // 4. 쿼리결과가 담긴 커서를 통해 데이터 꺼내기
//        if(cursor != null) {
//            while (cursor.moveToNext()) {
//                String key = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE_KEY));
//                albumOrderKeys.add(key);
//            }
//
//            cursor.close();
//        }
//    }

    /**
     * 실제 뮤직 데이터
     */
    public class Item implements IMusicItem, Player.ItemData{
        public String key;
        public String title;
        public String albumKey;
        public String album;
        public String artistKey;
        public String artist;
        public String duration;

        public Uri titleUri;
        public Uri albumUri;

        public boolean isChecked = false;

        @Override
        public ItemType getItemType() {
            return ItemType.TITLE;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getArtist() {
            return artist;
        }

        @Override
        public String getAlbum() {
            return album;
        }

        @Override
        public Uri getTitleUri() {
            return titleUri;
        }

        @Override
        public Uri getAlbumUri() {
            return albumUri;
        }

        @Override
        public String getDuration() {
            return duration;
        }
    }
}
