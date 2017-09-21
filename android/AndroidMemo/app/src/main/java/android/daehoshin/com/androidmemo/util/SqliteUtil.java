package android.daehoshin.com.androidmemo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by daeho on 2017. 9. 21..
 */

public class SqliteUtil extends SQLiteOpenHelper {
    private static final String DB_NAME = "sqlite.db";
    private static final int DB_VERSION = 1;

    private String CREATE_SQL = "";
    private String UPGRADE_SQL = "";

    public SqliteUtil(Context context, String createSql, String upgradeSql) {
        super(context, DB_NAME, null, DB_VERSION);

        CREATE_SQL = createSql;
        UPGRADE_SQL = upgradeSql;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 최초 생성할 테이블을 쿼리 실행으로 생성
        if(!CREATE_SQL.equals("")) db.execSQL(CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(!UPGRADE_SQL.equals("")) db.execSQL(UPGRADE_SQL);
    }

    private String createColumnNamesComma(List<String> columnNames){
        String sql = "";

        if(columnNames.size() == 0) return sql;

        for(int i = 0; i < columnNames.size(); i++) sql += "," + columnNames.get(i);

        return sql.substring(1);
    }
    private String createValuesComma(List<Object> values){
        String sql = "";

        if(values.size() == 0) return sql;

        for(int i = 0; i < values.size(); i++){
            Object value = values.get(i);

            if(value instanceof Integer) sql += "," + value;
            else if(value instanceof Long) sql += "," + value;
            else if(value instanceof Float) sql += "," + value;
            else if(value instanceof Double) sql += "," + value;
            else if(value instanceof String) sql += ",'" + value + "'";
        }

        return sql.substring(1);
    }
    private String createColValueSetComma(List<String> columnNames, List<Object> values){
        String sql = "";

        if(values.size() == 0) return sql;

        for(int i = 0; i < values.size(); i++){
            sql += "," + columnNames.get(i) + " = ";

            Object value = values.get(i);
            if(value instanceof Integer) sql += value;
            else if(value instanceof Long) sql += value;
            else if(value instanceof Float) sql += value;
            else if(value instanceof Double) sql += value;
            else if(value instanceof String) sql += "'" + value + "'";
        }

        return sql.substring(1);
    }

    public String createInsertSQL(String tableName, List<String> columnNames, List<Object> values){
        String sql = "";

        if(columnNames.size() == 0) return sql;
        if(columnNames.size() != values.size()) return sql;

        sql = "insert into " + tableName
            + "(" + createColumnNamesComma(columnNames)
            + ") values (" + createValuesComma(values) + ") ";

        return sql;
    }

    public String createUpdateSQL(String tableName, List<String> columnNames, List<Object> values, String where){
        String sql = "";

        if(columnNames.size() == 0) return sql;
        if(columnNames.size() != values.size()) return sql;

        if(where.startsWith("where")) where = where.substring(5);
        else if(where.startsWith(" where")) where = where.substring(6);

        sql = "update memo set " + createColValueSetComma(columnNames, values)
            + " where " + where;

        return sql;
    }

    public String createDeleteSQL(String tableName){
        String sql = "";

        sql = "delete from " + tableName;

        return sql;
    }
    public String createDeleteSQL(String tableName, String where){
        String sql = "";

        if(where.startsWith("where")) where = where.substring(5);
        else if(where.startsWith(" where")) where = where.substring(6);

        sql = createDeleteSQL(tableName) + " where " + where;

        return sql;
    }

    public String createSelectSQL(String tableName){
        String sql = "";

        sql = "select * from " + tableName;

        return sql;
    }
    public String createSelectSQL(String tableName, String where){
        String sql = "";

        if(where.startsWith("where")) where = where.substring(5);
        else if(where.startsWith(" where")) where = where.substring(6);

        sql = createSelectSQL(tableName) + " where " + where;

        return sql;
    }
    public String createSelectSQL(String tableName, List<String> columnNames){
        String sql = "";

        sql = "select " + createColumnNamesComma(columnNames) + " from " + tableName;

        return sql;
    }
    public String createSelectSQL(String tableName, List<String> columnNames, String where){
        String sql = "";

        sql = createSelectSQL(tableName, columnNames) + " Where " + where;

        return sql;
    }

    public void excuteSQL(String sql) {
        SQLiteDatabase conn = this.getWritableDatabase();

        conn.execSQL(sql);

        conn.close();
    }


}
