package com.example.roopalk.voyager.Activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.roopalk.voyager.Adapters.HorizontalAttractionAdapter;
import com.example.roopalk.voyager.Fragments.AttractionDetailsFragment;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.Event;
import com.example.roopalk.voyager.R;
import com.framgia.library.calendardayview.CalendarDayView;
import com.framgia.library.calendardayview.data.IEvent;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CalendarActivity extends AppCompatActivity {
    @BindView(R.id.dayView)
    CalendarDayView dayView;

    ArrayList<IEvent> events = new ArrayList<>();

    private ArrayList<Attraction> attractions = AttractionDetailsFragment.getChosenAttractions();

    private HorizontalAttractionAdapter horizontalAttractionAdapter;

    @BindView(R.id.rvHorizontal)
    RecyclerView rvHorizontal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ButterKnife.bind(this);
        dayView = findViewById(R.id.dayView);
        dayView.setLimitTime(8, 22);

        events = new ArrayList<>();
        {

            //set up the horizontal scroll for attractions
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            rvHorizontal.setLayoutManager(linearLayoutManager);
            horizontalAttractionAdapter = new HorizontalAttractionAdapter(attractions);
            rvHorizontal.setAdapter(horizontalAttractionAdapter);

            dayView.setLimitTime(8, 22);

            events = new ArrayList<>();
            {
                int eventColor = ContextCompat.getColor(this, R.color.eventColor);
                Calendar timeStart = Calendar.getInstance();
                timeStart.set(Calendar.HOUR_OF_DAY, 8);
                timeStart.set(Calendar.MINUTE, 0);
                Calendar timeEnd = (Calendar) timeStart.clone();
                timeEnd.set(Calendar.HOUR_OF_DAY, 9);
                timeEnd.set(Calendar.MINUTE, 30);
                Event event = new Event(timeStart, timeEnd, "Event", eventColor);
                events.add(event);
            }
            {
                int eventColor = ContextCompat.getColor(this, R.color.millenialPink);
                Calendar timeStart = Calendar.getInstance();
                timeStart.set(Calendar.HOUR_OF_DAY, 12);
                timeStart.set(Calendar.MINUTE, 0);
                Calendar timeEnd = (Calendar) timeStart.clone();
                timeEnd.set(Calendar.HOUR_OF_DAY, 14);
                timeEnd.set(Calendar.MINUTE, 30);
                Event event = new Event(timeStart, timeEnd, "Another event", eventColor);
                events.add(event);
            }

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
