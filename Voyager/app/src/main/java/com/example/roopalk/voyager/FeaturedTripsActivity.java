package com.example.roopalk.voyager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.roopalk.voyager.Fragments.FragmentAdapter;
import com.example.roopalk.voyager.Model.Photo;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.io.File;


public class FeaturedTripsActivity extends AppCompatActivity
{
    private final String TAG = "FeaturedTripsActivity";
    private static final String imagePath = "/storage/emulated/0/Download/Seattle_Space_Needle.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_trips);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),
                FeaturedTripsActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


        File file = new File(imagePath);
        ParseFile parseFile = new ParseFile(file);

        final Photo newPhoto = new Photo();

        newPhoto.setImage(parseFile);

        newPhoto.saveInBackground(new SaveCallback()
        {
            @Override
            public void done(ParseException e)
            {
                if(e == null)
                {
                    Log.i(TAG, "works");
                }
                else
                {
                    Log.e(TAG, "doesn't work");
                }
            }
        });
    }
}

