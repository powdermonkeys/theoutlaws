package com.example.roopalk.voyager.Fragments;

import android.support.v4.app.Fragment;

import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.Trip;

public interface onFragmentInteractionListener
{
    public void moveToDetailsPage(Attraction attraction);

    public void replaceToolbarFragment(Fragment fragment);

    public void moveToAttractionsPage(Trip trip);

    public void moveToAttractionsPage(int attractionPrice); //overridden moveToAttractionsPage method
}
