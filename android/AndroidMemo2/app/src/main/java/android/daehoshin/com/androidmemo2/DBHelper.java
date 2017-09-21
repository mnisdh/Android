package android.daehoshin.com.androidmemo2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by daeho on 2017. 9. 21..
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "sqlite.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);

        // super에서 넘겨받은 데이터베이스가 생성되어 있는지 확인 한 후
        // 1. 없으면 onCreate를 호출
        // 2. 있으면 버전을 체크해서 생성되어 있는 DB보다 버전이 높으면 onUpgrade를 호출
    }

    /**
     * db가 없을때 호출됨
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 최초 생성할 테이블을 정의
        String createSql = " CREATE TABLE `memo` (\n"
                         + " `id`	INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                         + " `title`	TEXT,\n"
                         + " `author`	TEXT,\n"
                         + " `content`	TEXT,\n"
                         + " `create_date`	NUMERIC,\n"
                         + " `update_date`	NUMERIC\n"
                         + " )";

        // 쿼리를 실행해서 테이블 생성
        db.execSQL(createSql);
    }

    /**
     * db가 있는데 낮은 버전일때 호출됨
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
