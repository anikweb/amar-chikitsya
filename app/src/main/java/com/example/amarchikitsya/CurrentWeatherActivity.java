package com.example.amarchikitsya;

import static java.lang.Math.round;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.amarchikitsya.ApiInterface.CurrentWeatherAPI;
import com.example.amarchikitsya.MyRetrofit.WeatherRetrofit;
import com.example.amarchikitsya.databinding.ActivityCurrentWeatherBinding;
import com.example.amarchikitsya.model.Current.CurrentWeather;
import com.example.amarchikitsya.utils.InternetConnection;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentWeatherActivity extends AppCompatActivity {

    ActivityCurrentWeatherBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityCurrentWeatherBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (!InternetConnection.checkConnection(CurrentWeatherActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CurrentWeatherActivity.this);
            builder.setTitle("Network Error!")
                    .setMessage("No Internet Connection, Please Check your Network Connection")
                    .setIcon(R.drawable.ic_baseline_info_24)
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                            System.exit(0);

                        }
                    }).setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(CurrentWeatherActivity.this, CurrentWeatherActivity.class));
                            finish();
                        }
                    }).create().show();
        }



        CurrentWeatherAPI currentWeatherAPI = WeatherRetrofit.getRetrofit().create(CurrentWeatherAPI.class);
        Call<CurrentWeather> currentWeatherCall = currentWeatherAPI.getCurrentWeather();
        currentWeatherCall.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                long date = System.currentTimeMillis();
                DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
                binding.timeAndDate.setText(dateFormat.format(date));
                binding.temp.setText(""+round(response.body().getMain().getTemp())+" \u2103");
                binding.location.setText(response.body().getName()+", "+response.body().getSys().getCountry());
                binding.weather.setText(response.body().getWeather().get(0).getMain());
                binding.mintemp.setText(round(response.body().getMain().getTempMin())+" \u2103");
                binding.maxtemp.setText(round(response.body().getMain().getTempMax())+" \u2103");
                binding.pressuretemp.setText(round(response.body().getMain().getPressure())+" mbar");
                binding.humiditytemp.setText(round(response.body().getMain().getHumidity())+"%");
                binding.feelsLike.setText(round(response.body().getMain().getFeelsLike())+"\u2103");

//                Date date1 = new Date(round(response.body().getSys().getSunrise()));
//                DateFormat dateFormat1 = new SimpleDateFormat("hh:mm a");
//
//                Date date2 = new Date(round(response.body().getSys().getSunset()));
//                DateFormat dateFormat2 = new SimpleDateFormat("hh:mm a");
//
//                binding.sunriseTxt.setText(dateFormat1.format(date1));
//                binding.sunsetTxt.setText(dateFormat2.format(date2));

                binding.sunriseTxt.setVisibility(View.GONE);
                binding.sunsetTxt.setVisibility(View.GONE);
                Glide.with(CurrentWeatherActivity.this).load("http://openweathermap.org/img/wn/"+response.body().getWeather().get(0).getIcon()+"@2x.png").into(binding.icon);

            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                Toast.makeText(CurrentWeatherActivity.this, ""+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.toolbar.backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });
        binding.toolbar.pageTitle.setText("Current Weather");




    }
}