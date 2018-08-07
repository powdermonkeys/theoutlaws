package com.example.roopalk.voyager.Model;

import android.widget.ProgressBar;

import org.parceler.Parcel;

@Parcel
public class BudgetBar {
    private static int budget;
    private static ProgressBar progressBar;

    public BudgetBar(){}

    public BudgetBar(Trip trip, ProgressBar pb)
    {
        budget = trip.getBudget();
        progressBar = pb;

        setBudgetMax();
    }

    public void setBudgetLevel(int attractionPrice)
    {
        int pastProgress = progressBar.getProgress();
        int currProgress = pastProgress + attractionPrice;
        progressBar.setProgress(currProgress);
    }

    private void setBudgetMax()
    {
        progressBar.setMax(budget);
    }
}