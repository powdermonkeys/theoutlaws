package com.example.roopalk.voyager.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.roopalk.voyager.Adapters.FragmentAdapter;
import com.example.roopalk.voyager.Fragments.AddingAttractionFragment;
import com.example.roopalk.voyager.Fragments.AddingEventFragment;
import com.example.roopalk.voyager.Fragments.AttractionDetailsFragment;
import com.example.roopalk.voyager.Fragments.BuildFragment;
import com.example.roopalk.voyager.Fragments.FeaturedTripsFragment;
import com.example.roopalk.voyager.Fragments.ProfileFragment;
import com.example.roopalk.voyager.Fragments.onFragmentInteractionListener;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.BudgetBar;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class  MainActivity extends AppCompatActivity implements onFragmentInteractionListener, ProfileFragment.SignoutListener
{
    private final String TAG = "MainActivity";

    AddingAttractionFragment addingAttractionFragment;
    AttractionDetailsFragment attractionDetailsFragment;
    FeaturedTripsFragment featuredTripsFragment;
    AddingEventFragment addingEventFragment;

    NetworkUtility networkUtility = new NetworkUtility(this);

    String currDateSTF;
    ArrayList<Trip> trips;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ParseUser.getCurrentUser() == null)
        {
            try
            {
                ParseUser.logIn("roopal", "password");
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        //gets today's date
        Date currentDate = new Date();
        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        currDateSTF = sdf.format(currentDate).toString();

        //compares todays date with trips in parse
        try {
            trips = networkUtility.getTripsByDateAndUser(currDateSTF, ParseUser.getCurrentUser());
            if (trips.size() > 0){
                final Trip trip = trips.get(0);
             //   final List<Attraction> attractions = trips.get(0).getAttractions(trips.get(0));
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("We noticed that you are currently on a trip. Would you like to be redirected to your calendar?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                                intent.putExtra("trip", Parcels.wrap(trip));
                                intent.putExtra("tripAttractions", Parcels.wrap(trip.getTripAttractions()));
                                startActivity(intent);
                                finish();

                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeOptionsMenu();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void moveToFeaturedTrips()
    {
        featuredTripsFragment = FeaturedTripsFragment.newInstance(1, "Page 1");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, featuredTripsFragment);
        fragmentTransaction.commit();
    }

    @Override
    //from the interface - move between fragments
    public void moveToDetailsPage(Attraction attraction, BudgetBar budgetBar)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        attractionDetailsFragment = AttractionDetailsFragment.newInstance(attraction, budgetBar);
        attractionDetailsFragment.show(fragmentTransaction, "fragment_attraction_details");
    }
    @Override
    public void replaceToolbarFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.viewpager, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    @Override
    public void moveToAttractionsPage(Trip trip)
    {
        addingAttractionFragment = AddingAttractionFragment.newInstance(trip, 0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, addingAttractionFragment);
        fragmentTransaction.commit();
    }


    @Override
    public void moveToAddEventPage(Attraction attraction)
    {
        addingEventFragment = AddingEventFragment.newInstance(attraction);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        addingEventFragment.show(ft, "fragment_add_event");
    }

    public void moveToCalendarPage(Trip trip)
    {
        Intent calendarIntent = new Intent(MainActivity.this, CalendarActivity.class);
        calendarIntent.putExtra("trip", Parcels.wrap(trip));
        startActivity(calendarIntent);
    }

    @Override
    public void moveToCreateTripPage()
    {
        BuildFragment buildFragment = new BuildFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_activity, buildFragment);
        fragmentTransaction.commit();
    }

    private void swapFragment()
    {
        FeaturedTripsFragment featuredTripsFragment = new FeaturedTripsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, featuredTripsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void goToLogIn()
    {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }
}