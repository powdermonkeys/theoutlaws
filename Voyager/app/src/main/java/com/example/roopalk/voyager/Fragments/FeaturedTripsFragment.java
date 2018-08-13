package com.example.roopalk.voyager.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brandongogetap.stickyheaders.StickyLayoutManager;
import com.example.roopalk.voyager.Adapters.FeaturedAdapter;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;
import com.parse.ParseException;

import java.util.ArrayList;


public class FeaturedTripsFragment extends Fragment
{
    private RecyclerView rvFeatured;
    private FeaturedAdapter mAdapter;

    onFragmentInteractionListener mListener;

    public FeaturedTripsFragment()
    {
        // Required empty public constructor
    }

    public static FeaturedTripsFragment newInstance(int page, String title) {
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        FeaturedTripsFragment fragment = new FeaturedTripsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_featured_trips, container, false);

        // allows us to get trips array
        NetworkUtility networkUtility = new NetworkUtility(getContext());

        // get data
        ArrayList<Trip> trips = null;
        try {
            trips = networkUtility.getFeaturedTrips();
        } catch (ParseException e) {
            Log.d("FeaturedTripsFragment", "Failed to get trips : " + e);
        }

        // test the number of trips
        Log.d("FeaturedTripsFragment",trips.size() + "");

        // bind adapter to recycler view
        rvFeatured = (RecyclerView) view.findViewById(R.id.rvFeatured);

        // allow for optimizations
        rvFeatured.setHasFixedSize(true);

        // Define 1 column grid layout
//        final GridLayoutManager layout = new GridLayoutManager(getActivity(), 1);

        //        TODO: figure out what to do when trips.size() == 0
//        int headerTripIndex = trips.size() / 2;
//        trips.add(headerTripIndex, ParseObject.create(HeaderTrip.class));


        // create adapter
        mAdapter = new FeaturedAdapter(getActivity(), trips);

        // Define StickyHeader Layout
        final StickyLayoutManager layout = new StickyLayoutManager(getActivity(), mAdapter);

        //give LayoutManager to RecyclerView to position items on the screen
        rvFeatured.setLayoutManager(layout);


        // bind adapter to list
        rvFeatured.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof onFragmentInteractionListener)
        {
            mListener = (onFragmentInteractionListener) context;
        }
        else
            {
            throw new RuntimeException(context.toString()
                    + " must implement onFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //
    private void swapFragment(){
        TripDetailsFragment tripDetailsFragment = new TripDetailsFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, tripDetailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}