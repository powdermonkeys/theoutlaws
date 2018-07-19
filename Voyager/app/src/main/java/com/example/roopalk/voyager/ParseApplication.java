package com.example.roopalk.voyager;

import android.app.Application;

import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.City;
import com.example.roopalk.voyager.Model.Photo;
import com.facebook.stetho.Stetho;
import com.parse.Parse;
import com.parse.ParseObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParseApplication extends Application
{
    private static final String TAG = "ParseApplication.java";
    private static final String imagePath = "/storage/emulated/0/Download/Seattle_Space_Needle.jpg";
    @Override
    public void onCreate()
    {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        // set applicationId, and server server based on the values in the Heroku settings.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax

        ParseObject.registerSubclass(City.class);
        ParseObject.registerSubclass(Attraction.class);
        ParseObject.registerSubclass(Photo.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("fbu-voyager-app-id")
                .clientKey("powdermonkeys")
                .server("http://fbu-voyager.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }
}
