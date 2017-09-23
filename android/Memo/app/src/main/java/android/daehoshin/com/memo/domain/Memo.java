package android.daehoshin.com.memo.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    private long create_date;

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

    public long getCreate_date() {
        return create_date;
    }

    public String getFormatedCreate_date(){ return getFormatedDatetime(create_date, "yyyy-MM-dd hh:mm:ss"); }

    public void setCreate_date(long cretae_date) {
        this.create_date = cretae_date;
    }

    public long getUpdate_date() {
        return update_date;
    }

    public String getFormatedUpdate_date(){ return getFormatedDatetime(update_date, "yyyy-MM-dd hh:mm:ss"); }

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

    private String getFormatedDatetime(long datetime, String format){
        Date date = new Date(datetime);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
