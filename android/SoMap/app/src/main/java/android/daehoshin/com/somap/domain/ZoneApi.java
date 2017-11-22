package android.daehoshin.com.somap.domain;

import android.daehoshin.com.somap.BuildConfig;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by daeho on 2017. 11. 21..
 */

public class ZoneApi {
    public static void getZones(IGetZone callback){
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
                    Thread.sleep(100);
                }
            }
        });
    }

    public static void getZones(LatLngBounds latLngBounds, IGetZone callback){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BuildConfig.SERVER_URL);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = builder.build();

        IZone service = retrofit.create(IZone.class);


        double minLat = (latLngBounds.northeast.latitude < latLngBounds.southwest.latitude)? latLngBounds.northeast.latitude : latLngBounds.southwest.latitude;
        double maxLat = (latLngBounds.northeast.latitude > latLngBounds.southwest.latitude)? latLngBounds.northeast.latitude : latLngBounds.southwest.latitude;
        double minLng = (latLngBounds.northeast.longitude < latLngBounds.southwest.longitude)? latLngBounds.northeast.longitude : latLngBounds.southwest.longitude;
        double maxLng = (latLngBounds.northeast.longitude > latLngBounds.southwest.longitude)? latLngBounds.northeast.longitude : latLngBounds.southwest.longitude;

        Log.d("getZones", minLat+"/"+minLng+"/"+ maxLat+"/"+maxLng);
        Observable<Zone> observable = service.getData(minLat+"", minLng+"", maxLat+"", maxLng+"");

        PublishSubject<Data> subject = PublishSubject.create();
        subject.observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> callback.callback(data));

        observable.observeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .subscribe(zone -> {
                    if(zone.isSuccess()){
                        for(Data data : zone.getData()) {
                            if(latLngBounds.contains(new LatLng(Double.parseDouble(data.getLat()), Double.parseDouble(data.getLng())))){
                                subject.onNext(data);
//                                Thread.sleep(100);
                            }
                        }
                    }
                });
    }

    public interface IGetZone{
        void callback(Data data);
    }

    interface IZone{
        @GET("zone")
        Observable<Zone> getData();

        @GET("zone")
        Observable<Zone> getData(
                  @Query("minLat") String minLat
                , @Query("minLng") String minLng
                , @Query("maxLat") String maxLat
                , @Query("maxLng") String maxLng);
    }
}
