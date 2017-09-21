package android.daehoshin.com.androidmemo.domain;

import android.content.Context;
import android.daehoshin.com.androidmemo.util.SqliteUtil;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 9. 21..
 */

public class MemoDAO {
    private SqliteUtil sUtil = null;
    private final String TABLE_NAME = "memo";

    private final String createSql = " CREATE TABLE `memo` (\n"
                                   + " `id`	            INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                                   + " `title`	        TEXT,\n"
                                   + " `author`	        TEXT,\n"
                                   + " `content`	    TEXT,\n"
                                   + " `create_date`	NUMERIC,\n"
                                   + " `update_date`	NUMERIC\n"
                                   + " )";
    private final String upgradeSql = "";

    public MemoDAO(Context context) { sUtil = new SqliteUtil(context, createSql, upgradeSql); }

    public void addMemo(Memo memo){
        List<String> columnNames = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        columnNames.add("title");    values.add(memo.getTitle());
        columnNames.add("create_date"); values.add(memo.getDatetime());
        columnNames.add("author");   values.add(memo.getAuthor());
        columnNames.add("content");  values.add(memo.getContent());

        String sql = sUtil.createInsertSQL(TABLE_NAME, columnNames, values);
        sUtil.excuteSQL(sql);

        memo.setId(getLastId());
    }

    public void updateMemo(Memo memo){
        List<String> columnNames = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        columnNames.add("title");    values.add(memo.getTitle());
        columnNames.add("update_date"); values.add(memo.getDatetime());
        columnNames.add("author");   values.add(memo.getAuthor());
        columnNames.add("content");  values.add(memo.getContent());
        String where = "id = " + memo.getId();

        String sql = sUtil.createUpdateSQL(TABLE_NAME, columnNames, values, where);
        sUtil.excuteSQL(sql);
    }

    public void deleteMemo(int id){
        String sql = "";

        String where = "id = " + id;

        sql = sUtil.createDeleteSQL(TABLE_NAME, where);
        sUtil.excuteSQL(sql);
    }
    public void deleteMemo(Memo memo){
        deleteMemo(memo.getId());
    }
    public void deleteAllMemo(){
        String sql = sUtil.createDeleteSQL(TABLE_NAME);

        sUtil.excuteSQL(sql);
    }

    private int getLastId(){
        SQLiteDatabase conn = sUtil.getReadableDatabase();
        Cursor cursor = conn.rawQuery("select max(id) from " + TABLE_NAME, null);

        int max = 0;
        while (cursor.moveToNext()){
            max = cursor.getInt(0);
        }

        cursor.close();
        conn.close();

        return max;
    }

    public Memo getMemo(int id){
        Memo memo = null;

        String where = "id = " + id;
        String sql = sUtil.createSelectSQL(TABLE_NAME, where);

        SQLiteDatabase conn = sUtil.getReadableDatabase();
        Cursor cursor = conn.rawQuery(sql, null);

        while (cursor.moveToNext()){
            memo = new Memo();
            for(int i = 0; i < cursor.getColumnCount(); i++){
                switch (cursor.getColumnName(i)){
                    case "id": memo.setId(cursor.getInt(i)); break;
                    case "title": memo.setTitle(cursor.getString(i)); break;
                    case "author": memo.setAuthor(cursor.getString(i)); break;
                    case "update_date": memo.setDatetime(cursor.getLong(i)); break;
                    case "content": memo.setContent(cursor.getString(i)); break;
                    case "create_date":
                        if(memo.getDatetime() == 0) memo.setDatetime(cursor.getLong(i));
                        break;
                }
            }
        }
        cursor.close();
        conn.close();

        return memo;
    }
    public List<Memo> getMemos(boolean isDESC){
        List<Memo> memos = new ArrayList<>();

        String sql = sUtil.createSelectSQL(TABLE_NAME);
        if(isDESC) sql += " order by id desc";

        SQLiteDatabase conn = sUtil.getReadableDatabase();
        Cursor cursor = conn.rawQuery(sql, null);

        while (cursor.moveToNext()){
            Memo memo = new Memo();
            for(int i = 0; i < cursor.getColumnCount(); i++){
                switch (cursor.getColumnName(i)){
                    case "id": memo.setId(cursor.getInt(i)); break;
                    case "title": memo.setTitle(cursor.getString(i)); break;
                    case "author": memo.setAuthor(cursor.getString(i)); break;
                    case "update_date": memo.setDatetime(cursor.getLong(i)); break;
                    case "content": memo.setContent(cursor.getString(i)); break;
                    case "create_date":
                        if(memo.getDatetime() == 0) memo.setDatetime(cursor.getLong(i));
                        break;
                }
            }
            memos.add(memo);
        }
        cursor.close();
        conn.close();

        return memos;
    }

    public void close() { sUtil.close(); }
}
