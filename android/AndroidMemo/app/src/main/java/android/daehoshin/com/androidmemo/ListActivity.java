package android.daehoshin.com.androidmemo;

import android.content.Intent;
import android.daehoshin.com.androidmemo.domain.Memo;
import android.daehoshin.com.androidmemo.domain.MemoAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    List<Memo> memos = new ArrayList<>();
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
        // 2.데이터와 리스트뷰를 연결하는 아답터를 생성
        adapter = new MemoAdapter(this, memos);

        // 3.아답터와 리스트뷰를 연결
        lvMemo = (ListView) findViewById(R.id.lvMemo);
        lvMemo.setAdapter(adapter);

        // 버튼 이벤트 연결
        findViewById(R.id.btnPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("no", memos.size() + 1);
                startActivityForResult(intent, NEW_MEMO_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case NEW_MEMO_REQUEST:
                if(resultCode == RESULT_OK) {
                    memos.add(0, (Memo) data.getSerializableExtra("memo"));
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
