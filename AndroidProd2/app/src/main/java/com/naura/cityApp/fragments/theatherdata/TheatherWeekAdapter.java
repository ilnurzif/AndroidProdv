package com.naura.cityApp.fragments.theatherdata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.naura.myapplication.R;

import java.util.List;

public class TheatherWeekAdapter extends RecyclerView.Adapter<TheatherWeekAdapter.ViewHolder> {
    private List<WeatherData> theatherDays;
    private LayoutInflater inflater;

    public TheatherWeekAdapter(Context context, List<WeatherData> theatherdays) {
        this.theatherDays = theatherdays;
        this.inflater = LayoutInflater.from(context);
    }

    public void setTheatherDays(List<WeatherData> theatherDays) {
        this.theatherDays = theatherDays;
    }

    @NonNull
    @Override
    public TheatherWeekAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheatherWeekAdapter.ViewHolder holder, int position) {
        WeatherData theatherDay = theatherDays.get(position);
//        holder.weatherView.setImageResource(theatherDay.getTheathericon());
        holder.temperatureView.setText(theatherDay.getFormatedTemperature());
        holder.weekdayView.setText(theatherDay.getDateStr());
        holder.descriptionView.setText(theatherDay.getDescription());
    }

    @Override
    public int getItemCount() {
        return theatherDays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView weatherView;
        final TextView weekdayView, temperatureView, descriptionView;

        ViewHolder(View view) {
            super(view);
            weatherView = view.findViewById(R.id.weathericon);
            weekdayView = view.findViewById(R.id.weekday);
            temperatureView = view.findViewById(R.id.temperature);
            descriptionView = view.findViewById(R.id.description);
        }
    }
}
