package com.example.roopalk.voyager.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roopalk.voyager.Adapters.MyTripsAdapter;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class MyTripsFragment extends Fragment {

    private RecyclerView rvMyTrips;
    private MyTripsAdapter mAdapter;
    private FloatingActionButton btnAddTrip;

//    @BindView(R.id.addATrip) FloatingActionButton btnAddTrip;

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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_trips, container, false);

        NetworkUtility networkUtility = new NetworkUtility(getContext());

        // get current user
        ParseUser currentUser = ParseUser.getCurrentUser();
        Log.d("MyTripsFragment", "Current user : " + ParseUser.getCurrentUser().getObjectId() + " Current Username : " + currentUser.getUsername());

        // get trips by current user
        // get data
        ArrayList<Trip> trips = null;
        try {
            trips = networkUtility.getTripsByUser(currentUser);
            Log.d("MyTripsFragment", "Trips by user : " + (trips != null && trips.size() > 0 ? trips.get(0).getObjectId() : null));
        } catch (ParseException e) {
            Log.d("MyTripsFragment", "Failed to get trips : " + e);
        }

        // bind adapter to recycler view
        rvMyTrips = (RecyclerView) view.findViewById(R.id.rvMyTrips);

        rvMyTrips.setHasFixedSize(true);

        final GridLayoutManager layout = new GridLayoutManager(getActivity(), 1);

        rvMyTrips.setLayoutManager(layout);

        // create adapter
        mAdapter = new MyTripsAdapter(getActivity(), trips);

        // bind adapter to list
        rvMyTrips.setAdapter(mAdapter);

        btnAddTrip = (FloatingActionButton) view.findViewById(R.id.btnAddTrip);

        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuildFragment buildFragment = new BuildFragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_activity, buildFragment).addToBackStack(null).commit();
            }
        });

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
