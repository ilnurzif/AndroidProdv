package com.naura.cityApp.fragments.citylist;

import com.naura.cityApp.App;
import com.naura.cityApp.basemodel.CityLoader;
import com.naura.cityApp.observercode.EventsConst;
import com.naura.cityApp.observercode.Observable;
import com.naura.cityApp.observercode.Observer;
import com.naura.cityApp.basemodel.CityData;
import com.naura.cityApp.fragments.citylist.ui.FragmentView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CityListPresenter implements Observer {
    private FragmentView fragmentView;

    @Inject
    Observable observable;

    @Inject
    CityLoader cityLoader;

    public CityListPresenter() {
        App.getComponent().inject(this);
        observable.subscribe(this);
    }

    public List<CityData> getCityList() {
        return cityLoader.getCityList();
    }

    public void bind(FragmentView fragmentView) {
        this.fragmentView = fragmentView;
        observable.notify(EventsConst.searchFieldVisible, true);
    }

    public void unBind() {
        observable.notify(EventsConst.searchFieldVisible, false);
        fragmentView = null;
    }

    @Override
    public <T> void update(String eventName, T val) {
        try {
            if (eventName.equals(EventsConst.searchCityEvent)) {
                String cityName = (String) val;
                cityLoader.searchCity(cityName);
            }

            if (eventName.equals(EventsConst.addNewCity)) {
                List<CityData> cityList = new ArrayList<>();
                if (val == null)
                    return;
                CityData cityData = (CityData) val;
                if (cityData == null)
                    return;
                cityList.add(cityData);
                if (fragmentView != null)
                    fragmentView.updateCityList(cityList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getImageUrl(String cityName) {
        return cityLoader.getCity(cityName).getImageUrl();
    }

    public void setDefaultCityName(String cityName) {
        cityLoader.setDefaultCityName(cityName);
        observable.notify(EventsConst.selectCityLoad, cityName);
    }

    public void setLikeCity(String cityName) {
        cityLoader.SetlikeCity(cityName);
    }

    public void likeSelectEvent(String cityName) {
        if (fragmentView != null && cityLoader != null) {
            fragmentView.setCityDataList(cityLoader.getCityList());
            fragmentView.setFavorCityList(cityLoader.getFavorCityList());
            cityLoader.favoriteStateUpdate(cityName);
        }
    }

    public List<CityData> getFavorCityList() {
        return cityLoader.getFavorCityList();
    }
}
