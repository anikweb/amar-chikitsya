package com.example.amarchikitsya.ApiInterface;

import static com.example.amarchikitsya.Const.weather_api_key;
import com.example.amarchikitsya.model.Current.CurrentWeather;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CurrentWeatherAPI {

    @GET("weather?q=Mymensingh&units=metric&appid="+weather_api_key)
    Call<CurrentWeather> getCurrentWeather();
}
