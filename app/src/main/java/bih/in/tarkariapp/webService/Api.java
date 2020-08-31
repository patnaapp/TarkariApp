package bih.in.tarkariapp.webService;

import com.google.gson.JsonObject;

import bih.in.tarkariapp.entity.AppDetailsResponse;
import bih.in.tarkariapp.entity.AppVersion;
import bih.in.tarkariapp.entity.ChangePassResponse;
import bih.in.tarkariapp.entity.GetOTPEntity;
import bih.in.tarkariapp.entity.GetPlacedOrderResponse;
import bih.in.tarkariapp.entity.GetVegResponse;
import bih.in.tarkariapp.entity.LoginDetailsResponse;
import bih.in.tarkariapp.entity.PlaceOrderResponse;
import bih.in.tarkariapp.entity.UserDetail;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api
{

    @GET("PVCSADM/GetAppDetails")
    Call<AppDetailsResponse> getAppVersion();

    @POST("Login/UserFarmerLogin")
    Call<LoginDetailsResponse> AuthenticateFarmeLogin(@Body JsonObject param);

    @POST("Tela/Login")
    Call<LoginDetailsResponse> AuthenticateThelaLogin(@Body JsonObject param);

    @POST("Tela/GenerateOPTNO")
    Call<GetOTPEntity> GetOtp(@Body JsonObject param);

    @POST("TelaUpdatePassword")
    Call<ChangePassResponse> ChangePassword(@Body JsonObject param);

    @POST("Tela/UpdateTelaGenerateOrder")
    Call<PlaceOrderResponse> PlaceOrderApi(@Body JsonObject param);

    @POST("Tela/GetVegOrderPrice")
    Call<GetVegResponse> GetVegListByDate(@Body JsonObject param);

    @POST("Tela/GetTelaGenerateOrderByDate")
    Call<GetPlacedOrderResponse> GetOrderPlaced(@Body JsonObject param);
}
