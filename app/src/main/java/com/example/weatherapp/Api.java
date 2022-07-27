package com.example.weatherapp;

import com.example.weatherapp.Model.MyWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {

    @Headers({
            "X-RapidAPI-Key: 506ed5160dmsh69b126295449edcp131494jsnc921c41673a9",
            "X-RapidAPI-Host: community-open-weather-map.p.rapidapi.com"
    })
    @GET("weather")
    Call<MyWeather> getMyCityWeather(@Query("q") String cityname);
}
