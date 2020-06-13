package com.naura.cityApp.utility;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.naura.myapplication.R;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Utility {
    public static void initReciclerViewAdapter(Activity activity, RecyclerView.Adapter adapter, RecyclerView rw) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(activity.getDrawable(R.drawable.separator));
        rw.addItemDecoration(itemDecoration);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rw.setLayoutManager(linearLayoutManager);
        rw.setAdapter(adapter);
    }

    public static void initHorizontalReciclerViewAdapter(Activity activity, RecyclerView.Adapter adapter, RecyclerView rw) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rw.setLayoutManager(linearLayoutManager);
        rw.setAdapter(adapter);
    }

    public static void initGridReciclerViewAdapter(Activity activity, RecyclerView.Adapter adapter, RecyclerView rw) {
     int NUM_COLUMNS=3;
     rw.setLayoutManager(new GridLayoutManager(activity, NUM_COLUMNS));
     rw.setAdapter(adapter);
    }

    public static void repaintView(Context context, RecyclerView.ViewHolder holder, int position, int currentPosition) {
        int color;
        if (currentPosition == position)
            color = ContextCompat.getColor(context, R.color.colorGrid);
        else
            color = Color.TRANSPARENT;
        holder.itemView.setBackgroundColor(color);
    }

    public static void loadImage(ImageView imageView, @DrawableRes int resourceId, boolean b) {
        if (b) {
            Picasso.get()
                    .load(resourceId)
                    .transform(new CircleTransformation())
                    .into(imageView);
            return;
        }

        Picasso.get()
                .load(resourceId)
                .into(imageView);
    }

    public static void loadImage(ImageView imageView, String path, boolean b) {
        if (b) {
            Picasso.get()
                    .load(path)
                    .transform(new CircleTransformation())
                    .into(imageView);
            return;
        }

        Picasso.get()
                .load(path)
                .into(imageView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Boolean checkActualData(Date firstDate) {
        Date currentDate=new Date();
        LocalDate firstlocalDate= firstDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate currlocalDate= currentDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return currlocalDate.compareTo(firstlocalDate)<=0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static int compareDates(Date date1, Date date2) {
        Date currentDate=new Date();
        LocalDate firstLocalDate= date1.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate secLocalDate= date2.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return firstLocalDate.compareTo(secLocalDate);
    }

}
