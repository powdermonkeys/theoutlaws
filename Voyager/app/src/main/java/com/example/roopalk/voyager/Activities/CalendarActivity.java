package com.example.roopalk.voyager.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roopalk.voyager.Adapters.HorizontalAttractionAdapter;
import com.example.roopalk.voyager.Fragments.AttractionDetailsFragment;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.Event;
import com.example.roopalk.voyager.R;
import com.example.roopalk.voyager.Weather;
import com.framgia.library.calendardayview.CalendarDayView;
import com.framgia.library.calendardayview.data.IEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CalendarActivity extends AppCompatActivity {
    @BindView(R.id.dayView) CalendarDayView dayView;
    @BindView(R.id.display_current_date) TextView currDate;
    @BindView(R.id.addIcon) FloatingActionButton add;
    @BindView(R.id.tvWeather) TextView weather;
    @BindView(R.id.previous_day) ImageView previousDay;
    @BindView(R.id.next_day) ImageView nextDay;


    ArrayList<IEvent> events = new ArrayList<>();

    private ArrayList<Attraction> attractions = AttractionDetailsFragment.getChosenAttractions();

    private HorizontalAttractionAdapter horizontalAttractionAdapter;

    public SimpleDateFormat currTime;
    public int hours;
    public int minutes;
    public Date c;

    @BindView(R.id.rvHorizontal)
    RecyclerView rvHorizontal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ButterKnife.bind(this);


        // gets the current date in simple format
        c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMMM, dd yyyy");
        SimpleDateFormat tfHours = new SimpleDateFormat("hh");
        SimpleDateFormat tfMinutes = new SimpleDateFormat("mm");
        currDate.setText(df.format(c));
        hours = Integer.parseInt(tfHours.format(c));
        minutes= Integer.parseInt(tfMinutes.format(c));



        dayView = findViewById(R.id.dayView);
        dayView.setLimitTime(8, 22);

        //gets the current weather
        Weather.placeIdTask asyncTask =new Weather.placeIdTask(new Weather.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature,  String weather_updatedOn, String weather_iconText, String sun_rise) {
                weather.setText("It is currently " + weather_temperature.substring(0,2) + "℉ and " + weather_description.toLowerCase());

            }
        });
        asyncTask.execute("47.6062", "-122.3321"); //  asyncTask.execute("Latitude", "Longitude")


        events = new ArrayList<>();
        {

            //set up the horizontal scroll for attractions
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            rvHorizontal.setLayoutManager(linearLayoutManager);
            horizontalAttractionAdapter = new HorizontalAttractionAdapter(attractions);
            rvHorizontal.setAdapter(horizontalAttractionAdapter);

            dayView.setLimitTime(8, 22);
            dayView.setEvents(events);
        }

    }

    public void setTime ( int startH, int startMin, int endH, int endMin){
        int eventColor = ContextCompat.getColor(this, R.color.mutedForestGreen);
        Calendar timeStart = Calendar.getInstance();

        //setting the time for the start of the event

        timeStart.set(Calendar.HOUR_OF_DAY, startH);
        timeStart.set(Calendar.MINUTE, startMin);

        Calendar timeEnd = (Calendar) timeStart.clone();

        //setting the time for the end of the event
        timeEnd.set(Calendar.HOUR_OF_DAY, endH);
        timeEnd.set(Calendar.MINUTE, endMin);

        Event added = new Event(timeStart, timeEnd, "attraction added", eventColor);
        events.add(added);

        dayView.refresh();
    }
}