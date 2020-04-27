package com.naura.cityApp.ui.searchhistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naura.cityApp.ui.theatherdata.TheatherData;
import com.naura.myapplication.R;

import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {
    List<TheatherData> theatherDataList;
    private Context context;

    public HistoryListAdapter(List<TheatherData> theatherDataList) {
        this.theatherDataList = theatherDataList;
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
       TheatherData theatherData=theatherDataList.get(position);
       holder.dateTW.setText(theatherData.getDateStr());
       holder.temperatureTW.setText(theatherData.getFormatedTemperature());
       holder.humidityTW.setText(theatherData.getFormatedHumitity());
       holder.pressureTW.setText(theatherData.getFormatedPressure());
    }

    @Override
    public int getItemCount() {
        return theatherDataList.size();
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