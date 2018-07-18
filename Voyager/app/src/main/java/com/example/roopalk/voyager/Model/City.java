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
}