package bih.in.tarkariapp.webService;

import com.google.gson.JsonObject;

import bih.in.tarkariapp.entity.AppDetailsResponse;
import bih.in.tarkariapp.entity.AppVersion;
import bih.in.tarkariapp.entity.ChangePassResponse;
import bih.in.tarkariapp.entity.GetOTPEntity;
import bih.in.tarkariapp.entity.GetPlacedOrderResponse;
import bih.in.tarkariapp.entity.GetVegResponse;
import bih.in.tarkariapp.entity.GetVegStockResponse;
import bih.in.tarkariapp.entity.LoginDetailsResponse;
import bih.in.tarkariapp.entity.PlaceOrderResponse;
import bih.in.tarkariapp.entity.UserDetail;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api
{

    @Headers("Token: 9900a9720d31dfd5fdb4352700c")
    @GET("PVCSADM/GetAppDetails")
    Call<AppDetailsResponse> getAppVersion();

    @Headers("Token: 9900a9720d31dfd5fdb4352700c")
    @POST("Login/UserFarmerLogin")
    Call<LoginDetailsResponse> AuthenticateFarmeLogin(@Body JsonObject param);

    @Headers("Token: 9900a9720d31dfd5fdb4352700c")
    @POST("Tela/Login")
    Call<LoginDetailsResponse> AuthenticateThelaLogin(@Body JsonObject param);

    @Headers("Token: 9900a9720d31dfd5fdb4352700c")
    @POST("Tela/GenerateOPTNO")
    Call<GetOTPEntity> GetOtp(@Body JsonObject param);

    @Headers("Token: 9900a9720d31dfd5fdb4352700c")
    @POST("TelaUpdatePassword")
    Call<ChangePassResponse> ChangePassword(@Body JsonObject param);

    @Headers("Token: 9900a9720d31dfd5fdb4352700c")
    @POST("Tela/UpdateTelaGenerateOrder")
    Call<PlaceOrderResponse> PlaceOrderApi(@Body JsonObject param);

    @Headers("Token: 9900a9720d31dfd5fdb4352700c")
    @POST("Tela/GetVegOrderPrice")
    Call<GetVegResponse> GetVegListByDate(@Body JsonObject param);

    @Headers("Token: 9900a9720d31dfd5fdb4352700c")
    @POST("Tela/GetTelaGenerateOrderByDate")
    Call<GetPlacedOrderResponse> GetOrderPlaced(@Body JsonObject param);

    @Headers("Token: 9900a9720d31dfd5fdb4352700c")
    @GET("Report/GetVeg")
    Call<GetVegStockResponse> GetVegListByDateFarmer();
}
