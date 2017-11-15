package android.daehoshin.com.rxbasic03;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvList;
    CustomAdapter adapter;

    List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        rvList = findViewById(R.id.rvList);
        adapter = new CustomAdapter();
        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(this));
    }

    // 요청시점 이후 데이터만 가져옴
    // 구독 시점부터 읽을수 있다 - 이전에 발행된 아이템은 읽을수 없다
    PublishSubject<String> publish = PublishSubject.create();
    public void doPublish(View v){
        new Thread(
                () -> {
                for(int i = 0; i < 10; i++){
                    publish.onNext("Something" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }).start();
    }
    public void getPublish(View v){
        adapter.clear();
        publish.observeOn(AndroidSchedulers.mainThread()).subscribe(s -> adapter.addDataAndRefresh(s));
    }

    // 요청시점 이전한개 + 이후 데이터만 가져옴
    // publish 보다 하나 앞에꺼 더 가져옴
    BehaviorSubject<String> behavior = BehaviorSubject.create();
    public void doBehavior(View v){
        new Thread(
                () -> {
                    for(int i = 0; i < 10; i++){
                        behavior.onNext("Something" + i);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
    }
    public void getBehavior(View v){
        adapter.clear();
        behavior.observeOn(AndroidSchedulers.mainThread()).subscribe(s -> adapter.addDataAndRefresh(s));
    }

    // 모든데이터를 쌓아놓고 요청이 올때 처음부터 다 보내줌
    ReplaySubject<String> replay = ReplaySubject.create();
    public void doReplay(View v){
        new Thread(
                () -> {
                    for(int i = 0; i < 10; i++){
                        replay.onNext("Something" + i);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
    }
    public void getReplay(View v){
        adapter.clear();
        replay.observeOn(AndroidSchedulers.mainThread()).subscribe(s -> adapter.addDataAndRefresh(s));
    }

    AsyncSubject<String> async = AsyncSubject.create();
    public void doAsync(View v){
        new Thread(
                () -> {
                    for(int i = 0; i < 10; i++){
                        async.onNext("Something" + i);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    async.onComplete();
                }).start();
    }
    public void getAsync(View v){
        adapter.clear();
        async.observeOn(AndroidSchedulers.mainThread()).subscribe(s -> adapter.addDataAndRefresh(s));
    }
}
