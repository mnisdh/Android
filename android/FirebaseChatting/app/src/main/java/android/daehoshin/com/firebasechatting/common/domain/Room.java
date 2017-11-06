package android.daehoshin.com.firebasechatting.common.domain;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by daeho on 2017. 11. 6..
 */
@IgnoreExtraProperties
public class Room {
    private String id;
    private String title;
    private String last_msg;
    private long last_msg_dt;
    private long msg_count;
    private String create_user_id;
    private long create_dt;

    @Exclude
    private long read_msg_count;

    public Room(){

    }

    public Room(String id, String title){
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLast_msg() {
        return last_msg;
    }

    public void setLast_msg(String last_msg) {
        this.last_msg = last_msg;
    }

    public long getLast_msg_dt() {
        return last_msg_dt;
    }

    public void setLast_msg_dt(long last_msg_dt) {
        this.last_msg_dt = last_msg_dt;
    }

    public long getMsg_count() {
        return msg_count;
    }

    public void setMsg_count(long msg_count) {
        this.msg_count = msg_count;
    }

    public String getCreate_user_id() {
        return create_user_id;
    }

    public void setCreate_user_id(String create_user_id) {
        this.create_user_id = create_user_id;
    }

    public long getCreate_dt() {
        return create_dt;
    }

    public void setCreate_dt(long create_dt) {
        this.create_dt = create_dt;
    }

    public long getRead_msg_count() {
        return read_msg_count;
    }

    public void setRead_msg_count(long read_msg_count) {
        this.read_msg_count = read_msg_count;
    }
}
