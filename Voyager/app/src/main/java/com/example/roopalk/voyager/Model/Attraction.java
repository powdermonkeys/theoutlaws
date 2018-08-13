package com.example.roopalk.voyager.Model;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Calendar;

@ParseClassName("Attraction")
public class Attraction extends ParseObject {
    //in the Parse database, the name of the attraction is stored in a column called attractionname
    private static final String ATTRACTION_NAME = "name";

    //the description of the attraction is stored in a column called attractiondescription
    private static final String ATTRACTION_DESCRIPTION = "description";

    //the location of the attraction is stored in a column called attractionlocation
    private static final String ATTRACTION_LOCATION = "location";

    //the estimated time spent at the attraction is stored in a column called estimatedtime
    private static final String ESTIMATED_TIME = "time";

    //the rating of the attraction is stored in a column called rating
    private static final String RATING = "rating";

    //the estimated price of the attraction is stored in a column called estimatedPrice
    private static final String ESTIMATED_PRICE = "price";

    //the city that each attraction is in is stored in a column called city
    private static final String CITY = "city";

    //photos of each attraction are stored in a column called photos
    private static final String PHOTOS = "photos";

    //the estimated price of the attraction is stored in a column called estimatedPrice
    private static final String STARTHOUR = "startHour";

    //the city that each attraction is in is stored in a column called city
    private static final String ENDHOUR = "endHour";

    //the estimated price of the attraction is stored in a column called estimatedPrice
    private static final String STARTMIN = "startMin";

    //the city that each attraction is in is stored in a column called city
    private static final String ENDMIN = "endMin";

    //getter methods for each of these variables
    public String getAttractionName()
    {
        return getString(ATTRACTION_NAME);
    }

    public String getAttractionDescription()
    {
        return getString(ATTRACTION_DESCRIPTION);
    }

    public ParseGeoPoint getAttractionLocation()
    {
        return getParseGeoPoint(ATTRACTION_LOCATION);
    }

    public String getEstimatedTime()
    {
        return getString(ESTIMATED_TIME);
    }

    public int getRATING()
    {
        return getInt(RATING);
    }

    public int getEstimatedPrice()
    {
        return getInt(ESTIMATED_PRICE);
    }

    public static String getSTARTHOUR() { return STARTHOUR; }

    public static String getENDHOUR() { return ENDHOUR; }

    public static String getSTARTMIN() { return STARTMIN; }

    public static String getENDMIN() { return ENDMIN; }



    public ParseObject getCity()
    {
        return getParseObject(CITY);
    }


    //setter methods for each of these variables
    public void setAttractionName(String attractionName) { put(ATTRACTION_NAME, attractionName); }

    public void setAttractionDescription(String attractionDescription) { put(ATTRACTION_DESCRIPTION, attractionDescription); }

    public void setAttractionLocation(ParseGeoPoint attractionLocation) { put(ATTRACTION_LOCATION, attractionLocation); }

    public void setEstimatedTime(String estimatedTime) { put(ESTIMATED_TIME, estimatedTime); }

    public void setEstimatedPrice(double estimatedPrice) { put(ESTIMATED_PRICE, estimatedPrice); }

    public void setRating(int rating) { put(RATING, rating); }

    public void setCity(City city) { put(CITY, city); }

    public void setStarthour(int starthour){ put(STARTHOUR, starthour);}

    public void setEndhour(int endhour){ put(ENDHOUR, endhour);}

    public void setStartmin(int startmin){ put(STARTMIN, startmin);}

    public void setEndmin(int endmin){ put(ENDMIN, endmin);}

//    public setAttraction(Calendar startHr, Calendar startMin, Calendar endHr, Calendar endMin, String mName, int mColor) {
//        this.sta = mStartTime;
//        this.mEndTime = mEndTime;
//        this.mName = mName;
////        this.mColor = mColor;
////    }



    public static class Query extends ParseQuery<Attraction>
    {
        public Query()
        {
            super(Attraction.class);
        }

        public Query withCity(String objectId)
        {
            whereMatches("city", objectId);
            orderByAscending(ATTRACTION_NAME);
            return this;
        }

        //query for the budget class; returns the attractions where the estimated price is less than or equal to that budget
        public Query withBudget(int price)
        {
            whereLessThanOrEqualTo(ESTIMATED_PRICE, price);
            return this;
        }
    }


}
