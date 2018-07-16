package Application;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import Model.Attraction;
import Model.City;
import Model.Photo;

public class ParseApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

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

        //testing the ParseApp

        City firstCity = new City(), secondCity = new City(), thirdCity = new City();
        firstCity.setCityName("Tokyo");
        secondCity.setCityName("Seattle");
        thirdCity.setCityName("Cape Town");

        firstCity.saveInBackground();
        secondCity.saveInBackground();
        thirdCity.saveInBackground();
    }
}
