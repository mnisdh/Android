package android.daehoshin.com.memo;

import android.content.Intent;
import android.daehoshin.com.memo.domain.Memo;
import android.daehoshin.com.memo.domain.MemoAdapter;
import android.daehoshin.com.memo.domain.MemoDAO;
import android.daehoshin.com.memo.util.PermissionUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;


public class ListActivity extends AppCompatActivity {
    PermissionUtil pUtil;

    MemoDAO dao;
    RecyclerView rvList;
    MemoAdapter adapter;

    public static final int NEW_MEMO_REQUEST = 1;

    PermissionUtil.PermissionGrant permissionGrant = new PermissionUtil.PermissionGrant() {
        @Override
        public void init() {
            init2();
            loadList();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        int REQ_CODE = 99;

        pUtil = new PermissionUtil(REQ_CODE, permissions);
        pUtil.checkPermission(this, permissionGrant);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        pUtil.afterPermissionResult(requestCode, grantResults, permissionGrant);
    }

    private void init2(){
        dao = new MemoDAO(this);
        adapter = new MemoAdapter(this);

        rvList = (RecyclerView) findViewById(R.id.rvList);
        rvList.setAdapter(adapter);

        rvList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

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
    }

    private void loadList(){
        adapter.setData(dao.selectAll());
        //adapter.update();
    }

    @Override
    protected void onDestroy() {
        if(dao != null) dao.close();

        super.onDestroy();
    }
}
