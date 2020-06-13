package com.naura.cityApp.fragments.citydetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.naura.cityApp.fragments.theatherdata.WeatherData;
import com.naura.cityApp.fragments.theatherdata.TheatherWeekAdapter;
import com.naura.myapplication.R;

import java.util.List;

public class CityDetailFragment extends Fragment implements CityDetailFragmentView {
    private RecyclerView recyclerView;
    private TextView temperatureTextView;
    private TextView airhumidityTextView;
    private HumidutyView humidutyView;
    private TheatherWeekAdapter adapter;
    private FloatingActionButton historyOpenFAB;
    private FloatingActionButton floatingActionButton;

    private CityDetailPresenter cityDetailPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.city_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cityDetailPresenter= CityDetailPresenter.getInstance();
        initViews(view);
    }

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
        airhumidityTextView = view.findViewById(R.id.airhumiditytextView);
        humidutyView=view.findViewById(R.id.humidutyView);

        Bundle bundle=this.getArguments();
        cityDetailPresenter.startCityLoad(bundle);
        historyOpenFAB=view.findViewById(R.id.historyOpenFAB);
        historyOpenFAB.setOnClickListener(v -> cityDetailPresenter.openCityHistory());
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
    public void setCurrentHumiduty(int airhumidityInt) {
        humidutyView.setCurrentHumiduty(airhumidityInt);
    }

    @Override
    public void callTheatherList(List<WeatherData> theatherDays) {
        if (adapter == null) {
            adapter = new TheatherWeekAdapter(getActivity(), theatherDays);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setTheatherDays(theatherDays);
            adapter.notifyDataSetChanged();
        }
    }
}
