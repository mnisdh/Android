package android.daehoshin.com.musicplayer.domain;

/**
 * Created by daeho on 2017. 10. 12..
 */

public interface IMusicItem {
    enum ItemType{
        TITLE, ALBUM, ARTIST
    }

    public ItemType getItemType();

    public String getKey();
    public String getTitle();
}
