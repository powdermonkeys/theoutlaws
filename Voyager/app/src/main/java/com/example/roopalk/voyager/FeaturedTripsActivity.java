package com.example.roopalk.voyager;

<<<<<<< HEAD
import android.app.Fragment;
import android.app.FragmentTransaction;
=======
>>>>>>> 166621738b801526a7f680a7ce94e656bb28ef6e
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.roopalk.voyager.Fragments.AttractionDetailsFragment;
import com.example.roopalk.voyager.Fragments.BuildFragment;
import com.example.roopalk.voyager.Fragments.FragmentAdapter;

import butterknife.ButterKnife;


public class FeaturedTripsActivity extends AppCompatActivity implements BuildFragment.OnFragmentInteractionListener, BlankFragment.OnFragmentInteractionListener, AddingAttractionFragment.OnFragmentInteractionListener
{
    private final String TAG = "FeaturedTripsActivity";

<<<<<<< HEAD
    private Fragment attractionDetailsFragment;

    @BindView(R.id.btn) Button btn;
=======
>>>>>>> 166621738b801526a7f680a7ce94e656bb28ef6e

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_trips);

        ButterKnife.bind(this);
        attractionDetailsFragment = new AttractionDetailsFragment();

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),
                FeaturedTripsActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

<<<<<<< HEAD
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.placeholder, attractionDetailsFragment);
                ft.commit();
            }
        });
=======




>>>>>>> 166621738b801526a7f680a7ce94e656bb28ef6e
    }


    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.viewpager, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

