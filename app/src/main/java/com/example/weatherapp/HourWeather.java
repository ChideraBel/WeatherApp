package com.example.weatherapp;

import java.io.Serializable;

public class HourWeather implements Serializable {
    private long dateTimeEpoch;
    private String temp;
    private String conditions;
    private String iconName;

    public HourWeather(long dateTimeEpoch, String temp, String conditions, String iconName){
        this.dateTimeEpoch = dateTimeEpoch;
        this.temp = temp;
        this.conditions = conditions;
        this.iconName = iconName;
    }

    public long getDateTimeEpoch() {
        return dateTimeEpoch;
    }

    public void setDateTimeEpoch(long dateTimeEpoch) {
        this.dateTimeEpoch = dateTimeEpoch;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
