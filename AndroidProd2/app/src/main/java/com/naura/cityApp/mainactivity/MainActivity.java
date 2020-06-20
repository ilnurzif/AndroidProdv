package com.naura.cityApp.mainactivity;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.SearchView;

import com.google.android.material.navigation.NavigationView;
import com.naura.cityApp.App;
import com.naura.cityApp.broadcast.BroadcastManager;
import com.naura.cityApp.location.CityLocation;
import com.naura.myapplication.R;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends BaseActivity implements MainView {
    private static int REQUEST_LOCATION_PERMISSION = 1;
    private AppBarConfiguration mAppBarConfiguration;
    private androidx.drawerlayout.widget.DrawerLayout mainLayout;
    private Toolbar toolbar;
    private NavController navController;
    private BroadcastManager broadcastManager;
    private CityLocation cityLocation;
    private MainActivityPresener mainActivityPresener;
    private MenuItem searchItem;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        App.getComponent().inject(this);
        mainActivityPresener = MainActivityPresener.getInstance();
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
           cityLocation.updateLocation();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
        mainActivityPresener.bind(this);
    }

    @Override
    protected void onPause() {
         cityLocation.onPause();
        mainActivityPresener.unBind();
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainActivityPresener.startDataLoad();
    }

    @Override
    protected void onStop() {
        mainActivityPresener.stopApp();
        super.onStop();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainLayout = findViewById(R.id.drawer_layout);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.city_detail, R.id.city_list, R.id.maps, R.id.fovorit_city)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        broadcastManager = new BroadcastManager(this);
        cityLocation = CityLocation.getInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        searchItem = menu.findItem(R.id.search);
        searchItem.setVisible(false);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        mainActivityPresener.startSearchCity(query);
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
    public void setToolBarTitle(String cityName) {
        toolbar.setTitle(cityName);
    }

    @Override
    public void setBackground(Drawable drawable) {
        mainLayout.setBackground(drawable);
    }

    @Override
    public void citySearchHistoryOpen() {
        navController.navigate(R.id.city_search_history);
    }

    @Override
    public void cityDetailHistoryOpen() {
        navController.navigate(R.id.city_detail);
    }

    @Override
    public void setSearchFieldVisible(Boolean visible) {
        searchItem.setVisible(visible);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION)
          cityLocation.onRequestPermissionsResult(requestCode, grantResults);
    }
}
