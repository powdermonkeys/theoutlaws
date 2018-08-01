package com.example.roopalk.voyager.Model;

import com.framgia.library.calendardayview.data.IEvent;

import java.util.Calendar;

public class Event implements IEvent {

    private Calendar mStartTime;
    private Calendar mEndTime;
    private String mName;
    private int mColor;

    public Event(Calendar mStartTime, Calendar mEndTime, String mName, int mColor) {
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.mName = mName;
        this.mColor = mColor;
    }




    public Calendar getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Calendar startTime) {
        this.mStartTime = startTime;
    }

    public Calendar getEndTime() {
        return mEndTime;
    }

    public void setEndTime(Calendar endTime) {
        this.mEndTime = endTime;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }



}
