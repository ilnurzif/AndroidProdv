package com.naura.cityApp.fragments.citylist.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.naura.cityApp.basemodel.CityData;
import com.naura.cityApp.fragments.citylist.CityListPresenter;
import com.naura.cityApp.utility.Utility;
import com.naura.myapplication.R;

import java.util.List;

public class CityListFragment extends Fragment implements FragmentView {
    private RecyclerView recyclerView;
    private CityListAdapter cityListAdapter;
    private CityListPresenter cityListPresenter;
    private LinearLayout findProgressLL;
    private ItemTouchHelper itemTouchHelper;

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

    @Override
    public void onResume() {
        super.onResume();
        cityListPresenter.bind(this);
    }

    @Override
    public void onPause() {
        cityListPresenter.unBind();
        super.onPause();
    }

    private void initVisual(View view) {
        recyclerView = view.findViewById(R.id.citiesRecyclerView);
        findProgressLL = view.findViewById(R.id.findProgressLL);
    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            int del_id = viewHolder.getAdapterPosition();
            cityListPresenter.delete(del_id);
            Log.d("TAG", "onSwiped: ");
        }
    };

    private void dataLoad() {
        cityListPresenter = new CityListPresenter();
        cityListAdapter = new CityListAdapter(getActivity(), cityListPresenter.getCityList(), false, cityListPresenter);
        Utility.initReciclerViewAdapter(getActivity(), cityListAdapter, recyclerView);

        itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void setCityDataList(List<CityData> cityList) {
        cityListAdapter.setCityDataList(cityList);
        cityListAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCityList(List<CityData> cityList) {
        cityListAdapter.setCityDataList(cityList);
        cityListAdapter.notifyDataSetChanged();
        findProgressLL.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setFavorCityList(List<CityData> favorCityList) {
    }

    @Override
    public void startCityEvent(String cityName) {
        findProgressLL.setVisibility(View.VISIBLE);
    }

    @Override
    public void CityFoundErrorMsg(String errMsg) {
        findProgressLL.setVisibility(View.INVISIBLE);
        Toast.makeText(getContext(), errMsg, Toast.LENGTH_LONG).show();
    }
}
