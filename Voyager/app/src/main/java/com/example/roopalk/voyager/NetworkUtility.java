package com.example.roopalk.voyager;

import android.content.Context;

import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.City;
import com.example.roopalk.voyager.Model.Photo;
import com.example.roopalk.voyager.Model.Trip;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

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

    public City getCityFromName(String name) throws ParseException
    {
        cityQuery.hasName(name);
        cities = (ArrayList<City>) cityQuery.find();
        return cities.size() > 0? cities.get(0) : null;
    }

    public ArrayList<City> getCities()
    {
        return cities;
    }

    public ArrayList<Attraction> getAttractionFromCity(City city) throws ParseException
    {
        attractionQuery.withCity(city.getObjectId());
        attractions = (ArrayList<Attraction>) attractionQuery.find();
        return attractions;
    }

    public ArrayList<Attraction> getAttractions()
    {
        return attractions;
    }

    public ArrayList<Attraction> getAttractionsByBudgetAndCity(int budget, City city) throws ParseException
    {
        attractionQuery.withBudget(budget);
        attractionQuery.withCity(city.getObjectId());
        attractions = (ArrayList<Attraction>) attractionQuery.find();
        return attractions;
    }

    public ArrayList<Photo> getImagesFromAttraction(Attraction attraction) throws ParseException
    {
        photoQuery.withAttraction(attraction.getObjectId());
        photos = (ArrayList<Photo>) photoQuery.find();
        return photos;
    }

    public ArrayList<Photo> getPhotos()
    {
        return photos;
    }

    // populates the imageURLs with images as strings given an Attraction
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

    // Returns a list of trips, random trips to show cardviews
    public ArrayList<Trip> getFeaturedTrips() throws ParseException {
        tripQuery.withName();
        trips = (ArrayList<Trip>) tripQuery.find();
        return trips;
    }


    public ArrayList<Trip> getTripsByDate(String date) throws ParseException
    {
        tripQuery.withCheckin(date);
        trips = (ArrayList<Trip>) tripQuery.find();
        return trips;
    }

    public ArrayList<Trip> getTripsByUser(ParseUser user) throws ParseException
    {
        tripQuery.withUser(user.getUsername());
        trips = (ArrayList<Trip>) tripQuery.find();
        return trips;
    }

    public ArrayList<Trip> getTripsWithUser() throws ParseException {
        tripQuery.withUser();
        trips = (ArrayList<Trip>) tripQuery.find();
        return trips;
    }

}