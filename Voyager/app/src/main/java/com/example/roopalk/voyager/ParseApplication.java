package com.example.roopalk.voyager;

import android.app.Application;

import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.City;
import com.parse.Parse;
import com.parse.ParseObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParseApplication extends Application
{
    private static final String TAG = "ParseApplication.java";
    @Override
    public void onCreate()
    {
        super.onCreate();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        // set applicationId, and server server based on the values in the Heroku settings.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax

        ParseObject.registerSubclass(City.class);
        ParseObject.registerSubclass(Attraction.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("fbu-voyager-app-id")
                .clientKey("powdermonkeys")
                .server("http://fbu-voyager.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);

        //testing the ParseApp

//        City firstCity = new City();
//        firstCity.setCityName("Tokyo");
//        firstCity.setKeyDescription("a city");
//        firstCity.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e == null)
//                {
//                    Log.i(TAG, "new city added!");
//                }
//                else
//                {
//                    Log.e(TAG,"failed to add new city");
//                }
//            }
//        });
//
//        City secondCity = new City();
//        secondCity.setCityName("Seattle");
//        secondCity.setKeyDescription("Seattle is a city");
//        secondCity.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e)
//            {
//                if(e == null)
//                {
//                    Log.i(TAG, "new city added!");
//                }
//                else
//                {
//                    Log.e(TAG,"failed to add new city");
//                }
//            }
//        });
//
//        Attraction spaceNeedle = new Attraction();
//        spaceNeedle.setAttractionName("Space Needle");
//        spaceNeedle.setAttractionDescription("WOW");
//        spaceNeedle.setCity(secondCity);
//        spaceNeedle.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e == null)
//                {
//                    Log.i(TAG, "new attraction added!");
//                }
//                else
//                {
//                    Log.e(TAG,"failed to add new attraction");
//                }
//            }
//        });
    }
}
