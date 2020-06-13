package com.naura.cityApp.mainactivity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.naura.cityApp.fragments.citydetail.IloadImage;
import com.naura.myapplication.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MainActivityModel {
    private IloadImage loadBackGround;
    private Target mTarget;
    private IMainPresenter mainPresenter;

    public MainActivityModel(IMainPresenter mainPresenter, Resources resources) {
        this.mainPresenter=mainPresenter;
        loadBackGround = new LoadBackGround();
        mTarget = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                Drawable loadedDrawable = new BitmapDrawable(resources, bitmap);
                loadBackGround.loadImage(loadedDrawable);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
    }

    public void setBackGround(String imageUrl ) {
        if (imageUrl.trim().equals(""))
            Picasso.get()
                    .load(R.drawable.default_image)
                    .into(mTarget);
        else
            Picasso.get()
                    .load(imageUrl)
                    .into(mTarget);
    }

    class LoadBackGround implements IloadImage {
        @Override
        public void loadImage(final Drawable drawable) {
           // mainLayout.setBackground(drawable);
            mainPresenter.setBackground(drawable);
        }
    }

}
