package com.example.roopalk.voyager.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roopalk.voyager.Adapters.AttractionAdapter;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.City;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;
import com.parse.ParseException;

import java.util.ArrayList;


public class AddingAttractionFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private AttractionAdapter mAdapter;
    ArrayList<Attraction> attractions = new ArrayList<>();
    ArrayList<City> cities = new ArrayList<>();
    public onFragmentInteractionListener listener;

    public AddingAttractionFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_adding_attraction, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        NetworkUtility networkUtility = new NetworkUtility(getContext());

        try
        {
            networkUtility.getCityFromName("Seattle");
            cities = networkUtility.getCities();

            networkUtility.getAttractionFromCity(cities.get(0));
            attractions = networkUtility.getAttractions();


            //  the recycler view for the attractions list
            mRecyclerView = (RecyclerView) view.findViewById(R.id.rvAttractions);


            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new AttractionAdapter(attractions, listener);
            mRecyclerView.setAdapter(mAdapter);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
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