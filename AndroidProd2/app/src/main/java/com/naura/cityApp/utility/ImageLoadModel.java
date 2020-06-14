package com.naura.cityApp.utility;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.naura.myapplication.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ImageLoadModel {
    private Target mTarget;
    private IImageLoadPresenter imageLoadPresenter;

    public ImageLoadModel(IImageLoadPresenter imageLoadPresenter, Resources resources) {
        this.imageLoadPresenter = imageLoadPresenter;
        mTarget = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                Drawable loadedDrawable = new BitmapDrawable(resources, bitmap);
                imageLoadPresenter.callDrawable(loadedDrawable);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
    }

    public void startLoadImage(String imageUrl) {
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
