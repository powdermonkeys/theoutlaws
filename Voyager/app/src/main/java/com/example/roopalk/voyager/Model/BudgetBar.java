package com.example.roopalk.voyager.Model;

import android.os.Build;
import android.support.annotation.RequiresApi;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void fillBudget(int attractionPrice)
    {
        int pastProgress = progressBar.getProgress();
        int currProgress = pastProgress + attractionPrice;
        progressBar.setProgress(currProgress, true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void deleteFromBudget(int attractionPrice)
    {
        int pastProgress = progressBar.getProgress();
        int currProgress = pastProgress - attractionPrice;
        progressBar.setProgress(currProgress, true);
    }

    private void setBudgetMax()
    {
        progressBar.setMax(budget);
    }
}