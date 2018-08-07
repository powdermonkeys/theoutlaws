package com.example.roopalk.voyager.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roopalk.voyager.Adapters.FeaturedAdapter;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.R;

import java.util.List;


public class FeaturedTripsFragment extends Fragment
{
    private RecyclerView rvFeatured;
    private List<Trip> trips;
    private FeaturedAdapter mAdapter;

    onFragmentInteractionListener mListener;

    public FeaturedTripsFragment()
    {
        // Required empty public constructor
    }

    public static FeaturedTripsFragment newInstance(int page, String title)
    {
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

        // bind adapter to recycler view
        rvFeatured = (RecyclerView) view.findViewById(R.id.rvFeatured);

        // allow for optimizations
        rvFeatured.setHasFixedSize(true);

        // Define 1 column grid layout
        final GridLayoutManager layout = new GridLayoutManager(getActivity(), 1);

        //give LayoutManager to RecyclerView to position items on the screen
        rvFeatured.setLayoutManager(layout);

        //        // get data
        trips = Trip.getTrips();
//
        // create adapter
        mAdapter = new FeaturedAdapter(getActivity(), trips);

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
}