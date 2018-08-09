package com.example.roopalk.voyager.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roopalk.voyager.Adapters.MyTripsAdapter;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTripsFragment extends Fragment {

    private RecyclerView rvMyTrips;
    private List<Trip> trips; // we can change this to get a different set of trips? (the user's)
    private MyTripsAdapter mAdapter;

    @BindView(R.id.addATrip) FloatingActionButton btnAddTrip;

    onFragmentInteractionListener mListener;

    public MyTripsFragment() {
        // Required empty public constructor
    }

    public static MyTripsFragment newInstance(int page, String title) {
        Bundle args = new Bundle();

        MyTripsFragment fragment = new MyTripsFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_trips, container, false);

        // bind adapter to recycler view
        rvMyTrips = (RecyclerView) view.findViewById(R.id.rvMyTrips);

        rvMyTrips.setHasFixedSize(true);

        final GridLayoutManager layout = new GridLayoutManager(getActivity(), 1);

        rvMyTrips.setLayoutManager(layout);

        // get data
        trips = Trip.getTrips();

        // create adapter
        mAdapter = new MyTripsAdapter(getActivity(), trips);

        // bind adapter to list
        rvMyTrips.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        btnAddTrip.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.moveToCreateTripPage();
            }
        });
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
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }
}
