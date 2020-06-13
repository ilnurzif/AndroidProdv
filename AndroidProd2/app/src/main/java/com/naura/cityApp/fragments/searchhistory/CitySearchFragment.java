package com.naura.cityApp.fragments.searchhistory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.naura.cityApp.fragments.theatherdata.WeatherData;
import com.naura.myapplication.R;

import java.util.List;

public class CitySearchFragment extends Fragment implements CitySearchFragmentView {
    private RecyclerView citySearchHistoryRW;
    private SearchCityPresenter searchCityPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.city_search_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchCityPresenter= SearchCityPresenter.getInstance();
        initVisual(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        searchCityPresenter.bind(this);
    }

    @Override
    public void onPause() {
        searchCityPresenter.unBind();
        super.onPause();
    }

    private void initVisual(View view) {
        citySearchHistoryRW=view.findViewById(R.id.citySearchHistoryRW);
/*        cityLoader=CityLoader.getInstance(getContext());
        cityLoader.StartLoadWeatherWithCity();*/
        searchCityPresenter.StartLoadWeatherWithCity();
    }

    @Override
    public void loadWeather(List<WeatherData> weatherDataList) {
        HistoryListAdapter historyListAdapter = new HistoryListAdapter(weatherDataList);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        citySearchHistoryRW.addItemDecoration(itemDecoration);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        citySearchHistoryRW.setLayoutManager(linearLayoutManager);
        citySearchHistoryRW.setAdapter(historyListAdapter);
    }

/*    @Override
    public <T> void update(String eventName, T val) {
     if (eventName.equals(EventsConst.LoadWeatherWithCityFinish)) {
         List<WeatherData> weatherDataList = (List<WeatherData>) val;
         if (weatherDataList == null) return;
         HistoryListAdapter historyListAdapter = new HistoryListAdapter(weatherDataList);
         DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
         citySearchHistoryRW.addItemDecoration(itemDecoration);
         LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
         citySearchHistoryRW.setLayoutManager(linearLayoutManager);
         citySearchHistoryRW.setAdapter(historyListAdapter);
     }
    }*/
}
