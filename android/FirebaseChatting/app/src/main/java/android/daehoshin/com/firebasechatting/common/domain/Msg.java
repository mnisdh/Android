package android.daehoshin.com.firebasechatting.common.domain;

/**
 * Created by daeho on 2017. 11. 7..
 */

public class Msg {
    /*

idx	0
type	[string] or [file] or [image] or [movie]
msg	내용 or 경로
user_id	전송자
time	전송시간

     */

    private String room_id;
    private long idx;
    private String type;
    private String msg;
    private String user_id;
    private String user_email;
    private long time;

    public Msg(){

    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public long getIdx() {
        return idx;
    }

    public void setIdx(long idx) {
        this.idx = idx;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
