# Android memo2

## DAO를 이용해 sqlite 사용한 Android memo

### ListActivity.java

```java
package android.daehoshin.com.androidmemo2;

import android.daehoshin.com.androidmemo2.domain.Memo;
import android.daehoshin.com.androidmemo2.domain.MemoDAO;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * 안드로이드 sqlite 사용하기
 * 1. db파일을 직접 코드로 생성
 * 2. 로컬에서 만든 파일을 assets에 담은후 복사 붙여넣기
 *    > 우편번호처럼 기반 데이터가 필요한 db일 경우
 */
public class ListActivity extends AppCompatActivity {
    EditText etId, etTitle, etContent;
    TextView tvResult;
    MemoDAO memoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        init();
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnCreate: create(); break;
                case R.id.btnSelect: select(); break;
                case R.id.btnUpdate: update(); break;
                case R.id.btnDelete: delete(); break;
            }
        }
    };

    private void create(){
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();

        Memo memo = new Memo();
        memo.setTitle(title);
        memo.setContent(content);

        memoDAO.create(memo);

        etTitle.setText("");
        etContent.setText("");
    }

    private void select(){
        List<Memo> memos = memoDAO.select();

        String s = "";
        for(Memo memo : memos){
            s += "id : " + memo.getId() + " / title : " + memo.getTitle() + " / content : " + memo.getContent() + "\n";
        }

        tvResult.setText(s);
    }

    private void update(){
        Memo memo = new Memo();
        memo.setId(Integer.parseInt(etId.getText().toString()));
        memo.setTitle(etTitle.getText().toString());
        memo.setContent(etContent.getText().toString());

        memoDAO.update(memo);
    }

    private void delete(){
        memoDAO.delete(Integer.parseInt(etId.getText().toString()));
    }

    private void init(){
        memoDAO = new MemoDAO(this);

        etId = (EditText) findViewById(R.id.etId);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);
        tvResult = (TextView) findViewById(R.id.tvResult);

        findViewById(R.id.btnCreate).setOnClickListener(onClickListener);
        findViewById(R.id.btnSelect).setOnClickListener(onClickListener);
        findViewById(R.id.btnUpdate).setOnClickListener(onClickListener);
        findViewById(R.id.btnDelete).setOnClickListener(onClickListener);
    }

    private void initDB(){
        // 1. 데이터베이스 연결
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase conn = dbHelper.getWritableDatabase();

        // 2. 데이터 넣기
        String insertSql = " INSERT INTO memo(title, author, content, create_date)"
                         + " VALUES('제목','이름','내용입니다', " + System.currentTimeMillis() + ")";
        conn.execSQL(insertSql);

        // 3. 데이터 읽기
        conn = dbHelper.getReadableDatabase();
        String selectSql = " select * from memo ";
        Cursor cursor = conn.rawQuery(selectSql, null);

        while (cursor.moveToNext()){
            for(int i = 0; i < cursor.getColumnCount(); i++){
                switch (cursor.getColumnName(i)){
                    case "no": cursor.getInt(i);
                        break;
                    case "title": cursor.getString(i);
                        break;
                    case "author": cursor.getString(i);
                        break;
                    case "contnet": cursor.getString(i);
                        break;
                }
            }
        }
    }
}

```


### DBHelper.java

```java
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
```


### Memo.java

```java
package android.daehoshin.com.androidmemo2.domain;

/**
 * Created by daeho on 2017. 9. 21..
 */

public class Memo {
    private int id;
    private String title;
    private String author;
    private long datetime;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
```


### MemoDAO.java

```java
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
```
