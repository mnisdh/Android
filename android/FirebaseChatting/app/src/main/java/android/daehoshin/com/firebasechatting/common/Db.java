package android.daehoshin.com.firebasechatting.common;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by daeho on 2017. 11. 3..
 */

public class Db {
    private static Db db = null;
    public static Db getInstance(){
        if(db == null) db = new Db();

        return db;
    }

    private FirebaseDatabase database;

    private Db(){
        database = FirebaseDatabase.getInstance();
    }

    public DatabaseReference getReference(String name){
        return database.getReference(name);
    }

}
