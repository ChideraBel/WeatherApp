package com.example.weatherapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DayWeather implements Serializable {
    private long dateTimeEpoch;
    private String tempMax;
    private String tempMin;
    private int precipProb;
    private int uvIndex;
    private String description;
    private String iconName;
    private List hours = new ArrayList<HourWeather>();

    public DayWeather(){

    }

    public long getDateTimeEpoch() {
        return dateTimeEpoch;
    }

    public void setDateTimeEpoch(long dateTimeEpoch) {
        this.dateTimeEpoch = dateTimeEpoch;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public int getPrecipProb() {
        return precipProb;
    }

    public void setPrecipProb(int precipProb) {
        this.precipProb = precipProb;
    }

    public int getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(int uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public List<HourWeather> getHours() {
        return hours;
    }

    public void setHours(List <HourWeather> hours) {
        this.hours.addAll(hours);
    }
}
