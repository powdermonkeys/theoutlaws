package com.example.roopalk.voyager.Model;

import org.parceler.Parcel;

@Parcel
public class Event {

    private String evtName;
    private float evtStartTime;
    private float evtEndTime;
    private int startTimeMinutes;
    private int endTimeMinutes;

    public Event(){}

    public Event(String evtName, float mStartTime, float mEndTime) {
        this.evtName = evtName;
        this.evtStartTime = mStartTime;
        this.evtEndTime = mEndTime;
        this.startTimeMinutes = (int)(mStartTime * 60f);
        this.endTimeMinutes = (int)(mEndTime * 60f);
    }

    public String getName()
    {
        return evtName;
    }

    public float getStartTime() {
        return evtStartTime;
    }

    public float getEndTime() {
        return evtEndTime;
    }

    public int getStartTimeInMinutes() {
        return startTimeMinutes;
    }

    public int getEndTimeInMinutes() {
        return endTimeMinutes;
    }

}
