package com.example.roopalk.voyager.Model;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

@ParseClassName("City")
public class City extends ParseObject
{
    ArrayList<Attraction> attractions;

    //in the Parse database, the name of the city is stored in a column called cityname
    private static final String CITY_NAME = "cityname";

    //the description of the city is stored in a column called description
    private static final String KEY_DESCRIPTION = "description";

    //the attractions of the city are stored in a column called attractions
    private static final String ATTRACTIONS = "attractions";

    //the country in which the city is
    private static final String COUNTRY = "Country";

    private static final String LATITUDE = "latitude";

    private static final String LONGITUDE = "longitude";


    //getter methods for each of the values

    public String getCityName() {
        return getString(CITY_NAME);
    }

    public String getKeyDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public String getCountry()
    {
        return getString(COUNTRY);
    }

    public String getLatitude() { return getString(LATITUDE); }

    public String getLongitude(){ return getString(LATITUDE);}

    public void setCityName(String cityName) {
        put(CITY_NAME, cityName);
    }

    public void setKeyDescription(String keyDescription) {
        put(KEY_DESCRIPTION, keyDescription);
    }

    public void setCountry(String country)
    {
        put(COUNTRY, country);
    }

    public void setLatitude(String latitude){
        put(LATITUDE, latitude);
    }

    public void setLongitude(String longitude){
        put(LONGITUDE, longitude);
    }


    public static class Query extends ParseQuery<City>
    {
        public Query()
        {
            super(City.class);
        }
        

        public Query withName()
        {
            include("cityname");
            return this;
        }

        public Query hasName(String name)
        {
            whereMatches("cityname", name);
            include("latitude");
            include("longitude");
            return this;
        }
    }
}