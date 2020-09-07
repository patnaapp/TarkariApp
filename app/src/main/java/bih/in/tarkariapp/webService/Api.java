package bih.in.tarkariapp.webService;

import com.google.gson.JsonObject;

import bih.in.tarkariapp.entity.AppDetailsResponse;
import bih.in.tarkariapp.entity.AppVersion;
import bih.in.tarkariapp.entity.ApprovedOrderResponse;
import bih.in.tarkariapp.entity.ChangePassResponse;
import bih.in.tarkariapp.entity.GetOTPEntity;
import bih.in.tarkariapp.entity.GetPlacedOrderResponse;
import bih.in.tarkariapp.entity.GetVegResponse;
import bih.in.tarkariapp.entity.GetVegStockResponse;
import bih.in.tarkariapp.entity.LoginDetailsResponse;
import bih.in.tarkariapp.entity.NotificationResponse;
import bih.in.tarkariapp.entity.PlaceOrderResponse;
import bih.in.tarkariapp.entity.PlaceStockResponse;
import bih.in.tarkariapp.entity.UserDetail;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api
{

    @Headers("VegToken: yKpT9flpzeYI1qb4fAv4ieVrT/Bn6wd1")
    @GET("PVCSADM/GetAppDetails")
    Call<AppDetailsResponse> getAppVersion();

    @Headers("VegToken: yKpT9flpzeYI1qb4fAv4ieVrT/Bn6wd1")
    @POST("Login/UserFarmerLogin")
    Call<LoginDetailsResponse> AuthenticateFarmeLogin(@Body JsonObject param);

    @Headers("VegToken: yKpT9flpzeYI1qb4fAv4ieVrT/Bn6wd1")
    @POST("Tela/Login")
    Call<LoginDetailsResponse> AuthenticateThelaLogin(@Body JsonObject param);

    @Headers("VegToken: yKpT9flpzeYI1qb4fAv4ieVrT/Bn6wd1")
    @POST("Tela/GenerateOPTNO")
    Call<GetOTPEntity> GetOtp(@Body JsonObject param);

    @Headers("VegToken: yKpT9flpzeYI1qb4fAv4ieVrT/Bn6wd1")
    @POST("TelaUpdatePassword")
    Call<ChangePassResponse> ChangePassword(@Body JsonObject param);

    @Headers("VegToken: yKpT9flpzeYI1qb4fAv4ieVrT/Bn6wd1")
    @POST("Tela/UpdateTelaGenerateOrder")
    Call<PlaceOrderResponse> PlaceOrderApi(@Body JsonObject param);

    @Headers("VegToken: yKpT9flpzeYI1qb4fAv4ieVrT/Bn6wd1")
    @POST("Tela/GetVegOrderPrice")
    Call<GetVegResponse> GetVegListByDate(@Body JsonObject param);

    @Headers("VegToken: yKpT9flpzeYI1qb4fAv4ieVrT/Bn6wd1")
    @POST("Tela/GetTelaGenerateOrderByDate")
    Call<GetPlacedOrderResponse> GetOrderPlaced(@Body JsonObject param);

   @Headers("VegToken: yKpT9flpzeYI1qb4fAv4ieVrT/Bn6wd1")
    @GET("Report/GetVeg")
    Call<GetVegStockResponse> GetVegListByDateFarmer();

    @Headers("VegToken: yKpT9flpzeYI1qb4fAv4ieVrT/Bn6wd1")
    @POST("Farmer/GetFarmerOrderDate")
    Call<NotificationResponse> GetNotification(@Body JsonObject param);

    @Headers("VegToken: yKpT9flpzeYI1qb4fAv4ieVrT/Bn6wd1")
    @POST("Farmer/GetfarmerOrderDetail")
    Call<ApprovedOrderResponse> GetOrderDetailsNotification(@Body JsonObject param);


    @Headers("VegToken: yKpT9flpzeYI1qb4fAv4ieVrT/Bn6wd1")
    @POST("Farmer/UpdateFarmerStock")
    Call<PlaceStockResponse> PlaceStockApi(@Body JsonObject param);
}
