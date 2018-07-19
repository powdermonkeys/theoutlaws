package com.example.roopalk.voyager.Model;


import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("City")
public class City extends ParseObject {
    ArrayList<Attraction> attractions;

    //in the Parse database, the name of the city is stored in a column called cityname
    private static final String CITY_NAME = "cityname";

    //the description of the city is stored in a column called description
    private static final String KEY_DESCRIPTION = "description";

    //the attractions of the city are stored in a column called attractions
    private static final String ATTRACTIONS = "attractions";

    //the number of guests in the trip
    private static final String NUM_GUESTS = "guests";

    //the total budget of the trip
    private static final int BUDGET = 0;

    //the check in  date for the trip
    private static final int CHECK_IN = 0;
    //the check out date for the trip
    private static final int CHECK_OUT = 0;




    //getter methods for each of the values

    public String getCityName() {
        return getString(CITY_NAME);
    }

    public String getKeyDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public List<Attraction> getAttractions() {
        return getList(ATTRACTIONS);
    }

    public static String getNumGuests() { return NUM_GUESTS; }

    public static int getBudget() { return BUDGET; }

    public static int getCheckIn() { return CHECK_IN; }

    public static int getCheckOut() { return CHECK_OUT; }


//setter methods for each of the values

    public void setCityName(String cityName) {
        put(CITY_NAME, cityName);
    }

    public void setKeyDescription(String keyDescription) {
        put(KEY_DESCRIPTION, keyDescription);
    }

    public void addAttraction(Attraction attraction)
    {
        List<Attraction> attractions = getAttractions();
        attractions.add(attraction);
        put(ATTRACTIONS, attractions);
    }


    public void setNumGuests(int numGuests) {
        put(NUM_GUESTS, numGuests);
    }

    public void setBudget(int budget) {
        put(String.valueOf(BUDGET), budget);
    }

    public void setCheckIn(int checkIn) {
        put(String.valueOf(CHECK_IN), checkIn);
    }
    public void setCheckOut(int checkOut) {
        put(String.valueOf(CHECK_OUT),  checkOut);
    }


}