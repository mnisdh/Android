package android.daehoshin.com.androidmemo2.domain;

import android.content.Context;
import android.daehoshin.com.androidmemo2.DBHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO Data Access Object
 * 데이터 조작을 담당 (CRUD)
 *
 * Created by daeho on 2017. 9. 21..
 */

public class MemoDAO{
    // 1. 데이터베이스에 연결
    // 2. 조작
    // 3. 연결을 해제

    private DBHelper dbHelper = null;

    public MemoDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    private SQLiteDatabase getReadableConnection(){

        return dbHelper.getReadableDatabase();
    }
    private SQLiteDatabase getWritableConnection(){

        return dbHelper.getWritableDatabase();
    }
    private void closeConnection(SQLiteDatabase db){
        db.close();
    }

    private void excuteSql(String sql){
        SQLiteDatabase conn = getWritableConnection();

        conn.execSQL(sql);

        closeConnection(conn);
    }

    public void create(Memo memo){
        String sql = "insert into memo(title, content) values("
                   + "'"  + memo.getTitle()   + "'"
                   + ",'" + memo.getContent() + "') ";

        excuteSql(sql);
    }

    public Memo select(int id){
        return new Memo();
    }

    public List<Memo> select(){
        List<Memo> memos = new ArrayList<>();

        String sql = "select * from memo";

        SQLiteDatabase conn = getReadableConnection();
        Cursor cursor = conn.rawQuery(sql, null);

        while (cursor.moveToNext()){
            Memo memo = new Memo();
            for(int i = 0; i < cursor.getColumnCount(); i++){
                switch (cursor.getColumnName(i)){
                    case "id": memo.setId(cursor.getInt(i));
                        break;
                    case "title": memo.setTitle(cursor.getString(i));
                        break;
                    case "content": memo.setContent(cursor.getString(i));
                        break;
                }
            }
            memos.add(memo);
        }

        return memos;
    }

    public void update(Memo memo){
        String sql = "update memo set title = '" +
                memo.getTitle() +
                "' , content = '" +
                memo.getContent() +
                "' where id = "+ memo.getId();

        excuteSql(sql);
    }

    public void delete(int id){
        String sql = "delete from memo where id =" + id;

        excuteSql(sql);
    }

    public void close(){ dbHelper.close(); }
}
