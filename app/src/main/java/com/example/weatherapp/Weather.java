package com.example.weatherapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Weather implements Serializable {
    private String address;
    private String timeZone;
    private int tzOffset;
    private List<DayWeather> days = new ArrayList<>();
    private CurrentCondition currentCondition;

    public Weather(){
        this.address = "";
        this.timeZone = "";
        this.tzOffset = 0;
    }

    public Weather(String address, String timeZone, int tzOffset){
        this.address = address;
        this.timeZone = timeZone;
        this.tzOffset = tzOffset;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public int getTzOffset() {
        return tzOffset;
    }

    public void setTzOffset(int tzOffset) {
        this.tzOffset = tzOffset;
    }

    public CurrentCondition getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(CurrentCondition currentCondition) {
        this.currentCondition = currentCondition;
    }

    public List<DayWeather> getDays() {
        return days;
    }

    public void setDays(List <DayWeather> days) {
        this.days.addAll(days);
    }

    public void addDay(DayWeather day){
        this.days.add(day);
    }
}
