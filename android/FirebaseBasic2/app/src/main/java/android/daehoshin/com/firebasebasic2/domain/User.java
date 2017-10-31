package android.daehoshin.com.firebasebasic2.domain;

/**
 * Created by daeho on 2017. 10. 31..
 */

public class User {
    private String email;
    private String token;
    private boolean isChecked = false;

    public User(){

    }

    public User(String email, String token){
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
