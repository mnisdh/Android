package android.daehoshin.com.library.Util;

import java.util.List;

/**
 * Created by daeho on 2017. 9. 21..
 */

public class SqliteUtil {
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

}
