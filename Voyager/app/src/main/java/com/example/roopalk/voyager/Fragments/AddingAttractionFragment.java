package com.example.roopalk.voyager.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.roopalk.voyager.Adapters.AttractionAdapter;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.BudgetBar;
import com.example.roopalk.voyager.Model.City;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;
import com.parse.ParseException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddingAttractionFragment extends Fragment
{

    private RecyclerView mRecyclerView;
    private AttractionAdapter mAdapter;
    ArrayList<Attraction> attractions = new ArrayList<>();
    ArrayList<City> cities = new ArrayList<>();
    public onFragmentInteractionListener listener;
    Trip trip;

    @BindView(R.id.pbBudget) ProgressBar pbBudget;
    @BindView(R.id.btnDone) Button done;

    public AddingAttractionFragment()
    {

    }

    public static AddingAttractionFragment newInstance(Trip trip)
    {
        Bundle args = new Bundle();
        args.putParcelable("trip", trip);

        AddingAttractionFragment fragment = new AddingAttractionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static AddingAttractionFragment newInstance(int attractionPrice)
    {
        Bundle price = new Bundle();
        price.putInt("price", attractionPrice);
        AddingAttractionFragment fragment = new AddingAttractionFragment();
        fragment.setArguments(price);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_adding_attraction, container, false);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        NetworkUtility networkUtility = new NetworkUtility(getContext());
        trip = getArguments().getParcelable("trip");
        String dest = trip.getDestination();

        try
        {
            networkUtility.getCityFromName(dest);
            cities = networkUtility.getCities();

            networkUtility.getAttractionFromCity(cities.get(0));
            attractions = networkUtility.getAttractions();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        //  the recycler view for the attractions list
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvAttractions);


        BudgetBar budgetBar = new BudgetBar(trip, pbBudget);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new AttractionAdapter(attractions, listener, budgetBar);
        mRecyclerView.setAdapter(mAdapter);

        done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.moveToCalendarPage();
            }
        });

    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if(context instanceof onFragmentInteractionListener)
        {
            listener = (onFragmentInteractionListener) context;
        }
        else
        {
            throw new ClassCastException(context.toString() + "must implement onFragmentInteractionListener");
        }
    }
}