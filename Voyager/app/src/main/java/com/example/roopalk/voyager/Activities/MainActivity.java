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
import com.example.roopalk.voyager.Fragments.onFragmentInteractionListener;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.BudgetBar;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class  MainActivity extends AppCompatActivity implements onFragmentInteractionListener
{
    private final String TAG = "MainActivity";

    AddingAttractionFragment addingAttractionFragment;

    AttractionDetailsFragment attractionDetailsFragment;
    AddingEventFragment addingEventFragment;

    SimpleDateFormat mdformat;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),
                MainActivity.this));

        //gets today's date
        Date currentDate = new Date();

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        // the alert dialog for trip builder

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("We noticed that you are currently on a trip, would you like to be redirected to your calendar?");
            alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });

            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
        addingAttractionFragment = AddingAttractionFragment.newInstance(trip);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, addingAttractionFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void moveToAddEventPage(Attraction attraction)
    {
        addingEventFragment = AddingEventFragment.newInstance(attraction);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        addingEventFragment.show(ft, "fragment_add_event");
    }

    public void moveToCalendarPage()
    {
        Intent calendarIntent = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(calendarIntent);
    }
}