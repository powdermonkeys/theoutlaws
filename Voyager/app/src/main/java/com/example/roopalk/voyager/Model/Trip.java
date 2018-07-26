package com.example.roopalk.voyager.Model;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

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

    public Trip() {}

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

    public void setDestination(final String destination) throws ParseException
    {
        put(DESTINATION, destination);

    }

    public void setTripInfo(String destination, String checkin, String checkout, int numguests) throws ParseException {
        setDestination(destination);
        setCheckout(checkout);
        setCheckin(checkin);
        setNumGuests(numguests);
    }

}
