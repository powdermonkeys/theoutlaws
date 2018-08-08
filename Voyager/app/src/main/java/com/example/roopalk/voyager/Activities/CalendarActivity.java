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

import com.example.roopalk.voyager.Adapters.HorizontalAttractionAdapter;
import com.example.roopalk.voyager.Fragments.AddingAttractionFragment;
import com.example.roopalk.voyager.Fragments.AttractionDetailsFragment;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.Event;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.R;
import com.framgia.library.calendardayview.CalendarDayView;
import com.framgia.library.calendardayview.data.IEvent;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;


public class CalendarActivity extends AppCompatActivity implements AddingAttractionFragment.calendarListener {
    @BindView(R.id.dayView) CalendarDayView dayView;
    @BindView(R.id.calendarView) HorizontalCalendarView horizontalCalendar;
    @BindView(R.id.addIcon) FloatingActionButton add;
    @BindView(R.id.rvHorizontal) RecyclerView rvHorizontal;


    Trip trip;

    ArrayList<IEvent> events = new ArrayList<>();

    private ArrayList<Attraction> attractions = AttractionDetailsFragment.getChosenAttractions();

    private HorizontalAttractionAdapter horizontalAttractionAdapter;

    public int hours;

    public int minutes;

    public String city;
//    String currDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ButterKnife.bind(this);

        city = getIntent().getExtras().toString();

        // gets the current date in simple format
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMMM, dd yyyy");
        SimpleDateFormat tfHours = new SimpleDateFormat("hh");
        SimpleDateFormat tfMinutes = new SimpleDateFormat("mm");
        hours = Integer.parseInt(tfHours.format(c));
        minutes= Integer.parseInt(tfMinutes.format(c));

        trip = Parcels.unwrap(getIntent().getParcelableExtra("trip"));

        dayView = findViewById(R.id.dayView);
        dayView.setLimitTime(8, 22);

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();



            //set up the horizontal scroll for attractions
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            rvHorizontal.setLayoutManager(linearLayoutManager);
            horizontalAttractionAdapter = new HorizontalAttractionAdapter(attractions);
            rvHorizontal.setAdapter(horizontalAttractionAdapter);

        events = new ArrayList<>();
        {
            int eventColor = ContextCompat.getColor(this, R.color.iconBackgroundBlue);
            Calendar timeStart = Calendar.getInstance();
            timeStart.set(Calendar.HOUR_OF_DAY, 11);
            timeStart.set(Calendar.MINUTE, minutes);
            Calendar timeEnd = (Calendar) timeStart.clone();
            timeEnd.set(Calendar.HOUR_OF_DAY, 14);
            timeEnd.set(Calendar.MINUTE, minutes + 2);
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
                ft.replace(R.id.rlCalendar, addingAttractionFragment);
                ft.commit();
            }
        });
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



    @Override
    public void moveToCalendarPage(Trip trip, Context context)
    {
        Intent calendarIntent = new Intent(context, CalendarActivity.class);
        calendarIntent.putExtra("trip", Parcels.wrap(trip));
        startActivity(calendarIntent);
    }


}