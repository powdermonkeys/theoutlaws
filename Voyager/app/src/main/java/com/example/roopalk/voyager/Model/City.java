package com.example.roopalk.voyager.Model;


import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("City")
public class City extends ParseObject
{
    //in the Parse database, the name of the city is stored in a column called cityname
    private static final String CITY_NAME = "cityname";

    //the description of the city is stored in a column called description
    private static final String KEY_DESCRIPTION = "description";

    //getter methods for each of the values

    public String getCityName()
    {
        return getString(CITY_NAME);
    }

    public String getKeyDescription()
    {
        return getString(KEY_DESCRIPTION);
    }

    //setter methods for each of the values

    public void setCityName(String cityName)
    {
        put(CITY_NAME, cityName);
    }

    public void setKeyDescription(String keyDescription)
    {
        put(KEY_DESCRIPTION, keyDescription);
    }
}