package com.example.roopalk.voyager.Fragments;

import android.support.v4.app.Fragment;

import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.BudgetBar;
import com.example.roopalk.voyager.Model.Trip;

public interface onFragmentInteractionListener
{
    public void moveToDetailsPage(Attraction attraction, BudgetBar budgetBar);

    public void replaceToolbarFragment(Fragment fragment);

    public void moveToAttractionsPage(Trip trip);

    void moveToFeaturedTrips();

    public void moveToAddEventPage(Attraction attraction);

    public void moveToCalendarPage(Trip trip);

    public void moveToCreateTripPage();
}
