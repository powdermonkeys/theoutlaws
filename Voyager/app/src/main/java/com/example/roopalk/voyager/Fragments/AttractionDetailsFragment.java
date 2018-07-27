package com.example.roopalk.voyager.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roopalk.voyager.Adapters.ViewPagerAdapter;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.Photo;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class AttractionDetailsFragment extends DialogFragment implements onFragmentInteractionListener
{
    @BindView(R.id.vpImageSlideshow) ViewPager viewPager;
    @BindView(R.id.ciImageSwiper) CircleIndicator circleIndicator;
    @BindView(R.id.tvAttractionName) TextView tvAttractionName;
    @BindView(R.id.tvAttractionTime) TextView tvAttractionTime;
    @BindView(R.id.tvAttractionDescription) TextView tvAttractionDescription;
    @BindView(R.id.tvAttractionPrice) TextView tvAttractionPrice;
    @BindView(R.id.leaveDialog) ImageView leaveDialog;
    @BindView(R.id.add) Button add;

    ViewPagerAdapter viewPagerAdapter;

    NetworkUtility networkUtility = new NetworkUtility(getContext());

    ArrayList<Photo> photos = new ArrayList<>();
    ArrayList<String> imageURLs = new ArrayList<>();

    private static final String TAG = "DetailsFragment";

    Attraction attraction;

    // Required empty public constructor

    public AttractionDetailsFragment()
    {
    }

    public static AttractionDetailsFragment newInstance(Attraction attraction)
    {
        AttractionDetailsFragment attractionDetailsFragment = new AttractionDetailsFragment();
        Bundle currAttraction = new Bundle();
        currAttraction.putParcelable("attraction", attraction);
        attractionDetailsFragment.setArguments(currAttraction);
        return attractionDetailsFragment;
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

       attraction = getArguments().getParcelable("attraction");

        try
        {
            getImages();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

       viewPagerAdapter = new ViewPagerAdapter(getContext(), imageURLs);
       viewPager.setAdapter(viewPagerAdapter);
       circleIndicator.setViewPager(viewPager);

       leaveDialog.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View v)
           {
               dismiss();
           }
       });

       add.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View v)
           {

           }
       });
    }


    public void getImages() throws ParseException
    {
        networkUtility.getImagesFromAttraction(attraction);
        photos = networkUtility.getPhotos();

        for(int i = 0; i < photos.size(); i++)
        {
            Photo p = photos.get(i);
            ParseFile images = p.getImage();
            String url = images.getUrl();

            imageURLs.add(url);
        }

        tvAttractionName.setText(attraction.getAttractionName());
        tvAttractionDescription.setText(attraction.getAttractionDescription());
        tvAttractionTime.setText(attraction.getEstimatedTime());
        tvAttractionPrice.setText(attraction.getEstimatedPrice()+"");
    }

    @Override
    public void moveToDetailsPage(Attraction attraction){ }

    @Override
    public void replaceToolbarFragment(Fragment fragment) { }

    @Override
    public void moveToAttractionsPage(Trip trip) {

    }

    @Override
    public void moveToAttractionsPage(int attractionPrice)
    {

    }
}