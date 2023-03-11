package com.example.amarchikitsya.MyRetrofit;

import static com.example.amarchikitsya.Const.WebsiteUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {
    public  static Retrofit websiteRetrofit = null;

//    public  static Retrofit getWebsiteRetrofit(){
//        if(websiteRetrofit == null){
//            websiteRetrofit = new Retrofit.Builder()
//                    .baseUrl(WebsiteUrl)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//        return websiteRetrofit;
//    }

}
