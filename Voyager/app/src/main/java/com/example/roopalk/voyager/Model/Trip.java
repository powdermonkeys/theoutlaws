package com.example.roopalk.voyager.Model;

import com.parse.FindCallback;
<<<<<<< HEAD
import com.parse.ParseException;

import java.util.List;

public class Trip
{
    public static int NUM_GUESTS;
    public static String CHECK_IN;
    public static String CHECK_OUT;
    public static City CITY_DEST;

    public Trip(int num_guests, String check_in, String check_out, String destination, String country)
    {
        NUM_GUESTS = num_guests;
        CHECK_IN = check_in;
        CHECK_OUT = check_out;
        getDestination(destination, country);
    }

    private void getDestination(String destination, String country)
=======
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("Trip")
public class Trip extends ParseObject
{

    //in the Parse database, the number of guests is stored in a column called guests
    private static final String NUM_GUESTS = "guests";

    //the checkin date of the trip stored in a column called checkin
    private static final String CHECKIN = "checkin";

    //the checkout date of the trip is stored in a column called checkout
    private static final String CHECKOUT = "checkout";

    //the destination of the trip is stored in a column called destination
    private static final String DESTINATION = "destination";

    private static String city = "";
    private static String country = "";

    public Trip(String ct, String tree, String c, String co, int ng)
    {
        city = ct;
        country = tree;
        setDestination(city, country);
        setCheckin(c);
        setCheckout(co);
        setNumGuests(ng);
    }

    //getter methods for each of the values

    public int getNumGuests()
    {
        return getInt(NUM_GUESTS);
    }

    public String getCheckin() {
        return getString(CHECKIN);
    }

    public String getCheckout()
    {
        return getString(CHECKOUT);
    }

    public String getDestination()
    {
        return getString(DESTINATION);
    }

    public void setNumGuests(int numguests)
    {
        put(NUM_GUESTS, numguests);
    }

    public void setCheckin(String checkin) {
        put(CHECKIN, checkin);
    }

    public void setCheckout(String checkout)
    {
        put(CHECKOUT, checkout);
    }

    private void setDestination(String destination, String country)
>>>>>>> 7afcfaa224544746a42f4b6417e54d2c1e1b4caf
    {
        final City.Query cityQuery = new City.Query();

        cityQuery.withName(destination);
        cityQuery.withCountry(country);

        cityQuery.findInBackground(new FindCallback<City>()
        {
            @Override
            public void done(List<City> objects, ParseException e)
            {
                if(e == null)
                {
                    //get the first object that is found that is name with this city
<<<<<<< HEAD
                    CITY_DEST = objects.get(0);
=======
                    put(DESTINATION, objects.get(0));
>>>>>>> 7afcfaa224544746a42f4b6417e54d2c1e1b4caf
                }
            }
        });
    }

}
