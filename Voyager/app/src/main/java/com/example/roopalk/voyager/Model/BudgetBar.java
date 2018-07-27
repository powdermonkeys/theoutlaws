package com.example.roopalk.voyager.Model;

import android.widget.ProgressBar;

public class BudgetBar
{
    private static int budget;
    private static ProgressBar progressBar;

    public BudgetBar (Trip trip, ProgressBar pb)
    {
        budget = trip.getBudget();
        progressBar = pb;
    }
    public void setBudgetLevel(int attractionPrice)
    {
        int pastProgress = progressBar.getProgress();
        int currProgress = pastProgress + attractionPrice;
        progressBar.setProgress(currProgress);
    }

    public void setBudgetMax()
    {
        progressBar.setMax(budget);
    }
}