package com.example.roopalk.voyager;

import android.content.Context;
import android.util.Log;

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

    public City getCityDataFromName(String name) throws ParseException
    {
        cityQuery.hasName(name);
        cities = (ArrayList<City>) cityQuery.find();
        return cities.get(0);
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

    // retrieves photo from first attraction of trip if any
    public Photo getImageFromTrip(Trip trip) {
        if (trip.getDestination() == null) {
            // TODO: add log error
            return null;
        }

        // get what city associated with
        City city = null;
        try {
            city = getCityFromName(trip.getDestination());
        } catch (ParseException e) {
            Log.e("NetworkUtility", "Failed to get city for trip", e);
            return null;
        }

        // get the attractions the city is associated with
        ArrayList<Attraction> attractions = null;
        try {
            attractions = getAttractionFromCity(city);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Attraction attraction = attractions.size() > 0 ? attractions.get(0) : null;

        // get the image associated with the first attraction
        Photo photo = null;
        try {
            photo = getImagesFromAttraction(attraction).get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        TODO: Write better log statements
        return photo;
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
        tripQuery.withUser(user.getObjectId());
        trips = (ArrayList<Trip>) tripQuery.find();
        return trips;
    }

    public ArrayList<Trip> getTripsWithUser() throws ParseException {
        tripQuery.withUser();
        trips = (ArrayList<Trip>) tripQuery.find();
        return trips;
    }

}