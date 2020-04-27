package com.naura.cityApp.ui.citylist.model;

import com.naura.cityApp.ui.citylist.model.rest.ILoadData;
import com.naura.cityApp.ui.citylist.model.rest.OpenWeather;
import com.naura.cityApp.ui.citylist.model.rest.Weather;
import com.naura.cityApp.ui.citylist.model.rest.WeatherAddParams;
import com.naura.cityApp.ui.citylist.model.rest.WeatherRequest;
import com.naura.cityApp.ui.theatherdata.TheatherData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;

public class RestLoadData implements ILoadData {
    private OpenWeather openWeather;
    private static String baseUrl = "https://api.openweathermap.org";
    private int cnt = 16;
    private String lang = "ru";
    private String keyApi = "1d208cf3fc4085d8d8ba431d9d470fb3";
    private String units = "metric";
    private ICallData callData;

    public RestLoadData(ICallData callData) {
        this.callData = callData;
        initRetrofit();
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        openWeather = retrofit.create(OpenWeather.class);
    }

    public void request(final String cityName) {
        openWeather.loadWeather(cityName, cnt, lang, keyApi, units)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        List<TheatherData> cityTheatherList = new ArrayList<>();
                        if (response.body() != null && response.isSuccessful()) {
                            WeatherAddParams[] weatherAddParams = response.body().getWeatherAddParams();
                            cityTheatherList.add(respToTheatherData(weatherAddParams[0]));
                            cityTheatherList.add(respToTheatherData(weatherAddParams[6]));
                            cityTheatherList.add(respToTheatherData(weatherAddParams[13]));
                            callData.execute(cityTheatherList, cityName,true);
                            Log.d("Debug", "FINISH");
                        }
                        else
                        {
                            // Ошибка Toast.makeText(, "", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        callData.errorTextReturn(t.getMessage());
                    }
                });
    }

    private TheatherData respToTheatherData(WeatherAddParams weatherAddParams) {
        try {
            float temper = weatherAddParams.getMainWeatherParams().getTemperature();
            int humidity = weatherAddParams.getMainWeatherParams().getHumidity();
            int pressure = weatherAddParams.getMainWeatherParams().getPressure();
            String dt_txt = weatherAddParams.getDt_txt();
            Weather[] weather = weatherAddParams.getWeather();
            String description = weather[0].getDescription();

            Date tempDate = getFormatedDate(dt_txt);
            return new TheatherData(temper, pressure, humidity, tempDate, description);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Date getFormatedDate(String dt_txt) throws ParseException {
        String dateS = dt_txt;
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(dateS);
    }

}
