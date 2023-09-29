package com.example.weatherapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{
    TextView currDateTimeTextView;
    TextView tempTextView;
    TextView feelsLikeTextView;
    TextView cloudCoverTextView;
    TextView windDirTextView;
    TextView humidityTextView;
    TextView uvIndexTextView;
    TextView visibilityTextView;
    TextView sunsetTextView;
    TextView sunriseTextView;
    TextView morningTempTextView;
    TextView afternoonTempTextView;
    TextView eveningTempTextView;
    TextView nightTempTextView;
    ImageView tempIconImageView;

    HourlyWeatherAdapter adapter;

    Weather weather = new Weather();
    String city;
    String units = "us";

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd h:mm a, yyyy", Locale.getDefault());
    Date currDate = new Date();
    List<HourWeather> list = new ArrayList<>();

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(loadFile() != null){
            city = loadFile()[0];
            units = loadFile()[1];
        }else{
            city = "Chicago, IL";
            units = "us";
        }

        if(hasNetworkConnection()){
            setContentView(R.layout.activity_main);

            weather.setTimeZone("America/Chicago");

            currDateTimeTextView = findViewById(R.id.textView_CurrDateTime);
            tempTextView = findViewById(R.id.textView_temp);
            feelsLikeTextView = findViewById(R.id.textView_feelsLike);
            cloudCoverTextView = findViewById(R.id.textView_cloudCover);
            windDirTextView = findViewById(R.id.textView_windDir);
            humidityTextView = findViewById(R.id.textView_humidity);
            uvIndexTextView = findViewById(R.id.textView_uvIndex);
            visibilityTextView = findViewById(R.id.textView_visibilty);
            sunsetTextView = findViewById(R.id.textView_sunset);
            sunriseTextView = findViewById(R.id.textView_sunrise);
            morningTempTextView = findViewById(R.id.textView_morningTemp);
            afternoonTempTextView = findViewById(R.id.textView_afternoonTemp);
            eveningTempTextView = findViewById(R.id.textView_eveningTemp);
            nightTempTextView = findViewById(R.id.textView_nightTemp);
            tempIconImageView = findViewById(R.id.imageView_tempIcon);

            loadRequest();

            adapter = new HourlyWeatherAdapter(list, "America/Chicago", MainActivity.this);
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        }else{
            setContentView(R.layout.activity_empty);
        }

    }

    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_screen_menu, menu);
        if(!hasNetworkConnection()){
            menu.setGroupEnabled(R.id.menu_group, false);
        }
        MenuItem unitsItem = menu.getItem(0);

        if(units.equals("us")){
            unitsItem.setTitle("farenheit");
            unitsItem.setIcon(R.drawable.units_f);
        }else{
            unitsItem.setTitle("celcius");
            unitsItem.setIcon(R.drawable.units_c);
        }

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toggle_units:
                String current = item.getTitle().toString();
                if(current.equals("farenheit")){
                    item.setIcon(R.drawable.units_c);
                    item.setTitle("celcuis");
                    toggleUnits("metric");
                }else{
                    item.setIcon(R.drawable.units_f);
                    item.setTitle("farenheit");
                    toggleUnits("us");
                }
                return true;
            case R.id.daily_forecast:
                openDailyForecast();
                return true;
            case R.id.change_location:
                changeLocation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void toggleUnits(String newUnit){
        units = newUnit;
        loadRequest();
    }
    public void openDailyForecast(){
        Intent intent = new Intent(MainActivity.this, DailyWeatherActivity.class);
        intent.putExtra(Weather.class.getName(), this.weather);
        startActivity(intent);
    }

    public void changeLocation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter a location");

        // Set up the input
        final EditText input = new EditText(this);

        builder.setMessage("For US locations enter a city as 'City' or 'City, State' \n\nFor international locations enter a city as 'City, Country'");

        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                city = input.getText().toString().trim();
                loadRequest();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void loadRequest() {
        String tempCity = weather.getAddress();
        weather = new Weather();
        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"+city+"?unitGroup="+units+"&key=CZ8TFXS9RGPC44GG97MSEKTYA&contentType=json";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response != null){
                                JSONArray days = response.getJSONArray("days");
                                weather.setAddress(response.getString("address"));
                                weather.setTimeZone(response.getString("timezone"));
                                weather.setTzOffset(response.getInt("tzoffset"));
                                weather.setCurrentCondition(loadCurrentCondition(response.getJSONObject("currentConditions")));

                                list.clear();
                                loadDays(days);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Unable to load data. " + error.getMessage() +" response.", Toast.LENGTH_SHORT).show();
                city = tempCity;
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    public void loadDays(JSONArray days){

        for (int i = 0 ; i < days.length() ; i ++){
            try {
                JSONObject day = days.getJSONObject(i);

                DayWeather dayWeather = new DayWeather();
                JSONArray hours = day.getJSONArray("hours");

                dayWeather.setDateTimeEpoch(day.getLong("datetimeEpoch"));
                dayWeather.setTempMax(Math.round(day.getDouble("tempmax"))+(units.equals("us") ? "°F": "°C"));
                dayWeather.setTempMin(Math.round(day.getDouble("tempmin"))+(units.equals("us") ? "°F": "°C"));
                dayWeather.setPrecipProb(day.getInt("precipprob"));
                dayWeather.setUvIndex(day.getInt("uvindex"));
                dayWeather.setDescription(day.getString("description"));
                dayWeather.setIconName(day.getString("icon"));
                dayWeather.setHours(loadHours(hours));

                weather.addDay(dayWeather);
//                hourWeatherList.add(hourWeather);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        fillScreen();
    }

    public List<HourWeather> loadHours(JSONArray hours){
        List<HourWeather> hourWeathers = new ArrayList<>();
        for (int i = 0 ; i < hours.length() ; i ++) {
            try {
                JSONObject jsonObject = hours.getJSONObject(i);
                long dateTimeEpoch = jsonObject.getLong("datetimeEpoch");
                String temp = Math.round(jsonObject.getDouble("temp"))+(units.equals("us") ? "°F": "°C");
                String conditions = jsonObject.getString("conditions");
                String iconName = jsonObject.getString("icon");

                HourWeather hourWeather = new HourWeather(dateTimeEpoch, temp, conditions, iconName);
                hourWeathers.add(hourWeather);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return hourWeathers;
    }

    public CurrentCondition loadCurrentCondition(JSONObject condition){
        CurrentCondition currentCondition = new CurrentCondition();
        try {
            currentCondition.setDateTimeEpoch(condition.getLong("datetimeEpoch"));
            currentCondition.setTemp(Math.round(condition.getDouble("temp"))+(units.equals("us") ? "°F": "°C"));
            currentCondition.setConditions(condition.getString("conditions"));
            currentCondition.setSunsetEpoch(condition.getLong("sunsetEpoch"));
            currentCondition.setSunriseEpoch(condition.getLong("sunriseEpoch"));
            currentCondition.setCloudCover(condition.getInt("cloudcover"));
            currentCondition.setVisibility(condition.getDouble("visibility")+(units.equals("us") ? " mi": " km"));
            currentCondition.setWindDirection(condition.getInt("winddir"));
            currentCondition.setUvIndex(condition.getInt("uvindex"));
            currentCondition.setIconName(condition.getString("icon"));
            currentCondition.setWindSpeed(condition.getDouble("windspeed")+(units.equals("us") ? " mph": " kph"));
            currentCondition.setWindGust(condition.getString("windgust")+(units.equals("us") ? " mph": " kph"));
            currentCondition.setHumidity(condition.getDouble("humidity"));
            currentCondition.setFeelsLike(Math.round(condition.getDouble("feelslike"))+(units.equals("us") ? "°F": "°C"));

            return currentCondition;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return currentCondition;
    }

    public void fillScreen(){
        CurrentCondition condition = weather.getCurrentCondition();
        List<HourWeather> mainHourWeathers = weather.getDays().get(0).getHours();
        SimpleDateFormat timeOnly = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String icon = condition.getIconName();
        setTitle(city);
        icon = icon.replace("-", "_");
        int iconID = this.getResources().getIdentifier(icon, "drawable", this.getPackageName());

        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(weather.getTimeZone()));
        String dateTime = simpleDateFormat.format(currDate);

        currDateTimeTextView.setText(dateTime);
        tempTextView.setText(condition.getTemp());
        feelsLikeTextView.setText("Feels like " + condition.getFeelsLike());
        cloudCoverTextView.setText(condition.getConditions() + " (" + condition.getCloudCover() + "% clouds)");
        if(condition.getWindGust().contains("null")){
            windDirTextView.setText("Winds: "+getDirection(condition.getWindDirection())+" at "+condition.getWindSpeed());
        }else{
            windDirTextView.setText("Winds: "+getDirection(condition.getWindDirection())+" at "+condition.getWindSpeed()+" gusting to "+condition.getWindGust());
        }
        humidityTextView.setText("Humidity: "+condition.getHumidity()+"%");
        uvIndexTextView.setText("UV Index: "+condition.getUvIndex());
        visibilityTextView.setText("Visibility: "+condition.getVisibility());
        morningTempTextView.setText(mainHourWeathers.get(8).getTemp());
        afternoonTempTextView.setText(mainHourWeathers.get(13).getTemp());
        eveningTempTextView.setText(mainHourWeathers.get(17).getTemp());
        nightTempTextView.setText(mainHourWeathers.get(23).getTemp());
        sunriseTextView.setText("Sunrise: "+timeOnly.format(new Date(condition.getSunriseEpoch()*1000)));
        sunsetTextView.setText("Sunset: "+timeOnly.format(new Date(condition.getSunsetEpoch()*1000)));
        tempIconImageView.setImageResource(iconID);

        fillRecyclerView();
    }

    public void fillRecyclerView(){

        for(int i = 0; i<4; i++){
            if(i == 0){
                List<HourWeather> dayOneList = weather.getDays().get(0).getHours();
                Log.d("WEATHER CLASS: ", weather.getDays().get(0).getHours().get(0)+"");
                int startIdx = findStartHourIndex(dayOneList);
                if(startIdx == -1){
                    continue;
                }else{
                    list.addAll(dayOneList.subList(startIdx, dayOneList.size()-1));
                }
            }else{
                list.addAll(weather.getDays().get(i).getHours());
            }
        }

        adapter = new HourlyWeatherAdapter(list, weather.getTimeZone(),  MainActivity.this);
        Log.d("*********************** TIMEZONE: ", weather.getTimeZone());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public int findStartHourIndex(List<HourWeather> hourList){
        Date currDate = new Date(currDateTimeTextView.getText().toString());
        Log.d("***************************** CURRENT DATE: ", currDate+"");
        for (int i = 0; i < hourList.size(); i++){
            Date dateTime = new Date(hourList.get(i).getDateTimeEpoch() * 1000); // Java time values need milliseconds
            SimpleDateFormat fullDate = new SimpleDateFormat("EEE MMM dd h:mm a, yyyy", Locale.getDefault());
            fullDate.setTimeZone(TimeZone.getTimeZone(weather.getTimeZone()));
            String formattedDate = fullDate.format(dateTime);

            Date compareDate = new Date(formattedDate);
            Log.d("***************************** LIST DATE: ", compareDate+"");
            if(compareDate.after(currDate)){
                Log.d("***************************** LIST int: ", i+"");
                return i;
            }
        }
        return -1;
    }
    private String getDirection(double degrees) {
        if (degrees >= 337.5 || degrees < 22.5)
            return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        return "X"; // We'll use 'X' as the default if we get a bad value
    }

    private String[] loadFile(){
        try{
                InputStream inputStream = getApplicationContext().openFileInput("savedPreferences.json");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null){
                    stringBuilder.append(line);
                }
                String[] output = new String[2];
                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                String units = jsonObject.getString("units");
                String location = jsonObject.getString("location");

                Log.d("TAGG", "toJSON: *********" + location);
                output[0] = location;
                output[1] = units;
            return output;
        }catch (FileNotFoundException fileNotFoundException){
            Toast.makeText(this, "File not found", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    protected void onPause(){
        savePreferences();
        super.onPause();
    }

    public void savePreferences(){
        try{
            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("savedPreferences.json", Context.MODE_PRIVATE);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            printWriter.print(perferencesToJSON());
            printWriter.close();
            fileOutputStream.close();
        }catch(FileNotFoundException fileNotFoundException){
            Toast.makeText(this, "File not found", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String perferencesToJSON() {

        try {
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(sw);
            jsonWriter.setIndent("  ");

            jsonWriter.beginObject();
            jsonWriter.name("units").value(units);
            jsonWriter.name("location").value(city);
            jsonWriter.endObject();

            jsonWriter.close();
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}