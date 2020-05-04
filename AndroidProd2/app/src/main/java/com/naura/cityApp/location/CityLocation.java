package com.naura.cityApp.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class CityLocation {
    private static CityLocation cityLocation;
    private final Context context;
    private LocationManager mLocManager = null;
    private static final int rescode = 100;
    private String cityName = "";
    private LocListener mLocListener = null;
    private double latitude;
    private double longitude;
    private Location loc;

    private CityLocation(Context context) {
        this.context = context;
        initLocation();
    }

    public static CityLocation getInstance(Context context) {
        if (cityLocation == null)
            cityLocation = new CityLocation(context);
        return cityLocation;
    }


    public String getCityName() {
        return cityName;
    }

    public void initLocation() {
        mLocManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        } else {
            loc = mLocManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (loc != null) {
                getAddressByLoc(loc);
            }
        }
    }

    private void getAddressByLoc(Location loc) {
        final Geocoder geo = new Geocoder(context);
        List<Address> list = null;
        try {
            list = geo.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list.isEmpty()) return;
        Address a = list.get(0);
        final int index = a.getMaxAddressLineIndex();
        String postal = null;
        if (index >= 0) {
            postal = a.getAddressLine(index);
        }
        cityName = a.getSubAdminArea();
        latitude = loc.getLatitude();
        longitude = loc.getLongitude();
    }

    public void onPause() {
        if (mLocListener != null) mLocManager.removeUpdates(mLocListener);
    }

    public void updateLocation() {
        if (mLocListener == null) mLocListener = new LocListener();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000L, 1.0F, mLocListener);
    }

    public void onRequestPermissionsResult(int requestCode, int[] grantResults) {
        if (requestCode == rescode) {
            boolean permissionsGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    permissionsGranted = false;
                    break;
                }
            }
            if (permissionsGranted) ((Activity) context).recreate();
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    private final class LocListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            getAddressByLoc(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { /* Empty */ }

        @Override
        public void onProviderEnabled(String provider) { /* Empty */ }

        @Override
        public void onProviderDisabled(String provider) { /* Empty */ }
    }
}
