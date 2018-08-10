package com.example.roopalk.voyager.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roopalk.voyager.Adapters.HorizontalAttractionAdapter;
import com.example.roopalk.voyager.Fragments.AddingAttractionFragment;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.Event;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;
import com.example.roopalk.voyager.Weather;
import com.framgia.library.calendardayview.CalendarDayView;
import com.framgia.library.calendardayview.data.IEvent;
import com.parse.ParseException;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;


public class CalendarActivity extends AppCompatActivity implements AddingAttractionFragment.calendarListener {
    @BindView(R.id.dayView) CalendarDayView dayView;
    @BindView(R.id.calendarView) HorizontalCalendarView horizontalCalendar;
    @BindView(R.id.addIcon) FloatingActionButton add;
    @BindView(R.id.rvHorizontal) RecyclerView rvHorizontal;
    @BindView(R.id.tvweather) TextView tvweather;

    Trip trip;

    public int hours;

    public int minutes;

    public  String city;

    ArrayList<IEvent> events = new ArrayList<>();

    private ArrayList<Attraction> attractions = AddingAttractionFragment.getChosenAttractions();

    NetworkUtility networkUtility = new NetworkUtility(this);

    private HorizontalAttractionAdapter horizontalAttractionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);

        city = getIntent().getExtras().toString();
        String city = getIntent().getStringExtra("city");
//        ArrayList ctAttractions = getIntent().getStringArrayListExtra("attractions");
//
        trip = Parcels.unwrap(getIntent().getParcelableExtra("trip"));
//        attractions = Parcels.unwrap(getIntent().getParcelableExtra("attractions"));
//

        // gets the current date in simple format
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat tfHours = new SimpleDateFormat("hh");
        SimpleDateFormat tfMinutes = new SimpleDateFormat("mm");
        hours = Integer.parseInt(tfHours.format(c));
        minutes= Integer.parseInt(tfMinutes.format(c));

        //params for dayview
        dayView = findViewById(R.id.dayView);
        dayView.setLimitTime(8, 22);

        setHorizontalCalendar();

        //set up the horizontal scroll for attractions
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvHorizontal.setLayoutManager(linearLayoutManager);
        horizontalAttractionAdapter = new HorizontalAttractionAdapter(attractions);
        rvHorizontal.setAdapter(horizontalAttractionAdapter);


        events = new ArrayList<>();
        {
            int eventColor = ContextCompat.getColor(this, R.color.calendarRed);
            Calendar timeStart = Calendar.getInstance();
            timeStart.set(Calendar.HOUR_OF_DAY, hours);
            timeStart.set(Calendar.MINUTE, minutes);
            Calendar timeEnd = (Calendar) timeStart.clone();
            Event event = new Event(timeStart, timeEnd, "Event", eventColor);
            events.add(event);
        }


        dayView.setEvents(events);


        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AddingAttractionFragment addingAttractionFragment = AddingAttractionFragment.newInstance(trip);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.calendarplaceholder, addingAttractionFragment);
                ft.addToBackStack("Adding Attraction fragment");;
                ft.commit();
            }
        });



    }

    public void setTime (int startH, int startMin, int endH, int endMin, Attraction attraction) {
        int eventColor = ContextCompat.getColor(this, R.color.mutedForestGreen);
        Calendar timeStart = Calendar.getInstance();

        //setting the time for the start of the event
        timeStart.set(Calendar.HOUR_OF_DAY, startH);
        timeStart.set(Calendar.MINUTE, startMin);

        Calendar timeEnd = (Calendar) timeStart.clone();

        //setting the time for the end of the event
        timeEnd.set(Calendar.HOUR_OF_DAY, endH);
        timeEnd.set(Calendar.MINUTE, endMin);

        String url  = null;

        try {
            networkUtility.getImages(attraction);
            ArrayList<String> imageURLs = networkUtility.getImageURLs();
            url = imageURLs.get(0).toString();

            Event added = new Event(timeStart, timeEnd, attraction.getAttractionName(), url, this);

            events.add(added);

            attractions.remove(attraction);
            horizontalAttractionAdapter.notifyDataSetChanged();


            dayView.refresh();


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void moveToCalendarPage(Trip trip, Context context)
    {
        Intent calendarIntent = new Intent(context, CalendarActivity.class);
        calendarIntent.putExtra("trip", Parcels.wrap(trip));
        startActivity(calendarIntent);
    }

    public void getWeather (final String city){
        //gets the current weather
        Weather.placeIdTask asyncTask =new Weather.placeIdTask(new Weather.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature,  String weather_updatedOn, String weather_iconText, String sun_rise) {
                System.out.print(weather_city);
                tvweather.setText("It is currently " + weather_temperature.substring(0,2) + "℉ and " + weather_description.toLowerCase() + "in" +  weather_city);
            }
        });
        asyncTask.execute(city);

        System.out.print(tvweather);
    }

    public void setHorizontalCalendar(){
        //gets the bounds for the cal view
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.WEEK_OF_MONTH, -1);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.WEEK_OF_MONTH, 1);

        // sets up the horizontal date calendar view
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView) .range(startDate, endDate).datesNumberOnScreen(5).build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) { Toast.makeText(getApplicationContext(), "You selected a date!", Toast.LENGTH_SHORT).show(); }
        });


    }

}

