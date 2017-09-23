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
