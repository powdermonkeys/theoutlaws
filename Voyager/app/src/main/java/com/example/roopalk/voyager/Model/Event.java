package com.example.roopalk.voyager.Model;

import com.framgia.library.calendardayview.data.IEvent;

import java.util.Calendar;

public class Event implements IEvent {

    private String evtName;
    private float evtStartTime;
    private float evtEndTime;
    private int startTimeMinutes;
    private int endTimeMinutes;

    public Event( String evtName, float mStartTime, float mEndTime, int startTimeMinutes, int endTimeMinutes) {
        this.evtName = evtName;
        this.evtStartTime = mStartTime;
        this.evtEndTime = mEndTime;
        this.startTimeMinutes = (int)(mStartTime * 60f);
        this.endTimeMinutes = (int)(mEndTime * 60f);
    }

    public String getName() {
        return evtName;
    }

    @Override
    public int getColor() {
        return 0;
    }

//    public float getStartTime() {
//        return evtStartTime;
//    }
//
//    public float getEndTime() {
//        return evtEndTime;
//    }

    public int getStartTimeInMinutes() {
        return startTimeMinutes;
    }

    public int getEndTimeInMinutes() {
        return endTimeMinutes;
    }

    @Override
    public Calendar getStartTime() {
        return null;
    }

    @Override
    public Calendar getEndTime() {
        return null;
    }
}
