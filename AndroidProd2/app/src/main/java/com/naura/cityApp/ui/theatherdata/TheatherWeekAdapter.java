package com.naura.cityApp.ui.theatherdata;

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
    private List<TheatherData> theatherDays;
    private LayoutInflater inflater;

    public TheatherWeekAdapter(Context context, List<TheatherData> theatherdays) {
        this.theatherDays = theatherdays;
        this.inflater = LayoutInflater.from(context);
    }

    public void setTheatherDays(List<TheatherData> theatherDays) {
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
        TheatherData theatherDay = theatherDays.get(position);
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
