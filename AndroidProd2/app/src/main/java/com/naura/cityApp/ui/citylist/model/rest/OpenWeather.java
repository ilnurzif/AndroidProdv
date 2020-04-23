package com.naura.cityApp.ui.citylist.model.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeather {
    @GET("data/2.5/forecast")
    Call<WeatherRequest> loadWeather(@Query("q") String city,
                                     @Query("cnt") int cnt,
                                     @Query("lang") String lang,
                                     @Query("appid") String keyApi,
                                     @Query("units") String units);
}
