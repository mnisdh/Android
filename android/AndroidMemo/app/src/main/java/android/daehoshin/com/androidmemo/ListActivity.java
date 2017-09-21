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
