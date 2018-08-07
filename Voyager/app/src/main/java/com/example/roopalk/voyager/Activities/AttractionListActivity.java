package com.example.roopalk.voyager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.roopalk.voyager.Adapters.AttractionAdapter;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.BudgetBar;
import com.example.roopalk.voyager.Model.City;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;
import com.parse.ParseException;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttractionListActivity extends AppCompatActivity
{
    private RecyclerView mRecyclerView;
    private AttractionAdapter mAdapter;
    ArrayList<Attraction> attractions = new ArrayList<>();
    ArrayList<City> cities = new ArrayList<>();
    //public onFragmentInteractionListener listener = (onFragmentInteractionListener) getSupportFragmentManager();
    Trip trip;

    @BindView(R.id.pbBudget) ProgressBar pbBudget;
    @BindView(R.id.btnDone) Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_list);
        ButterKnife.bind(this);

        trip = Parcels.unwrap(getIntent().getParcelableExtra("trip"));

        NetworkUtility networkUtility = new NetworkUtility(this);
        String dest = trip.getDestination();

        try
        {
            networkUtility.getCityFromName(dest);
            cities = networkUtility.getCities();

            networkUtility.getAttractionFromCity(cities.get(0));
            attractions = networkUtility.getAttractions();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        //  the recycler view for the attractions list
        mRecyclerView = (RecyclerView) findViewById(R.id.rvAttractions);

        BudgetBar budgetBar = new BudgetBar(trip, pbBudget);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mAdapter = new AttractionAdapter(attractions, listener, budgetBar);
        mAdapter = new AttractionAdapter(attractions, budgetBar);
        mRecyclerView.setAdapter(mAdapter);

        done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //listener.moveToCalendarPage();
                moveToCalendarPage();
            }
        });
    }

    private void moveToCalendarPage()
    {
        Intent calendarIntent = new Intent(AttractionListActivity.this, CalendarActivity.class);
        startActivity(calendarIntent);
    }
}
