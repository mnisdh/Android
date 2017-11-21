package android.daehoshin.com.realm.domain;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by daeho on 2017. 11. 17..
 */

public class Bbs extends RealmObject {
    @PrimaryKey
    private long no;
    private String title;
    private String content;
    private String user;
    private long date;

    @Ignore
    private String test;

    public long getNo() {
        return no;
    }
    public void setNo(long no) {
        this.no = no;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }
}
