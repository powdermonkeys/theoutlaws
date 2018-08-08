package com.example.roopalk.voyager.Model;

import java.util.Calendar;

public class Time {

    private Calendar mStartTime;


    public Time(Calendar mStartTime) {
        this.mStartTime = mStartTime;
    }

    public Calendar getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Calendar startTime) {
        this.mStartTime = startTime;
    }

}
