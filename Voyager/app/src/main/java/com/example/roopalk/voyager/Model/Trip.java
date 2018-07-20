package com.example.roopalk.voyager.Model;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;

//                        arrivalDate.setText(month+1 +"/" + dayOfMonth + "/" + year);

@ParseClassName("Trip")
public class Trip extends ParseObject {

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
                    put(DESTINATION, objects.get(0));
                }
            }
        });
    }

}
