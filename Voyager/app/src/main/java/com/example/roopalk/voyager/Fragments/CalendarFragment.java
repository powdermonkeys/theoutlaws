package com.example.roopalk.voyager.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.roopalk.voyager.Model.Event;
import com.example.roopalk.voyager.R;

import java.util.ArrayList;
import java.util.List;

public class CalendarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String START_DATE = "";
    private static final String END_DATE = "";


    private static ArrayList<Event> events = new ArrayList<>();
    public onFragmentInteractionListener mListener;
    private RelativeLayout rlCalendarRoot;
    private static final int MINUTES_IN_A_DAY = 24 * 60;


    public CalendarFragment() {
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



    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        rlCalendarRoot = v.findViewById(R.id.rl_calendar);
        rlCalendarRoot.post(new Runnable() {
            @Override
            public void run() {
                drawEvents(events);
            }
        });
        return v;
    }

    public void drawEvents(List<Event> events) {
        if (events != null && !events.isEmpty()) {
            int screenWidth = rlCalendarRoot.getWidth();
            int screenHeight = (int) ((23f * getResources().getDimension(R.dimen.hour_divider_height) + (24f * getResources().getDimension(R.dimen.hour_divider_margin_top))));

            for (Event e : events) {
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

    private int minutesToPixels(int screenHeight, int minutes){
        return (screenHeight * minutes) / MINUTES_IN_A_DAY;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onFragmentInteractionListener) {
            mListener = (onFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



}
