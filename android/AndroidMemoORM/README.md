# Android memo ORM

## onDraw를 활용한 그림메모앱

### MainActivity.java

```java
package android.daehoshin.com.androidmemoorm;

import android.content.Intent;
import android.daehoshin.com.androidmemoorm.dao.PicNoteDAO;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    private PicNoteDAO dao;
    public static final int DRAW_ACTIVITY_FINISHED = 1;

    RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
    }

    /**
     * RecyclerView 를 사용한 목록 만들기
     *
     * 1. 데이터를 정의
     *
     * 2. 아답터를 정의
     *
     * 3. 아답터를 생성하면서 재정의한 데이터를 담는다
     *
     * 4. 아답터와 RecyclerView 컨테이너를 연결
     *
     * 5. RecyclerView에 레이아웃 매니저를 설정
     */
    private void initRecyclerView(){
        rvList = (RecyclerView) findViewById(R.id.rvList);

        dao = new PicNoteDAO(this);

        CustomAdapter adapter = new CustomAdapter();
        CustomAdapter.data = dao.selectAll();

        rvList.setAdapter(adapter);

        // RecyclerView에 레이아웃 매니저를 설정
        //  - LinearLayoutManager
        rvList.setLayoutManager(new LinearLayoutManager(this));
        //  - StaggeredGridLayoutManager
        //rvList.setLayoutManager(new StaggeredGridLayoutManager(2 ,StaggeredGridLayoutManager.VERTICAL));
        //  - GridLayoutManager
        //rvList.setLayoutManager(new GridLayoutManager(this, 3));
    }

    public void openDraw(View v){
        Intent intent = new Intent(this, DrawActivity.class);
        startActivityForResult(intent, DRAW_ACTIVITY_FINISHED);
    }
}
```


### DrawActivity.java

```java
package android.daehoshin.com.androidmemoorm;

import android.daehoshin.com.androidmemoorm.dao.PicNoteDAO;
import android.daehoshin.com.androidmemoorm.model.PicNote;
import android.daehoshin.com.androidmemoorm.util.FileUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class DrawActivity extends AppCompatActivity {
    private PicNoteDAO dao;

    FrameLayout stage;
    DrawView dv;
    SeekBar sbSize;
    EditText etTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        init();
    }

    private void init(){
        dao = new PicNoteDAO(this);

        // drawview를 추가할 레이아웃 설정
        stage = (FrameLayout) findViewById(R.id.stage);

        // drawview를 생성해서 레이아웃에 추가
        dv = new DrawView(this);
        stage.addView(dv);

        etTitle = (EditText) findViewById(R.id.etTitle);

        // 사이즈 설정을 위한 seekbar 설정 / 리스너 연결
        sbSize = (SeekBar)findViewById(R.id.sbSize);
        sbSize.setOnSeekBarChangeListener(seekBarChangeListener);

        // 컬러 설정을 위한 라디오그룹 설정 / 리스너 연결
        RadioGroup rgColor =(RadioGroup) findViewById(R.id.rgColor);
        rgColor.setOnCheckedChangeListener(radioGroupCheckedChangeListener);
    }


    /**
     * 그림을 그린 stage를 캡쳐
     *
     * @param view
     */
    public void captureCanvas(View view){
        // 0. 드로잉 캐쉬를 먼저 지워준다
        stage.destroyDrawingCache();

        // 1. 다시 만든다
        stage.buildDrawingCache();

        // 2. 레이아웃에서 그려진 내용을 bitmap 형식으로 가져옴
        Bitmap bitmap = stage.getDrawingCache();

        String fileName = System.currentTimeMillis() + ".jpg";
        // 이미지 파일을 저장하고
        try {
            // /data/data/패키지/files 안에 저장
            FileUtil.write(this, fileName, bitmap);
        } catch (IOException e) {
            Toast.makeText(this, "저장실패", Toast.LENGTH_LONG).show();
        }
        finally {
            // Native에 다 썻다고 알려준다
            bitmap.recycle();
        }

        // 데이터베이스에 경로도 저장
        PicNote picNote = new PicNote();
        picNote.setImagePath(fileName);
        picNote.setTitle(etTitle.getText().toString());
        picNote.setDatetime(System.currentTimeMillis());
        dao.create(picNote);

        //Intent intent = new Intent();
        //intent.putExtra("id", memo.getId());
        //setResult(RESULT_OK, intent);

        finish();
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // 사이즈 변경 메소드 호출
            dv.setSize(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    RadioGroup.OnCheckedChangeListener radioGroupCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            int color = Color.BLACK;
            int size = sbSize.getProgress();

            switch (checkedId){
                case R.id.rbtBlack: color = Color.BLACK; break;
                case R.id.rbtCyan: color = Color.CYAN; break;
                case R.id.rbtMagenta: color = Color.MAGENTA; break;
                case R.id.rbtYellow: color = Color.YELLOW; break;
            }

            // 컬러 변경 메소드 호출
            dv.setColor(color, size);
        }
    };



}
```


### DrawView.java

