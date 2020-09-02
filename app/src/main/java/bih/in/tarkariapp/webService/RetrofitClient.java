package bih.in.tarkariapp.webService;

import java.io.IOException;

import bih.in.tarkariapp.utility.AppConstant;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient
{
    private static Retrofit retrofit;
    private static final String BASE_URL = AppConstant.BASE_URL;
    public static Retrofit getRetrofitInstance()

    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
