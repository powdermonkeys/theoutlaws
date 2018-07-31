package com.example.roopalk.voyager.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.roopalk.voyager.Adapters.HorizontalAttractionAdapter;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.Event;
import com.example.roopalk.voyager.R;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarFragment extends Fragment
{

    private static final String START_DATE = "";
    private static final String END_DATE = "";


    private static ArrayList<Event> events = new ArrayList<>();
    public onFragmentInteractionListener mListener;
    private static final int MINUTES_IN_A_DAY = 24 * 60;

    //get the attractions list after they have all been added

    private ArrayList<Attraction> attractions = AttractionDetailsFragment.getChosenAttractions();

    private HorizontalAttractionAdapter horizontalAttractionAdapter;

    @BindView(R.id.rvHorizontal) RecyclerView rvHorizontal;
    @BindView(R.id.rl_calendar) RelativeLayout rlCalendarRoot;

    public CalendarFragment()
    {
        // Required empty public constructor
    }

    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(START_DATE, param1);
        args.putString(END_DATE, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static CalendarFragment newInstance(Event event)
    {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putParcelable("event", Parcels.wrap(event));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    public void drawEvents(List<Event> events)
    {
        if (events != null && !events.isEmpty())
        {
            int screenWidth = rlCalendarRoot.getWidth();
            int screenHeight = (int) ((23f * getResources().getDimension(R.dimen.hour_divider_height) + (24f * getResources().getDimension(R.dimen.hour_divider_margin_top))));

            for (Event e : events)
            {
                int eventWidth = screenWidth;
                int leftMargin = (int) (screenWidth/1.25);
                int eventHeight = minutesToPixels(screenHeight, e.getEndTimeInMinutes() + 1) - minutesToPixels(screenHeight, e.getStartTimeInMinutes());
                int topMargin = minutesToPixels(screenHeight, e.getStartTimeInMinutes());

                TextView eventView = new TextView(getContext());
                eventView.setText(getString(R.string.event_name_format, e.getName()));
                eventView.setTextColor(Color.BLACK);
                eventView.setBackgroundColor(Color.BLUE);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(eventWidth, eventHeight);
                params.setMargins(leftMargin, topMargin, 0, 0);

                rlCalendarRoot.addView(eventView, params);
            }
        }
    }

    private int minutesToPixels(int screenHeight, int minutes)
    {
        return (screenHeight * minutes) / MINUTES_IN_A_DAY;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onFragmentInteractionListener)
        {
            mListener = (onFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvHorizontal.setLayoutManager(linearLayoutManager);
        horizontalAttractionAdapter = new HorizontalAttractionAdapter(attractions, mListener);
        rvHorizontal.setAdapter(horizontalAttractionAdapter);

        rlCalendarRoot.post(new Runnable() {
            @Override
            public void run() {
                drawEvents(events);
            }
        });
    }

}
