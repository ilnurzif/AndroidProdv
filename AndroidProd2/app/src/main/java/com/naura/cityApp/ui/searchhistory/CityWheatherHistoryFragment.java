package com.naura.cityApp.ui.searchhistory;

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

import com.naura.cityApp.observercode.Observer;
import com.naura.cityApp.ui.citylist.model.CityLoader;
import com.naura.cityApp.ui.theatherdata.TheatherData;
import com.naura.myapplication.R;

import java.util.List;

public class CityWheatherHistoryFragment extends Fragment implements Observer {
    private RecyclerView citySearchHistoryRW;
    private CityLoader cityLoader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.city_search_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVisual(view);
    }

    private void initVisual(View view) {
        citySearchHistoryRW=view.findViewById(R.id.citySearchHistoryRW);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        cityLoader=CityLoader.getInstance(getContext());
        List<TheatherData>  theatherDataList=cityLoader.getAllWeatherWithCity(cityLoader.getDefaultCityName());
        if (theatherDataList==null) return;
        HistoryListAdapter historyListAdapter=new HistoryListAdapter(theatherDataList);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
         citySearchHistoryRW.addItemDecoration(itemDecoration);
        citySearchHistoryRW.setLayoutManager(linearLayoutManager);
        citySearchHistoryRW.setAdapter(historyListAdapter);
    }


    @Override
    public <T> void update(String eventName, T val) {

    }
}
