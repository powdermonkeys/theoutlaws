package Application;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        // set applicationId, and server server based on the values in the Heroku settings.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("fbu-voyager-app-id")
                .clientKey("powdermonkeys")
                .server("http://fbu-voyager.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);

        //testing the ParseApp
    }
}
