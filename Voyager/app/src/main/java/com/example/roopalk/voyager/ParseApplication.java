package com.example.roopalk.voyager;

import android.app.Application;
import android.util.Log;

import com.example.roopalk.voyager.Model.City;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParseApplication extends Application
{
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
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("fbu-voyager-app-id")
                .clientKey("powdermonkeys")
                .server("http://fbu-voyager.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);

        //testing the ParseApp

        City firstCity = new City();
        firstCity.setCityName("Tokyo");
        firstCity.setKeyDescription("a city");
        firstCity.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null)
                {
                    Log.i("ParseApplication.java", "new city added!");
                }
                else
                {
                    Log.e("whatever",
                            "fail");
                }
            }
        });
    }
}
