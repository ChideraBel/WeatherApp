package com.example.weatherapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherHolder>{
    private final List<DayWeather> dayWeathers;
    private final DailyWeatherActivity dailyWeatherActivity;

    DailyWeatherAdapter(List<DayWeather> empList, DailyWeatherActivity da) {
        this.dayWeathers = empList;
        dailyWeatherActivity = da;
    }

    @NonNull
    @Override
    public DailyWeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_weather_item, parent, false);

        itemView.setOnClickListener(dailyWeatherActivity);
        itemView.setOnLongClickListener(dailyWeatherActivity);

        return new DailyWeatherHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyWeatherHolder holder, int position) {
        DayWeather dayWeather = dayWeathers.get(position);

        String icon = dayWeather.getIconName();
        icon = icon.replace("-", "_");
        int iconID = dailyWeatherActivity.getResources().getIdentifier(icon, "drawable", dailyWeatherActivity.getPackageName());

        long datetimeEpoch = dayWeather.getDateTimeEpoch();
        Date dateTime = new Date(datetimeEpoch * 1000);
        SimpleDateFormat dayDate = new SimpleDateFormat("EEEE MM/dd", Locale.getDefault());
        String dayDateStr = dayDate.format(dateTime);

        holder.dayTextView.setText(dayDateStr);
        holder.tempMinMaxTextView.setText(dayWeather.getTempMax()+"/"+dayWeather.getTempMin());
        holder.descriptionTextView.setText(dayWeather.getDescription());
        holder.precipTextView.setText("("+dayWeather.getPrecipProb()+"% precip)");
        holder.uvIndexTextView.setText("UV Index: "+dayWeather.getUvIndex());
        holder.weatherIconImageView.setImageResource(iconID);
        holder.morningTempTextView.setText(dayWeather.getHours().get(8).getTemp());
        holder.afternoonTempTextView.setText(dayWeather.getHours().get(13).getTemp());
        holder.eveningTempTextView.setText(dayWeather.getHours().get(17).getTemp());
        holder.nightTempTextView.setText(dayWeather.getHours().get(23).getTemp());
    }

    @Override
    public int getItemCount() {
        return dayWeathers.size();
    }
}
