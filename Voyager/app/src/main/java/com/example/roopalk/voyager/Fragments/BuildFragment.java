package com.example.roopalk.voyager.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.R;

import java.util.Calendar;

import static java.lang.Integer.parseInt;


public class BuildFragment extends Fragment {

    public TextView create;
    public EditText destination;
    public EditText departureDate;
    public EditText arrivalDate;
    public TextView tvGuests;
    public TextView tvBudget;
    public SeekBar sbGuests;
    public SeekBar sbBudget;
    public Button btnDone;

    int guests = 0;
    int budget = 100;

    Calendar mCurrentDate;
    int day; int month; int year;


    onFragmentInteractionListener mListener;

    // Required empty public constructor
    public BuildFragment() { }

    // newInstance constructor for creating fragment with arguments
    public static BuildFragment newInstance(int page, String title)
    {
        BuildFragment fragmentFirst = new BuildFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    { // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_build, container, false);

        create = view.findViewById(R.id.create);
        destination = view.findViewById(R.id.destinationNamed);
        departureDate = view.findViewById(R.id.departureDate);
        arrivalDate = view.findViewById(R.id.arrivalDate);
        sbGuests = view.findViewById(R.id.sbGuests);
        tvGuests = (TextView) view.findViewById(R.id.tvGuests);
        tvBudget = view.findViewById(R.id.tvBudget);
        sbBudget = view.findViewById(R.id.sbBudget);
        btnDone = view.findViewById(R.id.btnDone);

        tvGuests.setText("" + guests);
        tvBudget.setText("" + budget);


        //seekbar
        sbGuests.setMax(10);
        sbBudget.setMax(2000);
        sbGuests.setProgress(guests);
        sbBudget.setProgress(budget);
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(final View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);



        // listener for guest drag bar
        sbGuests.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                guests = progress;
                tvGuests.setText("" + guests);
                tvGuests.setTextSize(20);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        //listener for budget drag bar
        sbBudget.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                budget = progress;
                tvBudget.setText("" + budget);
                tvGuests.setTextSize(20);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        arrivalDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == 0){
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month +1;
                            arrivalDate.setText(month + "/" + dayOfMonth + "/" + year);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
                return false;
            }
        });


        // listener that sets text of departure date
        departureDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == 0) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month + 1;
                            departureDate.setText(month + "/" + dayOfMonth + "/" + year);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
                return false;
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String DESTINATION = destination.getText().toString();
                final String CHECKIN = departureDate.getText().toString();
                final String CHECKOUT = arrivalDate.getText().toString();
                final int NUM_GUESTS = parseInt(tvGuests.getText().toString());
                try {
                    Log.d("onClick", "reached the try catch statement");
                    // a new trip query
                    final Trip newTrip = new Trip(NUM_GUESTS, CHECKIN, CHECKOUT, DESTINATION);
                    } catch (Exception e) {
                        Log.d("onClick", "didnt create object");
                    }
                }
            });

        }

        @Override
        public void onAttach (Context context){
            super.onAttach(context);
            if (context instanceof onFragmentInteractionListener) {
                mListener = (onFragmentInteractionListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement onFragmentInteractionListener");
            }
        }

        @Override
        public void onDetach () {
            super.onDetach();
            mListener = null;
        }

        public void showAttractionFragment() {
            Fragment fr = new AddingAttractionFragment();
            onFragmentInteractionListener fc = (onFragmentInteractionListener) getActivity();
            fc.replaceToolbarFragment(fr);
            }

    }
