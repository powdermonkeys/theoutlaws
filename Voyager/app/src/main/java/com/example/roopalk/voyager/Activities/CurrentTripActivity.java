package com.example.roopalk.voyager.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.roopalk.voyager.R;
import com.example.roopalk.voyager.Weather;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrentTripActivity extends AppCompatActivity {

    private static final String OPEN_WEATHER_MAP_URL = "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric";
    private static final String OPEN_WEATHER_MAP_API = "bfbc87d937264d7683636f41f52b854d";

    @BindView(R.id.btnBudget) Button budget;
    @BindView(R.id.btnCalendar) Button calendar;
    @BindView(R.id.tvGreeting) TextView greeting;
    @BindView(R.id.tvDestination) TextView destination;
    @BindView(R.id.tvTemp) TextView temp;
    @BindView(R.id.tvWeather) TextView weather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_trip);
        ButterKnife.bind(this);

        Weather.placeIdTask asyncTask =new Weather.placeIdTask(new Weather.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature,  String weather_updatedOn, String weather_iconText, String sun_rise) {

                destination.setText(weather_city);
                weather.setText(weather_description);

                temp.setText(weather_temperature);
            }
        });
        asyncTask.execute("47.6062", "-122.3321"); //  asyncTask.execute("Latitude", "Longitude")
    }
}