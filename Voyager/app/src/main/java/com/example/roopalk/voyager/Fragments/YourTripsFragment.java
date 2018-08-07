package com.example.roopalk.voyager.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roopalk.voyager.Adapters.YourTripsAdapter;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.R;

import java.util.List;

public class YourTripsFragment extends Fragment {

    private RecyclerView rvYourTrips;
    private List<Trip> trips; // we can change this to get a different set of trips? (the user's)
    private YourTripsAdapter mAdapter;

    onFragmentInteractionListener mListener;

    public YourTripsFragment() {
        // Required empty public constructor
    }

    public static YourTripsFragment newInstance(String param1, String param2) {
        Bundle args = new Bundle();

        YourTripsFragment fragment = new YourTripsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_your_trips, container, false);

        // bind adapter to recycler view
        rvYourTrips = (RecyclerView) view.findViewById(R.id.rvYourTrips);

        rvYourTrips.setHasFixedSize(true);

        final GridLayoutManager layout = new GridLayoutManager(getActivity(), 1);

        rvYourTrips.setLayoutManager(layout);

        // get data
        trips = Trip.getTrips();

        // create adapter
        mAdapter = new YourTripsAdapter(getActivity(), trips);

        // bind adapter to list
        rvYourTrips.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onFragmentInteractionListener) {
            mListener = (onFragmentInteractionListener) context;
        } else {
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
