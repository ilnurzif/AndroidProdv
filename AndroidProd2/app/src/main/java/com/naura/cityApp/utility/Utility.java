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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        int NUM_COLUMNS = 3;
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

    public static Boolean checkActualData(Date firstDate) {
           Date currentDate0 = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date firstDecorateDate= new Date(firstDate.getYear(), firstDate.getMonth(),firstDate.getDay());
            Date currentDate= new Date(currentDate0.getYear(), currentDate0.getMonth(),currentDate0.getDay());
            return currentDate.compareTo(firstDecorateDate)<=0;
    }

    public static int compareDates(Date date1, Date date2) {
     Date firstDecorateDate2= new Date(date1.getYear(), date1.getMonth(),date1.getDay());
     Date currentDate2= new Date(date2.getYear(), date2.getMonth(),date2.getDay());
     return firstDecorateDate2.compareTo(currentDate2);
    }

    public static String getCurrentDate() {
        Date date = new Date();
        String dateStr = new SimpleDateFormat("E dd.MM.yyyy").format(date);
        return dateStr;
    }

    public static String getMapIconUrl(String iconUrl) {
        String url="i"+iconUrl;
        return url;
    }
}
