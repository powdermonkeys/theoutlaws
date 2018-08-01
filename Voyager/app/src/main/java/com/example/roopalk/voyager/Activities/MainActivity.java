package com.example.roopalk.voyager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.roopalk.voyager.Adapters.FragmentAdapter;
import com.example.roopalk.voyager.Fragments.AddingAttractionFragment;
import com.example.roopalk.voyager.Fragments.AttractionDetailsFragment;
import com.example.roopalk.voyager.Fragments.onFragmentInteractionListener;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.BudgetBar;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.R;

public class  MainActivity extends AppCompatActivity implements onFragmentInteractionListener
{
    private final String TAG = "MainActivity";

    AddingAttractionFragment addingAttractionFragment;
    AttractionDetailsFragment attractionDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
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
    public void moveToCalendarPage()
    {
        Intent calendarIntent = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(calendarIntent);
    }
}