package com.example.roopalk.voyager.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.roopalk.voyager.Fragments.AddingAttractionFragment;
import com.example.roopalk.voyager.Fragments.AttractionDetailsFragment;
import com.example.roopalk.voyager.Fragments.FragmentAdapter;
import com.example.roopalk.voyager.Fragments.onFragmentInteractionListener;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.R;

import butterknife.ButterKnife;

public class FeaturedTripsActivity extends AppCompatActivity implements onFragmentInteractionListener
{
    private final String TAG = "FeaturedTripsActivity";

    Fragment attractionDetailsFragment = new AttractionDetailsFragment();
    Fragment addingAttractionFragment = new AddingAttractionFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_trips);

        ButterKnife.bind(this);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),
                FeaturedTripsActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

//        btn.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.viewpager, attractionDetailsFragment);
//                ft.replace(R.id.viewpager, addingAttractionFragment);
//                ft.replace(R.id.placeholder, addingAttractionFragment);
//                ft.commit();
//                Log.i(TAG, "Moving to Adding Attraction Page now");
//            }
//        });
    }


    @Override
    //from the interface - move between fragments
    public void moveToDetailsPage(Attraction attraction)
    {
        AttractionDetailsFragment attractionDetailsFragment = AttractionDetailsFragment.newInstance(attraction);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.viewpager, attractionDetailsFragment);
        ft.commit();
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
}