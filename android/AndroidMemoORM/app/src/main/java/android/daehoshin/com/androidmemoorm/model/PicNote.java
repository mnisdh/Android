package android.daehoshin.com.androidmemoorm.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by daeho on 2017. 9. 22..
 */
@DatabaseTable(tableName = "picnote")
public class PicNote {
    @DatabaseField
    private long id;

    @DatabaseField
    private String title;

    @DatabaseField
    private String imagePath;

    @DatabaseField
    private long datetime;

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }
}
