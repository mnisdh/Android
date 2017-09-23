package android.daehoshin.com.memo;

import android.content.Context;
import android.daehoshin.com.memo.domain.Memo;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by daeho on 2017. 9. 22..
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {
    public static final String DB_NAME = "ormlite.db";
    public static final int DB_VER = 1;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            // Memo.class 를 참조해서 테이블을 생성함
            TableUtils.createTable(connectionSource, Memo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
