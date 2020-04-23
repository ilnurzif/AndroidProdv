package com.naura.cityApp.ui.citydetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.naura.cityApp.ui.theatherdata.TheatherData;
import com.naura.myapplication.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class CityData {
    private String key;
    private String name;
    private List<TheatherData> theatherDays;
    private String infoUrl;
    private boolean favoriteCity = true;
    private String imageUrl;

    public boolean isFavoriteCity() {
        return favoriteCity;
    }

    public void setFavoriteCity(boolean favoriteCity) {
        this.favoriteCity = favoriteCity;
    }

    public CityData(String key,
                    String name,
                    List<TheatherData> theatherDays,
                    String infoUrl,
                    String imageUrl) {

        this.key = key;
        this.name = name;
        this.theatherDays = theatherDays;
        this.infoUrl = infoUrl;
        this.imageUrl = imageUrl;
    }

    public String getKey() {
        return key;
    }

    public void getCityDrawable(final Activity activity, final IloadImage loadImage) {
        final Target mTarget = new Target() {
            @Override
            public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
               Drawable loadedDrawable=new BitmapDrawable(activity.getResources(),bitmap);
               loadImage.loadImage(loadedDrawable);
            }
            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (imageUrl.trim().equals(""))
                    Picasso.get()
                            .load(R.drawable.default_image)
                            .into(mTarget);
                else
                    Picasso.get()
                            .load(imageUrl)
                            .into(mTarget);
            }
        });
    }

    public String getName() {
        return name;
    }

    public List<TheatherData> getTheatherDays() {
        return theatherDays;
    }

    public void openInfo(Context context) {
        Uri uri = Uri.parse(infoUrl);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(browserIntent);
    }

    public String getUrl() {
        return imageUrl;
    }
}