```java
package android.daehoshin.com.androidmemoorm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 9. 18..
 */

public class DrawView extends View {
    // 현재 사용중인 path 변수
    private PathTool currentPath;
    // 공통으로 사용될 paint 변수
    private Paint paint;

    // 컬러, 사이즈 별로 담아둘 path 리스트
    List<PathTool> pts = new ArrayList<>();

    // 소스코드에서만 사용하기 때문에 생성자 파라미터는 context만 필요
    public DrawView(Context context) {
        super(context);
        paint = new Paint();

        init();
    }

    private void init(){
        // 처음 사용될 컬러, 사이즈 설정
        setColor(Color.CYAN, 1);

        // 공통으로 적용되는 페인트 설정
        paint.setColor(currentPath.getColor());
        paint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 컬러, 사이즈 변경시 path 새로 생성하여 currentPath로 설정하는 메소드
     * @param color
     * @param size
     */
    public void setColor(int color, int size){
        // PathTool 새로생성 -> path list에 추가 -> 생성된path를 currentPath로 설정
        PathTool pt = new PathTool(color, size);
        pts.add(pt);
        currentPath = pt;
    }

    /**
     * 사이즈 변경시 path 새로 생성하여 currentPath로 설정하는 메소드
     * @param size
     */
    public void setSize(int size){
        // 사이즈 변경만 하므로 기존 컬러 가져와서 setColor로 재호출하여 동일하게 path설정
        setColor(currentPath.getColor(), size);
    }

    // 화면을 그려주는 onDraw 오버라이드

    /**
     * 화면을 그려주는 onDraw 오버라이드하여 담고있는 path list를 화면에 표시해줌
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(PathTool pt : pts) {
            // pathTool에 갖고있는 컬러와 사이즈로 paint 설정
            paint.setColor(pt.getColor());
            paint.setStrokeWidth(pt.getSize());
            // 캔버스에 해당 path 출력
            canvas.drawPath(pt, paint);
        }
    }

    /**
     * 터치이벤트를 오버라이드 하여 path값을 설정하고 onDraw발생시키는 코드추가
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // 패스를 해당 좌표로 이동한다
                currentPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                // 해당 좌표를 그려준다
                currentPath.lineTo(x, y);
                break;
        }
        invalidate(); // <- 다른 언어에서는 대부분 그림을 그려주는 함수를 호출하는 메서드는
                      //    기존 그림을 유지하는데 안드로이드는 지운다

        // 리턴이 false일 경우는 touch 이벤트를 연속해서 발생시키지 않는다
        // 즉 드래그를 하면 onTouchEvent가 재 호출되지 않는다
        return true;
    }

}
```


### PathTool.java

```java
package android.daehoshin.com.androidmemoorm;

import android.graphics.Path;

/**
 * Path에 color와 size를 포함하기위해 path를 상속받아 만든 클래스
 *
 * Created by daeho on 2017. 9. 18..
 */

public class PathTool extends Path{
    private int color;
    private float size;

    /**
     * 생성자에서 초기값을 받아 셋팅
     * @param color
     * @param size
     */
    public PathTool(int color, float size){
        this.color = color;
        this.size = size;
    }

    /**
     * 컬러를 받아오는 메소드
      * @return
     */
    public int getColor(){
        return this.color;
    }

    /**
     * 사이즈를 받아오는 메소드
     * @return
     */
    public float getSize() { return this.size; }
}
```


### CustomAdapter.java

```java
package android.daehoshin.com.androidmemoorm;

import android.daehoshin.com.androidmemoorm.model.PicNote;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 9. 22..
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {
    // 1. 데이터 저장소
    public static List<PicNote> data = new ArrayList<>();

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 목록에서 아이템이 최초 용청되면 View Holder를 생성해준다
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new Holder(view);
    }

    /**
     * 생성된 View Holder를 Recycler뷰에 넘겨서 그리게 한다
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        PicNote picNote = data.get(position);
        holder.setTitle(picNote.getTitle());
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        CardView cvTitle;

        public Holder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            cvTitle = (CardView) itemView.findViewById(R.id.cvTitle);
        }

        public void setTitle(String title){
            tvTitle.setText(title);
        }
    }
}
```


### DBHelper.java

```java
package android.daehoshin.com.androidmemoorm;

import android.content.Context;
import android.daehoshin.com.androidmemoorm.model.PicNote;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by daeho on 2017. 9. 22..
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {
    public static final String DB_NAME = "ormlite.db";
    public static final int DB_VER = 1;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            // PicNote.class 를 참조해서 테이블을 생성함
            TableUtils.createTable(connectionSource, PicNote.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
```


### PicNote.java

```java
package android.daehoshin.com.androidmemoorm.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by daeho on 2017. 9. 22..
 */
@DatabaseTable(tableName = "picnote")
public class PicNote {
    @DatabaseField
    private long id;

    @DatabaseField
    private String title;

    @DatabaseField
    private String imagePath;

    @DatabaseField
    private long datetime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }
}
```


### PicNoteDAO.java

```java
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
```
