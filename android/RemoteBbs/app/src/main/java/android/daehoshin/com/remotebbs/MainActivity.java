package android.daehoshin.com.remotebbs;

import android.content.Intent;
import android.daehoshin.com.remotebbs.domain.Result;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvList;
    Intent postIntent;
    CustomAdapter adapter;

    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        load();
    }

    private void init(){
        postIntent = new Intent(this, PostActivity.class);

        rvList = findViewById(R.id.rvList);
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm = (LinearLayoutManager)recyclerView.getLayoutManager();

                if(lm.findFirstCompletelyVisibleItemPosition() == 0) {
                    Toast.makeText(recyclerView.getContext(), "Reload", Toast.LENGTH_LONG).show();
                    page = 0;
                    load();
                }

                if(lm.getItemCount() == lm.findLastVisibleItemPosition() + 1) load();
            }
        });
    }

    private void initList(Result result){
        adapter = new CustomAdapter(result.getBbs());
        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void addList(Result result){
        adapter.addDataAndRefresh(result.getBbs());
    }

    private void load() {
        new AsyncTask<Void, Void, Result>() {

            @Override
            protected Result doInBackground(Void... voids) {
                String result = Remote.getData("http://192.168.0.2:8090/bbs?type=all&page=" + page);
                Gson gson = new Gson();
                Result data = gson.fromJson(result, Result.class);
                return data;
            }

            @Override
            protected void onPostExecute(Result result) {
                if(result.isOK()){
                    if(page == 1) initList(result);
                    else if(page > 1) addList(result);

                    page++;
                }

            }
        }.execute();
    }

    public static final int POST_CODE = 1;
    public void post(View v){
        startActivityForResult(postIntent, POST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case POST_CODE: if(resultCode == RESULT_OK) adapter.notifyDataSetChanged(); break;
        }
    }
}
