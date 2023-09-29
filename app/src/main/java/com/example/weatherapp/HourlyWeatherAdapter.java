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
import java.util.TimeZone;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<MyViewHolder>{
    private static final String TAG = "HoursAdapter";
    private final List<HourWeather> hourWeatherList;
    private final MainActivity mainAct;
    private String timeZone = "America/Chicago";

    HourlyWeatherAdapter(List<HourWeather> empList, String timeZone, MainActivity ma) {
        this.hourWeatherList = empList;
        mainAct = ma;
        this.timeZone = timeZone;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: MAKING NEW MyViewHolder");

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hour_weather, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: FILLING VIEW HOLDER Note " + position);

        HourWeather hourWeather = hourWeatherList.get(position);

        String icon = hourWeather.getIconName();
        icon = icon.replace("-", "_");
        int iconID = mainAct.getResources().getIdentifier(icon, "drawable", mainAct.getPackageName());

        long datetimeEpoch = hourWeather.getDateTimeEpoch();
        Date dateTime = new Date(datetimeEpoch * 1000);
        SimpleDateFormat dayDate = new SimpleDateFormat("EEEE", Locale.getDefault());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        SimpleDateFormat timeOnly = new SimpleDateFormat("h:mm a", Locale.getDefault());

        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        timeOnly.setTimeZone(TimeZone.getTimeZone(timeZone));
        dayDate.setTimeZone(TimeZone.getTimeZone(timeZone));

        Date currDate = new Date();
        String currDayDateStr = simpleDateFormat.format(currDate);
        Log.d("CURRENT DAY TZd", currDayDateStr);
        Log.d("COMPARE date TZd", dateTime.toString());
        String timeOnlyStr = timeOnly.format(dateTime);
        String dayDateStr = dayDate.format(dateTime);
        Log.d("COMPARE DAY ", dayDateStr);

        if(currDayDateStr.equals(dayDateStr)){
            holder.day.setText("Today");
        }else{
            holder.day.setText(dayDateStr);
        }
        holder.hour.setText(timeOnlyStr);
        holder.hourlyTemp.setText(hourWeather.getTemp());
        holder.weatherIcon.setImageResource(iconID);
        holder.cloudCover.setText(hourWeather.getConditions());
    }

    public void setTimeZone(String timeZone){
        this.timeZone = timeZone;
    }

    @Override
    public int getItemCount() {
        return hourWeatherList.size();
    }
}