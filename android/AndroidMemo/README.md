# Android memo

## List view, sqlite를 활용한 메모앱

### ListActivity.java

```java
package android.daehoshin.com.androidmemo;

import android.content.Intent;
import android.daehoshin.com.androidmemo.domain.MemoAdapter;
import android.daehoshin.com.androidmemo.domain.MemoDAO;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;


public class ListActivity extends AppCompatActivity {
    MemoDAO memoDAO = null;
    ListView lvMemo;
    MemoAdapter adapter;

    final int NEW_MEMO_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        init();
    }

    private void init(){
        if(memoDAO == null) memoDAO = new MemoDAO(this);

        // 2.데이터와 리스트뷰를 연결하는 아답터를 생성
        adapter = new MemoAdapter(this);

        // 3.아답터와 리스트뷰를 연결
        lvMemo = (ListView) findViewById(R.id.lvMemo);
        lvMemo.setAdapter(adapter);

        // 버튼 이벤트 연결
        findViewById(R.id.btnPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("no", adapter.getCount() + 1);
                startActivityForResult(intent, NEW_MEMO_REQUEST);
            }
        });
        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadListView();
            }
        });

        loadListView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case NEW_MEMO_REQUEST:
                if(resultCode == RESULT_OK) {
                    int id = data.getIntExtra("id", -1);
                    adapter.insert(0, memoDAO.getMemo(id));
                }
                break;
        }
    }

    private void loadListView(){
        try {
            adapter.update();
        } catch (IOException e) {
            Toast.makeText(this, "업데이트 오류발생", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        if(memoDAO != null) memoDAO.close();

        super.onDestroy();
    }
}
```


### DetailActivity.java

```java
package android.daehoshin.com.androidmemo;

import android.content.Intent;
import android.daehoshin.com.androidmemo.domain.Memo;
import android.daehoshin.com.androidmemo.domain.MemoDAO;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    MemoDAO memoDAO = null;

    TextView tvText;
    EditText etTitle, etName, etContext, etDatetime;
    Button btnPost, btnDelete;
    Switch swUpdate;

    //private static final String DIR_INTR = "/data/data/android.daehoshin.com.androidmemo/files/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
    }

    private Memo getMemo(){
        Memo memo = new Memo();
        memo.setTitle(etTitle.getText().toString());
        memo.setAuthor(etName.getText().toString());
        memo.setContent(etContext.getText().toString());
        memo.setDatetime(System.currentTimeMillis());

        return memo;
    }

    private void init(){
        if(memoDAO == null) memoDAO = new MemoDAO(this);

        tvText = (TextView) findViewById(R.id.tvText);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etName = (EditText) findViewById(R.id.etName);
        etContext = (EditText) findViewById(R.id.etContext);
        etDatetime = (EditText) findViewById(R.id.etDatetime);

        swUpdate = (Switch) findViewById(R.id.swUpdate);
        swUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setUpdateMode(isChecked);
            }
        });

        btnPost = (Button) findViewById(R.id.btnPost);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnPost.setOnClickListener(listener);
        btnDelete.setOnClickListener(listener);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        if(id < 0) setAddMode();
        else{
            Memo memo = memoDAO.getMemo(id);
            setViewMode(memo);
            setUpdateMode(false);
        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        /**
         * 내용을 파일에 작성
         * - 파일 쓰기
         *   내부저장소 - Internal : 개별앱만 접근가능 파일탐색기에서 보이지 않음
         *   외부저장소 - External : 모든앱이 접근가능하나 권한 필요
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnPost: post(v); break;
                case R.id.btnDelete: delete(v); break;
            }

        }
    };

    private void post(View v){
        Memo memo = getMemo();
        try {
            // 내용을 파일에 쓴다
            // 내부저장소 경로 : /data/data/패키지명/files
            //String filename = System.currentTimeMillis() + ".txt";
            //File file = new File(DIR_INTR + filename);

            // 내용을 파일에 쓴다
            //memo.save(getApplicationContext().getFilesDir().getAbsolutePath() + File.separator + filename);
            memoDAO.addMemo(memo);

            Intent intent = new Intent();
            intent.putExtra("id", memo.getId());
            setResult(RESULT_OK, intent);
        }catch (Exception ex){
            Toast.makeText(v.getContext(), "에러 : " + ex.toString(), Toast.LENGTH_LONG).show();
        }
        finally {
            finish();
        }
    }

    private void delete(View v){

    }

    private void setUpdateMode(boolean use){
        setClickable(etTitle, use);
        setClickable(etName, use);
        setClickable(etDatetime, use);
        setClickable(etContext, use);

        if(use) {
            btnPost.setVisibility(View.VISIBLE);

        }
        else {
            btnPost.setVisibility(View.INVISIBLE);
        }
    }

    private void setAddMode(){
        tvText.setText("New post");

        btnDelete.setVisibility(View.GONE);
        swUpdate.setVisibility(View.GONE);
        etDatetime.setVisibility(View.GONE);
    }

    private void setViewMode(Memo memo){
        tvText.setText("Post detail");

        setEditText(etTitle, memo.getTitle());
        setEditText(etName, memo.getAuthor());
        setEditText(etDatetime, memo.getFormatedDatetime());
        setEditText(etContext, memo.getContent());
    }

    private void setEditText(EditText et, String text){
        et.setText(text);
        setClickable(et, false);
    }

    private void setClickable(View v, boolean use){
        v.setFocusable(use);
        v.setClickable(use);
    }

    @Override
    protected void onDestroy() {
        if(memoDAO != null) memoDAO.close();

        super.onDestroy();
    }
}
```


### Memo.java

