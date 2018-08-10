package com.example.roopalk.voyager.Model;

import com.framgia.library.calendardayview.data.IEvent;

import java.util.Calendar;

public class Event implements IEvent {

    private Calendar mStartTime;
    private Calendar mEndTime;
    private String mName;
    private int mColor;
    private String imageURL;

    public Event(Calendar mStartTime, Calendar mEndTime, String mName, int mColor) {
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.mName = mName;
        this.mColor = mColor;
    }

    public Event(Calendar mStartTime, Calendar mEndTime, String mName, String imageURL) {
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.mName = mName;
        this.imageURL = imageURL;
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

    public String getImage() {
        return imageURL;
    }

    public void setColor(int color) {
        this.mColor = color;
    }

    //get all the information of an event in integers

    public int getStartIntHour() { return mStartTime.HOUR_OF_DAY; }

    public int getStartIntMin() { return mStartTime.MINUTE; }

    public int getEndIntHour() { return mEndTime.HOUR_OF_DAY; }

    public int getEndIntMin() { return mEndTime.MINUTE; }
}
