package com.naura.cityApp.fragments.citydetail;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naura.cityApp.basemodel.WeatherData;
import com.naura.myapplication.R;

import java.util.List;

public class CityDetailFragment extends Fragment implements CityDetailFragmentView {
    private RecyclerView recyclerView;
    private TextView temperatureTextView;
    private WeatherWeekAdapter adapter;

    private CityDetailPresenter cityDetailPresenter;
    private TextView humidityTodayTW;
    private TextView pressureTodayTW;
    private TextView descriptionTodayTW;
    private TextView currentDateTextView;
    private ImageView weatherIconIW;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.city_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cityDetailPresenter = CityDetailPresenter.getInstance();
        initViews(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();
        cityDetailPresenter.bind(this);
    }

    @Override
    public void onPause() {
        cityDetailPresenter.unBind();
        super.onPause();
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.weekdays);
        temperatureTextView = view.findViewById(R.id.temperaturetextView);
        humidityTodayTW = view.findViewById(R.id.humidityTodayTW);
        pressureTodayTW = view.findViewById(R.id.pressureTodayTW);
        descriptionTodayTW = view.findViewById(R.id.descriptionTodayTW);
        currentDateTextView = view.findViewById(R.id.currentDateTextView);
        weatherIconIW = view.findViewById(R.id.weatherIconIW);
        Bundle bundle = this.getArguments();
        cityDetailPresenter.startCityLoad(bundle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setCurrentTemperature(String temperatureNow) {
        temperatureTextView.setText(temperatureNow);
    }

    @Override
    public void setCurrentHumiduty(String airhumidityStr) {
        humidityTodayTW.setText(airhumidityStr);
    }

    @Override
    public void callTheatherList(List<WeatherData> theatherDays) {
        if (adapter == null) {
            adapter = new WeatherWeekAdapter(getActivity(), theatherDays);
            DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
            itemDecoration.setDrawable(getActivity().getDrawable(R.drawable.liteseparator));
            recyclerView.addItemDecoration(itemDecoration);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setTheatherDays(theatherDays);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setCurrentPressure(String pressureStr) {
        pressureTodayTW.setText(pressureStr);
    }

    @Override
    public void setCurrentWeatherDesc(String weatherDescription) {
        descriptionTodayTW.setText(weatherDescription);
    }

    @Override
    public void callWeatherIcon(String deawableName) {
        int id = this.getResources().getIdentifier(deawableName, "drawable", getContext().getPackageName());
        weatherIconIW.setImageResource(id);
    }

    @Override
    public void callCurrentDate(String currentDate) {
        currentDateTextView.setText(currentDate);
    }
}
