package com.naura.cityApp.ui.citydetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.naura.cityApp.observercode.EventsConst;
import com.naura.cityApp.observercode.Observable;
import com.naura.cityApp.observercode.Observer;
import com.naura.cityApp.ui.citylist.CityLoader;
import com.naura.cityApp.ui.citylist.OpenWeatherMapLoader;
import com.naura.cityApp.ui.theatherdata.TheatherData;
import com.naura.cityApp.ui.theatherdata.TheatherWeekAdapter;
import com.naura.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CityDetailFragment extends Fragment implements Observer {
    private RecyclerView recyclerView;
    private TextView temperatureTextView;
    private TextView airhumidityTextView;
    private HumidutyView humidutyView;
    private List<TheatherData> theatherDays = new ArrayList<>();
    private TheatherWeekAdapter adapter;
    private CityLoader cityLoader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.city_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.weekdays);
        temperatureTextView = view.findViewById(R.id.temperaturetextView);
        airhumidityTextView = view.findViewById(R.id.airhumiditytextView);
        humidutyView=view.findViewById(R.id.humidutyView);

        Observable observable = Observable.getInstance();
        observable.subscribe(this);

        cityLoader = OpenWeatherMapLoader.getInstance(getActivity());
        cityLoader.startLoad();

        observable.notify(EventsConst.selectCityEvent, cityLoader.getDefaultCityName());
    }

    private void dataLoad(String cityName, List<TheatherData> theatherDays) {
        if (adapter == null) {
            adapter = new TheatherWeekAdapter(getActivity(), theatherDays);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setTheatherDays(theatherDays);
            adapter.notifyDataSetChanged();
        }

        String temperatureNow = theatherDays.get(0).getTemperature();
        temperatureTextView.setText(temperatureNow);

        String airhumidity = theatherDays.get(0).getAirhumidity();
        int airhumidity_int= (int) Math.round(Double. parseDouble(airhumidity));
        humidutyView.setCurrentHumiduty(airhumidity_int);
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
    public <T> void update(String eventName, T val) {
        if (eventName.equals(EventsConst.selectCityEvent)) {
            cityLoader.setDefaultCityName((String) val);
            cityLoader.startLoad();
            if (getActivity() == null) return;
        }
        if (eventName.equals(EventsConst.cityLoadFinish)) {
            List<TheatherData> cityTheatherList = (List<TheatherData>) val;
            dataLoad(cityLoader.getDefaultCityName(), cityTheatherList);
        }
    }
}
