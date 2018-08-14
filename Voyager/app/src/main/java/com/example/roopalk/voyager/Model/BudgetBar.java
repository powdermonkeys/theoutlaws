package com.example.roopalk.voyager.Model;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.parceler.Parcel;

@Parcel
public class BudgetBar {
    public int budget;
    private static ProgressBar progressBar;
    private static TextView progressText;

    public BudgetBar(){}

    public BudgetBar(Trip trip, ProgressBar pb, TextView tvProgress)
    {
        budget = trip.getBudget();
        progressBar = pb;
        setBudgetMax();
        progressText = tvProgress;
        setText();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void fillBudget(int attractionPrice)
    {
        int pastProgress = progressBar.getProgress();
        int currProgress = pastProgress + attractionPrice;
        progressBar.setProgress(currProgress, true);
        setText();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void deleteFromBudget(int attractionPrice)
    {
        int pastProgress = progressBar.getProgress();
        int currProgress = pastProgress - attractionPrice;
        progressBar.setProgress(currProgress, true);
        setText();
    }

    private void setBudgetMax()
    {
        progressBar.setMax(budget);
    }

    public int getBudgetMax() { return progressBar.getMax(); }

    public int getCurrFill()
    {
        return progressBar.getProgress();
    }

    public void setCurrFill(int price)
    {
        progressBar.setProgress(price);
    }

    public void setText()
    {
        int currProgress = getCurrFill();
        int maxProgress = getBudgetMax();
        String progress = currProgress + "/" + maxProgress;
        progressText.setText(progress);
    }
}