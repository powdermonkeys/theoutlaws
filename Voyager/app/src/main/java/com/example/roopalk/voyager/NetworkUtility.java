package com.example.roopalk.voyager;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.City;
import com.example.roopalk.voyager.Model.Photo;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import static com.parse.Parse.getApplicationContext;

public class NetworkUtility
{
    private Context context = getApplicationContext();

    private static final String TAG = "NetworkUtitlity.java";
    //setting up query calls for all network fetches
    final City.Query cityQuery = new City.Query();

    final Attraction.Query attractionQuery = new Attraction.Query();

    final Photo.Query photoQuery = new Photo.Query();

    public void loadImagesFromAttraction(final Attraction attraction, final ImageView imageView)
    {
        ArrayList<Photo> images;

        photoQuery.withAttraction();
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
                       if(currPhoto.getAttraction().hasSameId(attraction))
                       {
                           String imgURL = currPhoto.getImage().getUrl();

                           GlideApp.with(context)
                                   .load(imgURL)
                                   .into(imageView);
                       }
                   }
               }
               else
               {
                   Log.e(TAG, "Failed to load into imageView");
               }
            }
        });
    }
}