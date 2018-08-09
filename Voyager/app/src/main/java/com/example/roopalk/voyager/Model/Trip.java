package com.example.roopalk.voyager.Model;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Trip")
public class Trip extends ParseObject {

    private int mThumbnailDrawable;

    //in the Parse database, the number of guests is stored in a column called guests
    private static final String NUM_GUESTS = "guests";

    //the checkin date of the trip stored in a column called checkin
    private static final String CHECKIN = "checkin";

    //the checkout date of the trip is stored in a column called checkout
    private static final String CHECKOUT = "checkout";

    //the destination of the trip is stored in a column called destination
    private static final String DESTINATION = "destination";

    //the destination of the trip is stored in a column called budget
    private static final String BUDGET = "budget";

    //the destination of the trip is stored in a column called length
    private static final String LENGTH = "length";

    //list of attractions stored in a column called attrations
    private static  final String ATTRACTIONS = "attractions";

    private static final List<Attraction> attractions = new ArrayList<Attraction> ();


    public Trip() {}

    //getter methods for each of the values

    public int getThumbnailDrawable() { return mThumbnailDrawable; }

    public int getNumGuests() { return getInt(NUM_GUESTS); }

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

    public int getBudget() { return getInt(BUDGET); }

    public int getLength() { return getInt(LENGTH); }

    public List<Attraction> getAttractions(Trip trip) {
        return getList("attractions");
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

    public void setDestination(final String destination) { put(DESTINATION, destination); }

    public void setBudget(int budget) { put(BUDGET, budget);}

    public void setLength (int length) { put(LENGTH, length); }

    public void setAttractions (Attraction attraction){
       attractions.add(attraction);
       put(ATTRACTIONS, attractions);

    }

    public void setTripInfo(String destination, String checkin, String checkout, int numguests, int budget, int length) throws ParseException {
        setDestination(destination);
        setCheckout(checkout);
        setCheckin(checkin);
        setNumGuests(numguests);
        setBudget(budget);
        setLength(length);
    }


    // Returns a list of trips, random trips to show cardviews
    public static List<Trip> getTrips() {
        List<Trip> trips = new ArrayList<>();
        trips.add(ParseObject.create(Trip.class));
        trips.add(ParseObject.create(Trip.class));
        trips.add(ParseObject.create(Trip.class));
        trips.add(ParseObject.create(Trip.class));
        trips.add(ParseObject.create(Trip.class));
        return trips;
    }

    public static class Query extends ParseQuery<Trip>
    {
        public Query()
        {
            super(Trip.class);
        }

        public Query withDate(String date)
        {
            whereMatches("checkin", date);
            return this;
        }
        public Query withCheckinAndCheckout(String checkin, String checkout)
        {
            whereMatches(CHECKIN, checkin);
            whereMatches(CHECKOUT, checkout);
            return this;
        }

        public Query withCheckin(String checkin)
        {
            whereMatches(CHECKIN, checkin);
            return this;
        }


    }
}
