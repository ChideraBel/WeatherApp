package com.example.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DailyWeatherHolder extends RecyclerView.ViewHolder{

    TextView dayTextView;
    TextView tempMinMaxTextView;
    TextView descriptionTextView;
    TextView precipTextView;
    TextView uvIndexTextView;
    ImageView weatherIconImageView;
    TextView morningTempTextView;
    TextView afternoonTempTextView;
    TextView eveningTempTextView;
    TextView nightTempTextView;


    public DailyWeatherHolder(@NonNull View itemView) {
        super(itemView);
        dayTextView = itemView.findViewById(R.id.dayTextView);
        tempMinMaxTextView = itemView.findViewById(R.id.tempMinMaxTextView);
        descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        precipTextView = itemView.findViewById(R.id.precipTextView);
        uvIndexTextView = itemView.findViewById(R.id.uvIndexTextView);
        weatherIconImageView = itemView.findViewById(R.id.hourlyIconImageView);
        morningTempTextView = itemView.findViewById(R.id.morningTempTextView);
        afternoonTempTextView = itemView.findViewById(R.id.afternoonTempTextView);
        eveningTempTextView = itemView.findViewById(R.id.eveningTempTextView);
        nightTempTextView = itemView.findViewById(R.id.nightTempTextView);
    }
}
