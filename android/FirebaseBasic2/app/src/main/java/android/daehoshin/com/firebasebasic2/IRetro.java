package android.daehoshin.com.firebasebasic2;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by daeho on 2017. 11. 1..
 */

public interface IRetro {
    @POST("sendNotification")
    Call<ResponseBody> sendNotification(@Body RequestBody postdata);
}
