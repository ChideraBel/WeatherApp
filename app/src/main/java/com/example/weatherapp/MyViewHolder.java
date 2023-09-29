package com.example.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder{
    TextView day;
    TextView hour;
    ImageView weatherIcon;
    TextView hourlyTemp;
    TextView cloudCover;

    MyViewHolder(View view){
        super(view);
        day = view.findViewById(R.id.textView_day);
        hour = view.findViewById(R.id.textView_hourly);
        weatherIcon = view.findViewById(R.id.imageView_hourlyTempIcon);
        hourlyTemp = view.findViewById(R.id.textView_hourlyTemp);
        cloudCover = view.findViewById(R.id.textView_hourlyCloudCover);
    }
}
