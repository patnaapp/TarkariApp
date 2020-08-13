package bih.in.tarkariapp.webService;

import com.google.gson.JsonObject;
import bih.in.tarkariapp.entity.AppVersion;
import bih.in.tarkariapp.entity.UserDetail;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @GET("getAppDetail")
    Call<AppVersion> getAppVersion();

    @POST("verifylogin")
    Call<UserDetail> AuthenticateTubewellUser(@Body JsonObject param);
}
