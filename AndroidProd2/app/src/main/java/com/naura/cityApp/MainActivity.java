package com.naura.cityApp;

import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.SearchView;

import com.google.android.material.navigation.NavigationView;
import com.naura.cityApp.observercode.EventsConst;
import com.naura.cityApp.observercode.Observable;
import com.naura.cityApp.observercode.Observer;
import com.naura.cityApp.ui.BaseActivity;
import com.naura.cityApp.ui.citydetail.CityData;
import com.naura.cityApp.ui.citylist.CityLoader;
import com.naura.cityApp.ui.citylist.OpenWeatherMapLoader;
import com.naura.myapplication.R;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends BaseActivity implements Observer {
    private AppBarConfiguration mAppBarConfiguration;
    private SearchView searchView;
    private Observable observable;
    private CityLoader cityLoader;
    private androidx.drawerlayout.widget.DrawerLayout mainLayout;
    private Toolbar toolbar;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cityLoader = OpenWeatherMapLoader.getInstance(this);

        observable = Observable.getInstance();
        observable.subscribe(this);

        mainLayout = findViewById(R.id.drawer_layout);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.city_detail, R.id.city_list)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        observable.notify(EventsConst.searchCityEvent, query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public <T> void update(String eventName, T val) {
        if (eventName.equals(EventsConst.selectCityEvent)) {
            dataLoad((String) val);
        }

        if (eventName.equals(EventsConst.selectCityLoad)) {
            dataLoad((String) val);
            navController.navigate(R.id.city_detail);
        }
    }

    private void dataLoad(String cityName) {
        CityData cityData = cityLoader.getCity(cityName);
        Drawable drawable = new BitmapDrawable(cityData.getVerticalImage());
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            mainLayout.setBackground(drawable);
        else
            mainLayout.setBackground(new BitmapDrawable(cityData.getHorisontalImage()));
        toolbar.setTitle(cityName);
    }
}
