package com.example.roopalk.voyager.Model;

import com.parse.FindCallback;
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
                    CITY_DEST = objects.get(0);
                }
            }
        });
    }

}
