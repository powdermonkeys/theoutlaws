package com.example.roopalk.voyager.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.MyAdapter;
import com.example.roopalk.voyager.R;

import java.util.ArrayList;

public class AddingAttractionFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    ArrayList<Attraction> attractions = new ArrayList<>();


    public AddingAttractionFragment() {
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


        Attraction attraction = new Attraction();
        attraction.setAttractionName("Pike Place Market");
        attraction.setAttractionDescription("Pike Place Market is located in the center of Seattle. Built on the board... ");
        attraction.setEstimatedPrice(30.0);
        attractions.add(attraction);


        //  the recycler view for the attractions list
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvAttractions);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MyAdapter(attractions);
        mRecyclerView.setAdapter(mAdapter);

        Log.i("Adding", "Ok This shOUDL dwiRHE");
    }
}