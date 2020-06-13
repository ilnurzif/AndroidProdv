package com.naura.cityApp.fragments.searchhistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naura.cityApp.fragments.theatherdata.WeatherData;
import com.naura.myapplication.R;

import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {
    List<WeatherData> weatherDataList;
    private Context context;

    public HistoryListAdapter(List<WeatherData> weatherDataList) {
        this.weatherDataList = weatherDataList;
    }

    @NonNull
    @Override
    public HistoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        view = LayoutInflater.from(context)
                .inflate(R.layout.history_item, parent, false);
        return new HistoryListAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       WeatherData weatherData = weatherDataList.get(position);
       holder.dateTW.setText(weatherData.getDateStr());
       holder.temperatureTW.setText(weatherData.getFormatedTemperature());
       holder.humidityTW.setText(weatherData.getFormatedHumitity());
       holder.pressureTW.setText(weatherData.getFormatedPressure());
    }

    @Override
    public int getItemCount() {
        return weatherDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
     TextView dateTW, temperatureTW, humidityTW, pressureTW;
     ViewHolder(View view) {
            super(view);
            dateTW=view.findViewById(R.id.dateTW);
            temperatureTW=view.findViewById(R.id.temperatureTW);
            humidityTW=view.findViewById(R.id.humidityTW);
            pressureTW=view.findViewById(R.id.pressureTW);
        }
    }

}