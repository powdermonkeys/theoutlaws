package com.example.roopalk.voyager.Fragments;

import android.support.v4.app.Fragment;

import com.example.roopalk.voyager.Model.Attraction;

public interface onFragmentInteractionListener
{
    public void moveToDetailsPage(Attraction attraction);

    public void replaceToolbarFragment(Fragment fragment);
}
