package android.daehoshin.com.musicplayer.domain;

/**
 * Created by daeho on 2017. 10. 12..
 */

public interface IMusicItem {
    public static enum ItemType{
        TITLE, ALBUM, ARTIST
    }

    public ItemType getItemType();

    public String getKey();
    public String getTitle();
}
