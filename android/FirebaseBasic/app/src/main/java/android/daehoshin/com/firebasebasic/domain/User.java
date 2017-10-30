package android.daehoshin.com.firebasebasic.domain;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 10. 30..
 */

public class User {
    private String user_id;
    private String username;
    private int age;
    private String email;

    @Exclude
    private boolean isChecked = false;

    public List<Bbs> bbs = new ArrayList<>();

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, int age, String email) {
        this.username = username;
        this.age = age;
        this.email = email;
    }

    public String getUsername(){
        return username;
    }
    public int getAge(){
        return age;
    }
    public String getEmail(){
        return email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
