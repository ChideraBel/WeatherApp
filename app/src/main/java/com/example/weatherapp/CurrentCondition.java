package com.example.weatherapp;

import java.io.Serializable;

public class CurrentCondition implements Serializable {
    private long dateTimeEpoch;
    private String temp;
    private String feelsLike;
    private double humidity;
    private String windGust;
    private String windSpeed;
    private int windDirection;
    private String visibility;
    private int cloudCover;
    private int uvIndex;
    private String conditions;
    private String iconName;
    private long sunriseEpoch;
    private long sunsetEpoch;

    public CurrentCondition(){

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

    public String getFeelsLike() {return feelsLike;}

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getWindGust() {
        return windGust;
    }

    public void setWindGust(String windGust) {
        this.windGust = windGust;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public int getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(int cloudCover) {
        this.cloudCover = cloudCover;
    }

    public int getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(int uvIndex) {
        this.uvIndex = uvIndex;
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

    public long getSunriseEpoch() {
        return sunriseEpoch;
    }

    public void setSunriseEpoch(long sunriseEpoch) {
        this.sunriseEpoch = sunriseEpoch;
    }

    public long getSunsetEpoch() {
        return sunsetEpoch;
    }

    public void setSunsetEpoch(long sunsetEpoch) {
        this.sunsetEpoch = sunsetEpoch;
    }
}
