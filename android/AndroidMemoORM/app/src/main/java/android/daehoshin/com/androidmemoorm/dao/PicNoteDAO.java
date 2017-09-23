package android.daehoshin.com.androidmemoorm.dao;

import android.content.Context;
import android.daehoshin.com.androidmemoorm.DBHelper;
import android.daehoshin.com.androidmemoorm.model.PicNote;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 9. 22..
 */

public class PicNoteDAO {
    private DBHelper dbHelper;
    Dao<PicNote, Long> dao = null;

    public PicNoteDAO(Context context){
        dbHelper = new DBHelper(context);

        try {
            dao = dbHelper.getDao(PicNote.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(PicNote picNote){
        try {
            dao.create(picNote);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PicNote select(long id){
        PicNote picNote = null;

        try {
            picNote = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return picNote;
    }

    public List<PicNote> selectAll(){
        List<PicNote> notes = new ArrayList<>();

        try {
            notes = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notes;
    }

    public List<PicNote> search(String word){
        List<PicNote> notes = new ArrayList<>();

        String sql = String.format("select * from picnote where title like '%%%s%%'", word);
        try {
            GenericRawResults<PicNote> temp = dao.queryRaw(sql,dao.getRawRowMapper());
            notes = temp.getResults();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notes;
    }

    public void update(PicNote picNote){
        try {
            dao.update(picNote);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(PicNote picNote){
        try {
            dao.delete(picNote);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
