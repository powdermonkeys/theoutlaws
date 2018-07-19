package com.example.roopalk.voyager;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.City;
import com.example.roopalk.voyager.Model.Photo;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class NetworkUtility
{
    private Context context;

    private static final String TAG = "NetworkUtility.java";
    //setting up query calls for all network fetches
    final City.Query cityQuery = new City.Query();

    final Attraction.Query attractionQuery = new Attraction.Query();

    final Photo.Query photoQuery = new Photo.Query();

    public NetworkUtility(Context context)
    {
        this.context = context;
    }

    /*
        CALL THESE METHODS IN THE ADAPTER WHEN POPULATING THE VIEWS SO THAT THE RIGHT CITIES, IMAGES, AND ATTRACTIONS SHOW UP
     */

    //When this method is called, a list of images that contain images from the entered attraction will be returned

    public ArrayList<String> getImagesOfAttraction(final Attraction attraction)
    {
        final ArrayList<String> imageURLs = new ArrayList<>();

        String attractionID = attraction.getObjectId();

        photoQuery.withAttraction(attractionID);
        photoQuery.findInBackground(new FindCallback<Photo>()
        {
            @Override
            public void done(List<Photo> objects, ParseException e)
            {
               if(e == null)
               {
                   for(int i = 0; i < objects.size(); i++)
                   {
                       Photo currPhoto = objects.get(i);
                       String imgURL = currPhoto.getImage().getUrl();
                       imageURLs.add(imgURL);
                   }
               }
               else
               {
                   Toast.makeText(context, "Failed to find any images of that attraction", Toast.LENGTH_LONG).show();
                   Log.e(TAG, "Failed to find any images of that attraction");
               }
            }
        });

        return imageURLs;
    }

    //When this method is called, a list of attractions that are in a specific city (entered) will be returned
    public ArrayList<Attraction> getAttractionsOfCity(final City city)
    {
        final ArrayList<Attraction> attractions = new ArrayList<>();
        String cityID = city.getObjectId();

        attractionQuery.withCity(cityID);
        attractionQuery.findInBackground(new FindCallback<Attraction>()
        {
            @Override
            public void done(List<Attraction> objects, ParseException e)
            {
                if(e == null)
                {
                    for(int j = 0; j < objects.size(); j++)
                    {
                        attractions.add(objects.get(j));
                    }
                }
                else
                {
                    Toast.makeText(context, "Failed to find any attractions in that city", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Failed to find any attractions in that city");
                }
            }
        });

        return attractions;
    }

    //When this method is called, a list of cities that match (or contain the name) that is passed in is returned=
    public ArrayList<City> getCityFromName(final String cityName)
    {
        final ArrayList<City> cities = new ArrayList<>();

        cityQuery.withName(cityName);
        cityQuery.findInBackground(new FindCallback<City>()
        {
            @Override
            public void done(List<City> objects, ParseException e)
            {
                if(e == null)
                {
                    for(int k = 0; k < objects.size(); k++)
                    {
                        cities.add(objects.get(k));
                    }
                }
                else
                {
                    Toast.makeText(context, "Failed to find any cities by that name", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Failed to find any cities by that name");
                }
            }
        });

        return cities;
    }
}