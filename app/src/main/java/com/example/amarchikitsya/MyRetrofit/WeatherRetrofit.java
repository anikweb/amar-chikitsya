package com.example.amarchikitsya.MyRetrofit;

import static com.example.amarchikitsya.Const.base_weather_url;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRetrofit {
    public  static Retrofit retrofit = null;
    public  static  Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_weather_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
