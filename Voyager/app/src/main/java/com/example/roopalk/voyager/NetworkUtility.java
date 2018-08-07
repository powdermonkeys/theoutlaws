package com.example.roopalk.voyager;

import android.content.Context;

import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.City;
import com.example.roopalk.voyager.Model.Photo;
import com.example.roopalk.voyager.Model.Trip;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.util.ArrayList;

public class NetworkUtility
{
    Context context;

    ArrayList<City> cities = new ArrayList<>();
    ArrayList<Attraction> attractions = new ArrayList<>();
    ArrayList<Photo> photos = new ArrayList<>();
    ArrayList<Trip> trips = new ArrayList<>();

    ArrayList<String> imageURLs = new ArrayList<>();

    final City.Query cityQuery = new City.Query();
    final Attraction.Query attractionQuery = new Attraction.Query();
    final Photo.Query photoQuery = new Photo.Query();
    final Trip.Query tripQuery = new Trip.Query();

    public NetworkUtility(Context context)
    {
        this.context = context;
    }

    public void getAllCities() throws ParseException
    {
        cityQuery.withName();
        cities = (ArrayList<City>) cityQuery.find();
    }

    public ArrayList<String> getCityNames() throws ParseException
    {
        getAllCities();
        ArrayList<String> names = new ArrayList<>();

        for(int i = 0; i < cities.size(); i++)
        {
            names.add(cities.get(i).getCityName());
        }

        return names;
    }

    public void getCityFromName(String name) throws ParseException
    {
        cityQuery.hasName(name);
        cities = (ArrayList<City>) cityQuery.find();
    }

    public ArrayList<City> getCities()
    {
        return cities;
    }

    public void getAttractionFromCity(City city) throws ParseException
    {
        attractionQuery.withCity(city.getObjectId());
        attractions = (ArrayList<Attraction>) attractionQuery.find();
    }

    public ArrayList<Attraction> getAttractions()
    {
        return attractions;
    }

    public void getImagesFromAttraction(Attraction attraction) throws ParseException
    {
        photoQuery.withAttraction(attraction.getObjectId());
        photos = (ArrayList<Photo>) photoQuery.find();
    }

    public ArrayList<Photo> getPhotos()
    {
        return photos;
    }


    public void getImages(Attraction attraction) throws ParseException
    {
        getImagesFromAttraction(attraction);
        photos = getPhotos();

        for(int i = 0; i < photos.size(); i++)
        {
            Photo p = photos.get(i);
            ParseFile images = p.getImage();
            String url = images.getUrl();

            imageURLs.add(url);
        }
    }
    public ArrayList<String> getImageURLs()
    {
        return imageURLs;
    }
}