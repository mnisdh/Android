package android.daehoshin.com.memo.domain;

import android.content.Context;
import android.daehoshin.com.memo.DBHelper;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 9. 21..
 */

public class MemoDAO {
    private DBHelper dbHelper;
    Dao<Memo, Long> dao = null;

    public MemoDAO(Context context){
        dbHelper = new DBHelper(context);

        try {
            dao = dbHelper.getDao(Memo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(Memo memo){
        try {
            dao.create(memo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Memo select(long id){
        Memo memo = null;

        try {
            memo = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return memo;
    }

    public List<Memo> selectAll(){
        List<Memo> memos = new ArrayList<>();

        try {
            memos = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return memos;
    }

    public List<Memo> search(String word){
        List<Memo> memos = new ArrayList<>();

        String sql = String.format("select * from picnote where title like '%%%s%%'", word);
        try {
            GenericRawResults<Memo> temp = dao.queryRaw(sql,dao.getRawRowMapper());
            memos = temp.getResults();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return memos;
    }

    public void update(Memo memo){
        try {
            dao.update(memo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Memo memo){
        try {
            dao.delete(memo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(List<Memo> memos){
        try {
            dao.delete(memos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
