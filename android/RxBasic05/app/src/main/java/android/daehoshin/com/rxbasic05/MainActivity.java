package android.daehoshin.com.rxbasic05;

import android.daehoshin.com.rxbasic05.domain.Row;
import android.daehoshin.com.rxbasic05.domain.WeatherAPI;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity {
    private IWeather service;

    private TextView tvResult;
    private EditText etFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initService();
    }

    private void init(){
        etFind = findViewById(R.id.etFind);
        tvResult = findViewById(R.id.tvResult);
    }

    private void initService(){
        // 1. 생성
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(IWeather.SERVER_URL);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = builder.build();

        // 2. 서비스 만들기 < 인터페이스로부터
        service = retrofit.create(IWeather.class);
    }

    public void find(View v){
        tvResult.setText("");

        // 3. 옵저버블 생성
        Observable<WeatherAPI> observable = service.getData(IWeather.SERVER_KEY, 0, 50, etFind.getText().toString());

        // 4. 발행시작
        observable
            .subscribeOn(Schedulers.io())
            // 5. 구독
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(wAPI -> {
                String result = "";
                for(Row row : wAPI.getRealtimeWeatherStation().getRow()){
                    result += "지역명 : " + row.getSTN_NM() + "\n";
                    result += "온도 : " + row.getSAWS_TA_AVG() + "\n";
                    result += "습도 : " + row.getSAWS_HD() + "\n" + "\n";
                }
                tvResult.setText(result);
            });
    }
}

interface IWeather{
    public static final String SERVER_URL = "http://openapi.seoul.go.kr:8088/";
    public static final String SERVER_KEY = "6f65416d616d6e6932346a4e516453";

    @GET("{key}/json/RealtimeWeatherStation/{skip}/{count}/{guName}")
    Observable<WeatherAPI> getData(
            @Path("key") String key,
            @Path("skip") int skip,
            @Path("count") int count,
            @Path("guName") String guName);
}