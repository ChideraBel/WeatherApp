package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class DailyWeatherActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    DailyWeatherAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_weather);

        Intent intent = getIntent();
        if (intent.hasExtra(Weather.class.getName())) {

            Weather w = (Weather) intent.getSerializableExtra(Weather.class.getName());
            if (w == null)
                return;

            Log.d("*************", w.getDays().toString());

            setTitle(w.getAddress() + "15 days");
            adapter = new DailyWeatherAdapter(w.getDays(), DailyWeatherActivity.this);
            recyclerView = findViewById(R.id.dailyRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}