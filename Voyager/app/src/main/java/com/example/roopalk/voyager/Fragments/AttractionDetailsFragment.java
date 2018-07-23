package com.example.roopalk.voyager.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roopalk.voyager.Adapters.ViewPagerAdapter;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.City;
import com.example.roopalk.voyager.Model.Photo;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class AttractionDetailsFragment extends Fragment
{
    @BindView(R.id.vpImageSlideshow) ViewPager viewPager;
    @BindView(R.id.ciImageSwiper) CircleIndicator circleIndicator;

    ViewPagerAdapter viewPagerAdapter;
    ArrayList<String> imageURLs = new ArrayList<>();

    NetworkUtility networkUtility = new NetworkUtility(getContext());

    List<City> cities = new ArrayList<>();
    List<Attraction> attractions = new ArrayList<>();
    List<Photo> photos = new ArrayList<>();


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

        try
        {
            getImages("Tokyo");
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

       viewPagerAdapter = new ViewPagerAdapter(getContext(), imageURLs);
       viewPager.setAdapter(viewPagerAdapter);
       circleIndicator.setViewPager(viewPager);
    }


    public void getImages(String cityname) throws ParseException
    {
        networkUtility.getCityFromName(cityname);
        cities = networkUtility.getCities();

        City c = cities.get(0);

        networkUtility.getAttractionFromCity(c);
        attractions = networkUtility.getAttractions();

        for(int i = 0; i < attractions.size(); i++)
        {
            networkUtility.getImagesFromAttraction(attractions.get(i));
            photos.addAll(networkUtility.getPhotos());
        }

        for (int j = 0; j < photos.size(); j++)
        {
            String URL = photos.get(j).getImage().getUrl();
            imageURLs.add(URL);
        }
    }
}