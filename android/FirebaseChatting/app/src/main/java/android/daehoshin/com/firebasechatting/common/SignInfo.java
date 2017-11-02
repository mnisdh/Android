package android.daehoshin.com.firebasechatting.common;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by daeho on 2017. 11. 2..
 */

public class SignInfo {
    private FirebaseAuth mAuth;

    private static SignInfo info = null;
    public static SignInfo getInstance(){
        if(info == null) info = new SignInfo();
        return info;
    }

    private SignInfo(){
        mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getAuth(){
        return mAuth;
    }
}
