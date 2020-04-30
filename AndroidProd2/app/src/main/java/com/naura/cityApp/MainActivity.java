package com.naura.cityApp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.naura.cityApp.broadcast.BroadcastManager;
import com.naura.cityApp.observercode.EventsConst;
import com.naura.cityApp.observercode.Observable;
import com.naura.cityApp.observercode.Observer;
import com.naura.cityApp.ui.BaseActivity;
import com.naura.cityApp.ui.citydetail.CityData;
import com.naura.cityApp.ui.citydetail.IloadImage;
import com.naura.cityApp.ui.citylist.model.CityLoader;
import com.naura.myapplication.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends BaseActivity implements Observer {
    private AppBarConfiguration mAppBarConfiguration;
    private Observable observable;
    private CityLoader cityLoader;
    private androidx.drawerlayout.widget.DrawerLayout mainLayout;
    private Toolbar toolbar;
    private NavController navController;
    private Target mTarget;
    private IloadImage loadBackGround;
    private BroadcastManager broadcastManager;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }


    @Override
    protected void onStart() {
        super.onStart();
        dataLoad(cityLoader.getDefaultCityName());
    }

    @Override
    protected void onStop() {
        observable.notify(EventsConst.stopApp, null);
        super.onStop();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cityLoader = CityLoader.getInstance(this);

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

        loadBackGround=new loadBackGround();
        mTarget = new Target() {
            @Override
            public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
                Drawable loadedDrawable=new BitmapDrawable(getResources(),bitmap);
                loadBackGround.loadImage(loadedDrawable);
            }
            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
        broadcastManager = new BroadcastManager(this);
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

        if (eventName.equals(EventsConst.openCityHistory)) {
            navController.navigate(R.id.city_search_history);
            String title=cityLoader.getDefaultCityName()+" - история просмотров";
            toolbar.setTitle(title);
        }
    }

    private void dataLoad(String cityName) {
        CityData cityData = cityLoader.getCity(cityName);
        toolbar.setTitle(cityName);
        setBackGround(cityData);
    }

    class loadBackGround implements IloadImage {
      @Override
       public void loadImage(final Drawable drawable) {
          mainLayout.setBackground(drawable);
       }
    }

    public void setBackGround(final CityData cityData) {
        String imageUrl=cityData.getImageUrl();
                if (imageUrl.trim().equals(""))
                    Picasso.get()
                            .load(R.drawable.default_image)
                            .into(mTarget);
                else
                    Picasso.get()
                            .load(imageUrl)
                            .into(mTarget);
    }
}
