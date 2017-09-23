package android.daehoshin.com.memo;

import android.content.Intent;
import android.daehoshin.com.memo.domain.Memo;
import android.daehoshin.com.memo.domain.MemoAdapter;
import android.daehoshin.com.memo.domain.MemoDAO;
import android.os.Bundle;
import android.support.v7.app.AlertController;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;


public class ListActivity extends AppCompatActivity {
    MemoDAO dao;
    RecyclerView rvList;
    MemoAdapter adapter;

    final int NEW_MEMO_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        init();
        loadList();
    }

    private void init(){
        dao = new MemoDAO(this);
        adapter = new MemoAdapter();

        rvList = (RecyclerView) findViewById(R.id.rvList);
        rvList.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case NEW_MEMO_REQUEST:
                if(resultCode == RESULT_OK) loadList();
                break;
        }
    }

    public void newPost(View v){
        Intent intent = new Intent(v.getContext(), DetailActivity.class);
        //intent.putExtra("no", adapter.getCount() + 1);
        startActivityForResult(intent, NEW_MEMO_REQUEST);
    }

    public void deleteListAll(View v){
        dao.delete(adapter.getData());
        adapter.setData(new ArrayList<Memo>());
        adapter.update();
    }

    private void loadList(){
        adapter.setData(dao.selectAll());
        adapter.update();
    }

    @Override
    protected void onDestroy() {
        if(dao != null) dao.close();

        super.onDestroy();
    }
}
