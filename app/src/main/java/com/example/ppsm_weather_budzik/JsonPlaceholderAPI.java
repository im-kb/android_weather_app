package com.example.ppsm_weather_budzik;

import com.example.ppsm_weather_budzik.classes.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceholderAPI {
    @GET("data/2.5/weather")
    Call<WeatherData> getMainWeatherClassCall(@Query("q") String q,
                                              @Query("APPID") String APPID,
                                              @Query("units") String units);
}
