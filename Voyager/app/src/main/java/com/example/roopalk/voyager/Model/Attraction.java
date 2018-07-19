package com.example.roopalk.voyager.Model;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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

    public String getEstimatedPrice()
    {
        return getString(ESTIMATED_PRICE);
    }

    public ParseObject getCity()
    {
        return getParseObject(CITY);
    }

    //setter methods for each of these variables
    public void setAttractionName(String attractionName)
    {
        put(ATTRACTION_NAME, attractionName);
    }

    public void setAttractionDescription(String attractionDescription)
    {
        put(ATTRACTION_DESCRIPTION, attractionDescription);
    }

    public void setAttractionLocation(ParseGeoPoint attractionLocation)
    {
        put(ATTRACTION_LOCATION, attractionLocation);
    }

    public void setEstimatedTime(String estimatedTime)
    {
        put(ESTIMATED_TIME, estimatedTime);
    }

    public void setEstimatedPrice(double estimatedPrice)
    {
        put(ESTIMATED_PRICE, estimatedPrice);
    }

    public void setRating(int rating)
    {
        put(RATING, rating);
    }

    public void setCity(City city)
    {
        put(CITY, city);
    }
    public static class Query extends ParseQuery<Attraction>
    {
        public Query()
        {
            super(Attraction.class);
        }

        public Query withPhotos()
        {
            include(PHOTOS);
            return this;
        }

    }
}
