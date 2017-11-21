package android.daehoshin.com.realm;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by daeho on 2017. 11. 17..
 */

public class RealmApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}
