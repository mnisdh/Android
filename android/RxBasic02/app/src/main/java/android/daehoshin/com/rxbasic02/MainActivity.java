package android.daehoshin.com.rxbasic02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.text.DateFormatSymbols;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvList;
    CustomAdapter adapter;
    String[] arrMonths;
    Observable<String> observable;
    Observable<String> observableZip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvList = findViewById(R.id.rvList);
        adapter = new CustomAdapter();
        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(this));

        // 1월부터 12월 가져오기
        DateFormatSymbols dfs = new DateFormatSymbols();
        arrMonths = dfs.getMonths();

        // 1. 발행자
        observable = Observable.create(e -> {
            try{
                for(String month : arrMonths) {
                    e.onNext(month);
                    Thread.sleep(1000);
                }
                e.onComplete();
            }
            catch (Exception ex){
                throw ex;
            }
        });

        // 1. 발행자
        observable = Observable.create(e -> {
            try{
                for(String month : arrMonths) {
                    e.onNext(month);
                    Thread.sleep(1000);
                }
                e.onComplete();
            }
            catch (Exception ex){
                throw ex;
            }
        });

        observableZip = Observable.zip(Observable.just("가","나","다"), Observable.just("a", "b", "c"), (item1, item2) -> "item1 :" + item1 + " item2:" + item2);
    }

    public void doMap(View v){
        adapter.clear();

        // 2. 구독자
        observable.subscribeOn(Schedulers.io())                 // 옵저버블의 thread를 지정
                .observeOn(AndroidSchedulers.mainThread())      // 옵저버의 thread를 지정
                .filter(s -> !s.equals("January"))
                .map(s -> "[[" + s + "]]")
                .subscribe(s -> adapter.addDataAndRefresh(s));
    }

    public void doFlatmap(View v){
        adapter.clear();

        // 2. 구독자
        observable.subscribeOn(Schedulers.io())                 // 옵저버블의 thread를 지정
                .observeOn(AndroidSchedulers.mainThread())      // 옵저버의 thread를 지정
                .filter(s -> !s.equals("January"))
                .flatMap(item -> Observable.fromArray(new String[]{"name:"+item, "["+item+"]"}))
                .subscribe(s -> adapter.addDataAndRefresh(s));
    }

    public void doZip(View v){
        adapter.clear();

        // 2. 구독자
        observableZip.subscribeOn(Schedulers.io())                 // 옵저버블의 thread를 지정
                .observeOn(AndroidSchedulers.mainThread())      // 옵저버의 thread를 지정
                .subscribe(s -> adapter.addDataAndRefresh(s));
    }
}


class Test{

}