``` java
package android.daehoshin.com.androidmemo.domain;

import android.content.Context;
import android.daehoshin.com.androidmemo.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daeho on 2017. 9. 19..
 */

public class Memo {
    private final String SEP = "@#@";

    private int id;
    public int getId(){return id;}
    public void setId(int id){
        this.id = id;
    }

    private String title;
    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}

    private String author;
    public String getAuthor(){return author;}
    public void setAuthor(String author){this.author = author;}

    private String content;
    public String getContent(){return content;}
    public void setContent(String content){this.content = content;}

    private long datetime;
    public long getDatetime(){return datetime;}
    public String getFormatedDatetime(){
        return getFormatedDatetime("yyyy-MM-dd hh:mm:ss");
    }
    public String getFormatedDatetime(String format){
        Date date = new Date(datetime);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    public void setDatetime(long datetime){this.datetime = datetime;}

    public Memo() { }
    public Memo(int id){
        this.id = id;
    }
    public Memo(File file) throws IOException {
        try {
            String s = FileUtil.read(file);
            String[] rows = s.split("[\\r\\n]+");
            //String[] rows = s.split("\n");

            for(String row : rows){
                String[] cells = row.split(SEP);

                String key = "";
                String value = "";
                if(cells.length == 2){
                    key = cells[0];
                    value = cells[1];
                }
                else{
                    key = "";
                    value = cells[0];
                }

                switch (key){
                    case "no": this.id = Integer.parseInt(value); break;
                    case "title": this.title = value; break;
                    case "author": this.author = value; break;
                    case "datetime": this.datetime = Long.parseLong(value); break;
                    case "content": this.content += value; break;
                    default: this.content += "\n" + value; break;
                }
            }

        }catch (Exception e){
            throw e;
        }

    }

    private void save(Context context){
        MemoDAO memoDAO = new MemoDAO(context);
        memoDAO.addMemo(this);
        memoDAO.close();
    }
    public void save(String fileName) throws IOException {
        try {
            FileUtil.write(fileName, toBytes());
        } catch (IOException e) {
            throw e;
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("no").append(SEP).append(id).append("\n");
        sb.append("title").append(SEP).append(title).append("\n");
        sb.append("author").append(SEP).append(author).append("\n");
        sb.append("datetime").append(SEP).append(datetime).append("\n");
        sb.append("content").append(SEP).append(content).append("\n");

        return sb.toString();
    }

    public byte[] toBytes(){
        return toString().getBytes();
    }

}
```


### MemoAdapter.java

```java
package android.daehoshin.com.androidmemo.domain;

import android.content.Context;
import android.content.Intent;
import android.daehoshin.com.androidmemo.DetailActivity;
import android.daehoshin.com.androidmemo.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 9. 19..
 */

public class MemoAdapter extends BaseAdapter {
    private Context context;
    private List<Memo> memos = new ArrayList<>();
    private MemoDAO memoDAO = null;

    public MemoAdapter(Context context){
        if(memoDAO == null) memoDAO = new MemoDAO(context);
        this.context = context;
    }

    public void insert(int index, Memo memo){
        memos.add(index, memo);
        this.notifyDataSetChanged();
    }

    public void update() throws IOException {
        memos.clear();
        // 파일목록 가져오기
        // 1. 파일이 있는 디렉토리 경로를 가져온다
        //for(File file : DirUtil.getFiles(context.getFilesDir().getAbsolutePath() + "/")){
        //    Memo memo = null;
        //    try {
        //        memo = new Memo(file);
        //        memos.add(memo);
        //    } catch (IOException e) {
        //        throw e;
        //    }
        //}

        memos = memoDAO.getMemos(true);

        this.notifyDataSetChanged();
    }

    public void clear(){
        //DirUtil.removeFiles(context.getFilesDir().getAbsolutePath());
        memoDAO.deleteAllMemo();
        memos.clear();

        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return memos.size();
    }

    @Override
    public Object getItem(int position) {
        return memos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView == null) { // 아이템 convertView를 재사용하기위해 null체크
            // 레이아웃 인플레이터로 xml 파일을 View 객체로 변환
            convertView = LayoutInflater.from(context).inflate(R.layout.lv_memo, null);

            holder = new Holder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        holder.setMemo(memos.get(position));

        return convertView;
    }

    class Holder{
        private TextView tvNo, tvTitle, tvDatetime;
        private Memo memo;
        private Intent intent = null;

        public Holder(View v){
            tvNo = (TextView) v.findViewById(R.id.tvNo);
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            v.setOnClickListener(new View.OnClickListener() {
                // 화면에 보여지는 View는
                // 기본적으로 자신이 속한 컴포넌트의 컨텍스트를 그대로 가지고 있다
                @Override
                public void onClick(View v) {
                    if(intent == null) intent = new Intent(v.getContext(), DetailActivity.class);

                    intent.putExtra("id", memo.getId());
                    v.getContext().startActivity(intent);
                }
            });
            tvDatetime = (TextView) v.findViewById(R.id.tvDatetime);
        }

        public void setMemo(Memo memo){
            this.memo = memo;

            setTvNo(memo.getId());
            setTvTitle(memo.getTitle());
            setTvDatetime(memo.getFormatedDatetime());
        }

        public void setTvNo(int no){
            tvNo.setText(no + "");
        }
        public TextView getTvNo(){ return tvNo; }

        public void setTvTitle(String title){
            tvTitle.setText(title);
        }
        public TextView getTvTitle(){
            return tvTitle;
        }

        public void setTvDatetime(String datetime){
            tvDatetime.setText(datetime);
        }
        public TextView getTvDatetime(){
            return tvDatetime;
        }
    }
}
```


### MemoDAO.java

```java
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
```
