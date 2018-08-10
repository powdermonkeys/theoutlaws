package com.example.roopalk.voyager.Fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.roopalk.voyager.Adapters.AttractionAdapter;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.BudgetBar;
import com.example.roopalk.voyager.Model.City;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;
import com.example.roopalk.voyager.SwipeController;
import com.example.roopalk.voyager.SwipeControllerActions;
import com.parse.ParseException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddingAttractionFragment extends Fragment
{
    private AttractionAdapter mAdapter;
    ArrayList<Attraction> attractions = new ArrayList<>();
    ArrayList<City> cities = new ArrayList<>();
    public calendarListener listener;
    public onFragmentInteractionListener mListener;

    Trip trip;

    BudgetBar budgetBar;

    int currFill;

    static ArrayList<Attraction> chosen_attractions = new ArrayList<>();

    @BindView(R.id.pbBudget) ProgressBar pbBudget;
    @BindView(R.id.rvAttractions) RecyclerView mRecyclerView;
    @BindView(R.id.done) Button done;

    public AddingAttractionFragment()
    {

    }

    public static AddingAttractionFragment newInstance(Trip trip, int currFill)
    {
        Bundle args = new Bundle();
        args.putParcelable("trip", trip);
        args.putInt("currFill", currFill);
        AddingAttractionFragment fragment = new AddingAttractionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adding_attraction, container, false);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        NetworkUtility networkUtility = new NetworkUtility(getContext());
        trip = getArguments().getParcelable("trip");
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

        budgetBar = new BudgetBar(trip, pbBudget);
        currFill = getArguments().getInt("currFill");
        budgetBar.setCurrFill(currFill);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new AttractionAdapter(attractions, mListener, budgetBar);
        mRecyclerView.setAdapter(mAdapter);

        setUpSwipeController(view);

        done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.moveToCalendarPage(trip, getContext(), budgetBar.getCurrFill());
            }
        });
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if(context instanceof calendarListener)
        {
            listener = (calendarListener) context;
        }
        else if(context instanceof onFragmentInteractionListener)
        {
            mListener = (onFragmentInteractionListener) context;
        }
        else
        {
            throw new ClassCastException(context.toString() + "must implement calendarListener");
        }
    }

    public void setUpSwipeController(final View view)
    {
        final SwipeController swipeController = new SwipeController(new SwipeControllerActions()
        {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onRightClicked(int position)
            {
                final Attraction currentAttraction = attractions.get(position);

                if(!chosen_attractions.contains(currentAttraction))
                {
                    chosen_attractions.add(currentAttraction);
                    Snackbar snackbar = Snackbar.make(view, "Attraction added!", Snackbar.LENGTH_SHORT);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chosen_attractions.remove(currentAttraction);
                            budgetBar.deleteFromBudget(currentAttraction.getEstimatedPrice());
                        }
                    });
                    snackbar.show();
                }

                int numGuests = trip.getNumGuests();
                int totalPrice = currentAttraction.getEstimatedPrice() * numGuests;

                budgetBar.fillBudget(totalPrice);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    public static ArrayList<Attraction> getChosenAttractions()
    {
        return chosen_attractions;
    }

    public interface calendarListener
    {
        void moveToCalendarPage(Trip trip, Context context, int currBudget);
    }
}