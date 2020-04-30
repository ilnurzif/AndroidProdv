package com.naura.cityApp.ui.citylist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.naura.cityApp.observercode.EventsConst;
import com.naura.cityApp.observercode.Observable;
import com.naura.cityApp.ui.citydetail.CityData;
import com.naura.cityApp.ui.citylist.model.CityLoader;
import com.naura.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {
    private List<CityData> cityDataList;
    private Context context;
    private int currentPosition = -1;
    private Boolean cardMode = false;
    private Observable observable = Observable.getInstance();
    private CityLoader cityLoader;


    public CityListAdapter(Context context, List<CityData> cityDataList, Boolean cardMode) {
        this.cityDataList = cityDataList;
        this.context = context;
        this.cardMode = cardMode;
        this.cityLoader=CityLoader.getInstance(context);
    }

    public void setCityDataList(List<CityData> cityDataList) {
        this.cityDataList = cityDataList;
    }

    @NonNull
    @Override
    public CityListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        if (cardMode) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.city_item_card, parent, false);
        } else {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.city_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CityData cityData = cityDataList.get(position);
        holder.cityNameTextView.setText(cityData.getName());
        if (cityData.isFavoriteCity()) {
            holder.citySmallImageView.setBackground(context.getResources().getDrawable(R.drawable.like_red));
        } else
            holder.citySmallImageView.setBackground(context.getResources().getDrawable(R.drawable.like_brown));
        String cityName = holder.cityNameTextView.getText().toString();
        String imageUrl=cityLoader.getCity(cityName).getImageUrl();
        if (cardMode) {
            holder.citySmallImageView.setBackground(context.getResources().getDrawable(R.drawable.like_red));
            if (imageUrl.trim().equals(""))
                Picasso.get()
                        .load(R.drawable.default_image)
                        .into(holder.bigImage);
            else
                Picasso.get()
                        .load(imageUrl)
                        .into(holder.bigImage);
        }
        else
        {
            if (imageUrl.trim().equals(""))
                Picasso.get()
                        .load(R.drawable.default_image)
                        .transform(new CircleTransformation())
                        .into(holder.circleSmallImageView);
            else
                Picasso.get()
                        .load(imageUrl)
                        .transform(new CircleTransformation())
                        .into(holder.circleSmallImageView);
     }
        SetOnClickHolder(holder, position);
        repaintView(holder, position);
    }


    @Override
    public int getItemCount() {
        return cityDataList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    private void openCityList(Activity activity, String cityName) {
        cityLoader.setDefaultCityName(cityName);
        observable = Observable.getInstance();
        observable.notify(EventsConst.selectCityLoad, cityName);
    }

    private void SetOnClickHolder(@NonNull final ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.cityNameTextView = view.findViewById(R.id.cityNameTextView);
                holder.citySmallImageView = view.findViewById(R.id.citySmallImageView);
                if (cardMode) {
                    holder.bigImage = view.findViewById(R.id.cityBigImageView);
                }
                currentPosition = position;
                notifyDataSetChanged();
                Activity activity = (Activity) context;
                openCityList(activity, holder.cityNameTextView.getText().toString());
            }
        });
    }

    public void searchCity(String cityName) {
        cityLoader.searchCity(cityName);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityNameTextView;
        ImageView citySmallImageView;
        ImageView bigImage;
        ImageView circleSmallImageView;

        ViewHolder(View view) {
            super(view);
            cityNameTextView = view.findViewById(R.id.cityNameTextView);
            bigImage=view.findViewById(R.id.cityBigImageView);
            citySmallImageView = view.findViewById(R.id.citySmallImageView);
            if (!cardMode) {
                circleSmallImageView= view.findViewById(R.id.circleSmallImageView);
                String str="";
            }
            citySmallImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String cityName = cityNameTextView.getText().toString();
                    cityLoader.SetlikeCity(cityName);
                    Snackbar.make(citySmallImageView, R.string.city_add_question, Snackbar.LENGTH_LONG).
                            setAction(R.string.Yes_const, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    observable.notify(EventsConst.likeSelectEvent, cityName);
                                }
                            }).show();
                }
            });
        }
    }

    private void repaintView(@NonNull ViewHolder holder, int position) {
        if (!cardMode) {
            int color = ContextCompat.getColor(context, android.R.color.transparent);
            if (currentPosition == position)
                color = ContextCompat.getColor(context, R.color.colorGrid);
            holder.itemView.setBackgroundColor(color);
        }
    }
}
