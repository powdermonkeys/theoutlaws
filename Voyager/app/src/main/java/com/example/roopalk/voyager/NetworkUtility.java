package com.example.roopalk.voyager;

import android.content.Context;

import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.City;
import com.example.roopalk.voyager.Model.Photo;
import com.parse.ParseException;

import java.util.ArrayList;

public class NetworkUtility
{
    Context context;

    ArrayList<City> cities = new ArrayList<>();
    ArrayList<Attraction> attractions = new ArrayList<>();
    ArrayList<Photo> photos = new ArrayList<>();

    final City.Query cityQuery = new City.Query();
    final Attraction.Query attractionQuery = new Attraction.Query();
    final Photo.Query photoQuery = new Photo.Query();

    public NetworkUtility(Context context)
    {
        this.context = context;
    }

    public void getCityFromName(String name) throws ParseException
    {
        cityQuery.hasName(name);
        cities = (ArrayList) cityQuery.find();
    }

    public ArrayList<City> getCities()
    {
        return cities;
    }

    public void getAttractionFromCity(City city) throws ParseException
    {
        attractionQuery.withCity(city.getObjectId());
        attractions = (ArrayList) attractionQuery.find();
    }

    public ArrayList<Attraction> getAttractions()
    {
        return attractions;
    }

    public void getImagesFromAttraction(Attraction attraction) throws ParseException
    {
        photoQuery.withAttraction(attraction.getObjectId());
        photos = (ArrayList) photoQuery.find();
    }

    public ArrayList<Photo> getPhotos()
    {
        return photos;
    }
}