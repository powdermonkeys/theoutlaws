package com.example.roopalk.voyager.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roopalk.voyager.Adapters.ViewPagerAdapter;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.City;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class AttractionDetailsFragment extends Fragment
{
    ArrayList<Integer> imageURLs = new ArrayList<>();

    NetworkUtility networkUtility = new NetworkUtility(getContext());
    ArrayList<Attraction> attractions;
    ArrayList<City> cities;

    @BindView(R.id.vpImageSlideshow) ViewPager viewPager;
    @BindView(R.id.ciImageSwiper) CircleIndicator circleIndicator;

    ViewPagerAdapter viewPagerAdapter;

    // Required empty public constructor

    public AttractionDetailsFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attraction_details, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    { ButterKnife.bind(this, view);
       super.onViewCreated(view, savedInstanceState);

       imageURLs.add(R.drawable.calendaricon);
       imageURLs.add(R.drawable.ic_launcher_background);
       imageURLs.add(R.drawable.person);

       viewPagerAdapter = new ViewPagerAdapter(getContext(), imageURLs);
       viewPager.setAdapter(viewPagerAdapter);
       circleIndicator.setViewPager(viewPager);
    }
}
