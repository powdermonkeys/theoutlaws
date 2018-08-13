package com.example.roopalk.voyager.Model;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Trip")
public class Trip extends ParseObject {

    private List<Attraction> tripAttractions = new ArrayList<Attraction>();

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

    //the attractions of the trip is stored in a column called tripAttractions
    private static final String TRIPATTRACTIONS = "tripAttractions";

    //the user that created this trip is stored in a column called user
    private static final String USER = "user";

    //the name of the trip is stored in a column called name
    private static final String NAME = "name";

    public Trip() {}

    //getter methods for each of the values

    public int getNumGuests() { return getInt(NUM_GUESTS); }

    public String getCheckin() {return getString(CHECKIN); }

    public String getCheckout() { return getString(CHECKOUT); }

    public String getDestination() { return getString(DESTINATION); }

    public List<Attraction> getTripAttractions() { return getList(TRIPATTRACTIONS); }

    public void setTripattractions() {
        put(TRIPATTRACTIONS, tripAttractions);
    }

    public void addAttractiontoTrip(final Attraction attraction){
        tripAttractions.add(attraction);

    }
    public int getBudget() { return getInt(BUDGET); }

    public int getLength() { return getInt(LENGTH); }

    public ParseUser getUser()
    {
        return getParseUser(USER);
    }

    public String getName() { return getString(NAME); }

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

    public void setUser(ParseUser parseUser) { put(USER, parseUser); }

    public void setTripInfo(String destination, String checkin, String checkout, int numguests, int budget, int length, ParseUser parseUser) throws ParseException {
        setDestination(destination);
        setCheckout(checkout);
        setCheckin(checkin);
        setNumGuests(numguests);
        setBudget(budget);
        setLength(length);
        setUser(parseUser);
    }


    public static class Query extends ParseQuery<Trip>
    {
        public Query()
        {
            super(Trip.class);
        }

        // Query that gets trips that are featured therefore have a name in the 'name' column (e.g. Spend 3 days in...)
        public Query withName() {
            whereExists(NAME);
            return this;
        }

        public Query withCheckin(String date)
        {
            whereMatches(CHECKIN, date);
            return this;
        }
        public Query withCheckinAndCheckout(String checkin, String checkout)
        {
            whereMatches(CHECKIN, checkin);
            whereMatches(CHECKOUT, checkout);
            return this;
        }

        public Query withUser(String userObjectId)
        {
            whereMatches(USER, userObjectId);
            return this;
        }

        public Query withUser()
        {
            include("user");
            return this;
        }
    }
}

