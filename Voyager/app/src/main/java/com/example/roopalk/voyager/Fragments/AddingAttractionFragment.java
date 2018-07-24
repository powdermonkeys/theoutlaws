package com.example.roopalk.voyager.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.R;

import java.util.ArrayList;

public class AddingAttractionFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    ArrayList<Attraction> attractions = new ArrayList<>();


    public AddingAttractionFragment() { }




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Attraction attraction = new Attraction();
        attraction.setAttractionName("Pike Place Market");
        attraction.setAttractionDescription("Pike Place Market is located in the center of Seattle. Built on the board... ");
        attraction.setEstimatedPrice(30.0);
        attractions.add(attraction);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_adding_attraction, container, false);

//
//        //  the recycler view for the attractions list
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvAttractions);
//
//
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new MyAdapter(attractions);
//        mRecyclerView.setAdapter(mAdapter);
        return view;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}