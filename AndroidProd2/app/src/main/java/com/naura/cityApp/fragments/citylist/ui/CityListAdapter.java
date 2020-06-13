package com.naura.cityApp.fragments.citylist.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naura.cityApp.fragments.citydetail.CityData;
import com.naura.cityApp.fragments.citylist.CityListPresenter;
import com.naura.cityApp.utility.Utility;
import com.naura.myapplication.R;

import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {
    private List<CityData> cityDataList;
    private Context context;
    private int currentPosition = -1;
    private Boolean cardMode = false;
    private CityListPresenter cityListPresenter;


    public CityListAdapter(Context context, List<CityData> cityDataList, Boolean cardMode, CityListPresenter cityListPresenter) {
        this.cityDataList = cityDataList;
        this.context = context;
        this.cardMode = cardMode;
        this.cityListPresenter = cityListPresenter;
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
        String imageUrl = cityListPresenter.getImageUrl(cityName);
        if (cardMode) {
            if (imageUrl.trim().equals(""))
                Utility.loadImage(holder.bigImage, R.drawable.default_image, false);
            else
                Utility.loadImage(holder.bigImage, imageUrl, false);
        } else {
            if (imageUrl.trim().equals(""))
                Utility.loadImage(holder.circleSmallImageView, R.drawable.default_image, true);
            else
                Utility.loadImage(holder.circleSmallImageView, imageUrl, true);
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

    private void SetOnClickHolder(@NonNull final ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(view -> {
            holder.cityNameTextView = view.findViewById(R.id.cityNameTextView);
            holder.citySmallImageView = view.findViewById(R.id.citySmallImageView);
            if (cardMode) {
                holder.bigImage = view.findViewById(R.id.cityBigImageView);
            }
            currentPosition = position;
            notifyDataSetChanged();
            cityListPresenter.setDefaultCityName(holder.cityNameTextView.getText().toString());
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityNameTextView;
        ImageView citySmallImageView;
        ImageView bigImage;
        ImageView circleSmallImageView;

        ViewHolder(View view) {
            super(view);
            cityNameTextView = view.findViewById(R.id.cityNameTextView);
            bigImage = view.findViewById(R.id.cityBigImageView);
            citySmallImageView = view.findViewById(R.id.citySmallImageView);
            if (!cardMode) {
                circleSmallImageView = view.findViewById(R.id.circleSmallImageView);
                String str = "";
            }
            citySmallImageView.setOnClickListener(v -> {
                final String cityName = cityNameTextView.getText().toString();
                cityListPresenter.setLikeCity(cityName);
                Snackbar.make(citySmallImageView, R.string.city_add_question, Snackbar.LENGTH_LONG).
                        setAction(R.string.Yes_const, v1 -> {
                            cityListPresenter.likeSelectEvent(cityName);
                        }).show();
            });
        }
    }

    private void repaintView(@NonNull ViewHolder holder, int position) {
        if (!cardMode)
            Utility.repaintView(context, holder, position, currentPosition);
    }
}
