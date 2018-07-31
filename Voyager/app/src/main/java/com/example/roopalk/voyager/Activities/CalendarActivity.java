package com.example.roopalk.voyager.Activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.roopalk.voyager.R;
import com.framgia.library.calendardayview.CalendarDayView;
import com.framgia.library.calendardayview.data.IEvent;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    CalendarDayView dayView;

    ArrayList<IEvent> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        dayView = (CalendarDayView) findViewById(R.id.dayView);

        dayView.setLimitTime(7,22);

        events = new ArrayList<>(); {
            int eventColor = ContextCompat.getColor(this, R.color.mutedForestGreen);
            Calendar timeStart = Calendar.getInstance();
            timeStart.set(Calendar.HOUR_OF_DAY, 11);
            timeStart.set(Calendar.MINUTE, 0);
            Calendar timeEnd = (Calendar) timeStart.clone();
            timeEnd.set(Calendar.HOUR_OF_DAY, 15);
            timeEnd.set(Calendar.MINUTE, 30);
          //  Event event = new Event("Test Event", timeStart, timeEnd, time, "Hockaido", eventColor);
          //  events.add(event);
        }

        dayView.setEvents(events);

    }
}
