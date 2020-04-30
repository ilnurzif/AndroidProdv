package com.naura.cityApp.ui.citylist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naura.cityApp.ui.citydetail.CityData;
import com.naura.cityApp.observercode.EventsConst;
import com.naura.cityApp.observercode.Observable;
import com.naura.cityApp.observercode.Observer;
import com.naura.cityApp.ui.citylist.model.CityLoader;
import com.naura.myapplication.R;

import java.util.List;

public class CityListFavorFragment extends Fragment implements Observer {
    private List<CityData> favorCityList;
    private RecyclerView recyclerView;
    private CityListAdapter cityListAdapter;
    private CityLoader cityLoader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.city_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVisual(view);
        dataLoad();
    }

    private void dataLoad() {
        favorCityList = cityLoader.getFavorCityList();
        cityListAdapter = new CityListAdapter(getContext(), favorCityList, true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cityListAdapter);
    }

    private void initVisual(View view) {
        recyclerView = view.findViewById(R.id.citiesRecyclerView);
        recyclerView.setBackgroundColor(getContext().getResources().getColor(R.color.colorGrid));
        recyclerView.getLayoutParams().height = 400;
        Observable observable = Observable.getInstance();
        observable.subscribe(this);
        this.cityLoader = CityLoader.getInstance(getContext());
    }

    @Override
    public <T> void update(String eventName, T val) {
        if (eventName.equals(EventsConst.likeSelectEvent)) {
            cityListAdapter.setCityDataList(cityLoader.getFavorCityList());
            cityListAdapter.notifyDataSetChanged();
        }
    }
}
