package android.daehoshin.com.memo.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by daeho on 2017. 9. 19..
 */

@DatabaseTable(tableName = "memo")
public class Memo {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String title;

    @DatabaseField
    private String author;

    @DatabaseField
    private long cretae_date;

    @DatabaseField
    private long update_date;

    @DatabaseField
    private String content;

    @DatabaseField
    private String image_path;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getCretae_date() {
        return cretae_date;
    }

    public void setCretae_date(long cretae_date) {
        this.cretae_date = cretae_date;
    }

    public long getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(long update_date) {
        this.update_date = update_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
