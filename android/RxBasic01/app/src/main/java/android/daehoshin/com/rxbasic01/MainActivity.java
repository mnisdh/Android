package android.daehoshin.com.rxbasic01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

public class MainActivity extends AppCompatActivity {
    CustomSubject subject;

    RecyclerView rvList;
    CustomAdapter adapter;

    // 데이터 저장 변수
    List<String> months = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvList = findViewById(R.id.rvList);
        adapter = new CustomAdapter();
        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(this));


        // 데이터 - 인터넷에서 순차적으로 가져오는것이라 가정
        String[] data = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };

        // 1. 발행자 operator from
        Observable<String> observableFrom = Observable.fromArray(data);

        // 2. 구독자
//        observableFrom.subscribe(
//            // onNext 데이터가 있으면 호출된다
//            new Consumer<String>() {
//                @Override
//                public void accept(String s) throws Exception {
//                    months.add(s);
//                }
//            }
//            //onError 가 호출된다
//            , new Consumer<Throwable>() {
//                @Override
//                public void accept(Throwable throwable) throws Exception {
//
//                }
//            }
//            // onComplete 이 호출된다
//            , new Action() {
//                @Override
//                public void run() throws Exception {
//                    adapter.setDataAndRefresh(months);
//                }
//            }
//        );
        observableFrom.subscribe(month -> months.add(month), throwable -> {}, () -> adapter.setDataAndRefresh(months));

        // 2. Just
        Observable<String> observableJust = Observable.just("JAN", "FEB", "MAR");
        observableJust.subscribe(month -> months.add(month));

        // 3. defer
        Observable<String> observableDefer = Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.just("JAN", "FEB", "MAR");
            }
        });
        observableDefer.subscribe(month -> months.add(month));

//        // 발행자 생성 1. Observable, Subject
//        subject = new CustomSubject();
//        subject.start();
    }

    // 구독자에 id를 부여하기 위한 코드
    int clientIdx = 0;
    /**
     * 위에서 생성한 발행자에 클라이언트를 클릭할 때 마다 등록한다
     * @param v
     */
    public void addObserver(View v){
        Client client = new Client(clientIdx++);
        subject.add(client);
    }

    /**
     * 구독자
     */
    class Client implements CustomSubject.Observer{
        String name;

        public Client(int idx){
            this.name = "Client" + idx;
        }

        @Override
        public void notification(String msg) {
            Log.d(name, msg);
        }
    }
}

/**
 * 발행자 클래스
 */
class CustomSubject extends Thread{
    // 옵저버 목록
    List<Observer> observers = new ArrayList<>();

    public void add(Observer observer){
        observers.add(observer);
    }

    // 실행코드
    public void run(){
        int count = 0;
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (Observer observer : observers) observer.notification("Hello" + count++);
        }
    }

    // 옵저버 인터페이스
    public interface Observer{
        void notification(String msg);
    }
}

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder>{
    List<String> data = new ArrayList<>();

    public void setDataAndRefresh(List<String> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.text1.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView text1;
        public Holder(View itemView) {
            super(itemView);

            text1 = itemView.findViewById(android.R.id.text1);
        }
    }
}