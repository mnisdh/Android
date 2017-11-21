package android.daehoshin.com.somap.domain;

import android.daehoshin.com.somap.BuildConfig;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by daeho on 2017. 11. 21..
 */

public class ZoneApi {
    public static Zone getZones(IGetZone callback){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BuildConfig.SERVER_URL);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = builder.build();

        IZone service = retrofit.create(IZone.class);

        Observable<Zone> observable = service.getData();

        PublishSubject<Data> subject = PublishSubject.create();
        subject.observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> callback.callback(data));

        observable.observeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .subscribe(zone -> {
            if(zone.isSuccess()){
                for(Data data : zone.getData()) {
                    subject.onNext(data);
                    Thread.sleep(500);
                }
            }
        });

        return new Zone();
    }

    public interface IGetZone{
        void callback(Data data);
    }

    interface IZone{
        @GET("zone")
        Observable<Zone> getData();
    }
}
