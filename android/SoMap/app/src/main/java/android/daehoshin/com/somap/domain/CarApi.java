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
import retrofit2.http.Path;

/**
 * Created by daeho on 2017. 11. 21..
 */

public class CarApi {
    public static void getCars(String zone_id, IGetCar callback){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BuildConfig.SERVER_URL);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = builder.build();

        ICar service = retrofit.create(ICar.class);

        Observable<Car> observable = service.getData(zone_id);

        PublishSubject<CarData> subject = PublishSubject.create();
        subject.observeOn(AndroidSchedulers.mainThread())
                .subscribe(carData -> callback.callback(carData));

        observable.observeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .subscribe(car -> {
                    if(car.isSuccess()){
                        for(CarData data : car.getCarData()) {
                            subject.onNext(data);
                        }
                    }
                });
    }

    public interface IGetCar{
        void callback(CarData data);
    }

    interface ICar{
        @GET("car/{param1}")
        Observable<Car> getData(@Path("param1") String zone_id);
    }
}
