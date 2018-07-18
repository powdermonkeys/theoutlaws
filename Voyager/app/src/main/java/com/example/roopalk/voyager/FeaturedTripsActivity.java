package com.example.roopalk.voyager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.roopalk.voyager.Fragments.FragmentAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FeaturedTripsActivity extends AppCompatActivity
{
    private final String TAG = "FeaturedTripsActivity";
    //private static final String imagePath = "/storage/emulated/0/Download/Seattle_Space_Needle.jpg";

    @BindView(R.id.btn) Button btn;

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

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(FeaturedTripsActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });
    }
}

