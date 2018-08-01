package com.example.roopalk.voyager.Activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.roopalk.voyager.Model.Event;
import com.example.roopalk.voyager.R;
import com.framgia.library.calendardayview.CalendarDayView;
import com.framgia.library.calendardayview.data.IEvent;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CalendarActivity extends AppCompatActivity {

    @BindView(R.id.dayView) CalendarDayView dayView ;
    ArrayList<IEvent> events = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ButterKnife.bind(this);
        dayView =  findViewById(R.id.dayView);
        dayView.setLimitTime(8, 22);

        events = new ArrayList<>();{
            int eventColor = ContextCompat.getColor(this, R.color.eventColor);
            Calendar timeStart = Calendar.getInstance();
            timeStart.set(Calendar.HOUR_OF_DAY, 7);
            timeStart.set(Calendar.MINUTE, 0);
            Calendar timeEnd = (Calendar) timeStart.clone();
            timeEnd.set(Calendar.HOUR_OF_DAY, 9);
            timeEnd.set(Calendar.MINUTE, 30);
            Event event = new Event( timeStart, timeEnd, "Event",  eventColor);
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
            Event event = new Event( timeStart, timeEnd, "Another event", eventColor);
            events.add(event);
        }

        dayView.setEvents(events);
    }
}