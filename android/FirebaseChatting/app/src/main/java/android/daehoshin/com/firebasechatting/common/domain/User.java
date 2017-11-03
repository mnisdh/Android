package android.daehoshin.com.firebasechatting.common.domain;

import com.google.firebase.database.Exclude;

/**
 * Created by daeho on 2017. 11. 3..
 */

public class User {
    String id = "";
    String email = "";
    String name = "";
    String gender = "";
    String birethday = "";
    String phone_number = "";
    String token = "";
    @Exclude
    boolean isChecked = false;

    public User(){

    }

    public User(String id, String email, String token){
        this.id = id;
        this.email = email;
        this.token = token;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getBirethday() {
        return birethday;
    }
    public void setBirethday(String birethday) {
        this.birethday = birethday;
    }
    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public boolean isChecked() {
        return isChecked;
    }
    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
