package android.daehoshin.com.firebasebasic.domain;

/**
 * Created by daeho on 2017. 10. 30..
 */

public class Bbs {
    private String bbs_id;
    private String title;
    private String content;
    private String date;
    private String user_id;

    public Bbs(){
        // 파이어베이스에서 parsing 할때 한번 호출된다
    }

    public Bbs(String title, String content, String date){
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public String getBbs_id() {
        return bbs_id;
    }

    public void setBbs_id(String bbs_id) {
        this.bbs_id = bbs_id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
