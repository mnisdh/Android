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
