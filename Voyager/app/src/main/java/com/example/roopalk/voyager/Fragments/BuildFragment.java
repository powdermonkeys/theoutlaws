package com.example.roopalk.voyager.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.roopalk.voyager.Activities.CalendarActivity;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Calendar;

import static java.lang.Integer.parseInt;


public class BuildFragment extends Fragment {

    public TextView create;
    public AutoCompleteTextView destination;
    public EditText departureDate;
    public EditText arrivalDate;
    public TextView tvGuests;
    public TextView tvBudget;
    public SeekBar sbGuests;
    public SeekBar sbBudget;
    public Button btnDone;
    public Button button;

    int guests = 1;
    int budget = 0;

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
        button = view.findViewById(R.id.calendar);

        tvGuests.setText("" + guests);
        tvBudget.setText("" + budget);


        //seekbar
        sbGuests.setMax(10);
        sbBudget.setMax(40);
        sbGuests.setProgress(guests);
        sbBudget.setProgress(budget);

        //getting autocomplete textview

        NetworkUtility networkUtility = new NetworkUtility(getContext());

        try
        {
            ArrayList<String> cityNames = networkUtility.getCityNames();
            ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, cityNames);
            destination.setAdapter(cityAdapter);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
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

        tvGuests.setTextSize(20);
        tvBudget.setTextSize(20);


        // listener for guest drag bar
        sbGuests.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                guests = progress;
                tvGuests.setText("" + guests);
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
                budget = progress * 50;
                tvBudget.setText("$" + budget);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
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

        //On Click Listener for the button when done designing a trip
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String DESTINATION = destination.getText().toString();
                final String CHECKIN = departureDate.getText().toString();
                final String CHECKOUT = arrivalDate.getText().toString();
                final int NUM_GUESTS = parseInt(tvGuests.getText().toString());
                final int BUDGET = parseInt(tvBudget.getText().toString());
                try
                {
                    Log.d("onClick", "reached the try catch statement");
                    // a new trip object being created
                    final Trip newTrip = ParseObject.create(Trip.class);
                    newTrip.setTripInfo(DESTINATION, CHECKIN, CHECKOUT, NUM_GUESTS, BUDGET);

                    newTrip.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {

//                            //TESTING CODE-
//                            // Create new fragment and transaction
//                            Fragment newFragment = new AddingAttractionFragment();
//                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//                            // Replace whatever is in the fragment_container view with this fragment. and add the transaction to the back stack if needed
//                            transaction.replace(R.id.fragment_adding_attraction, newFragment);
//                            transaction.addToBackStack(null);
//                            transaction.commit();


                            // WORKING CODE
                            mListener.moveToAttractionsPage(newTrip);

                        }
                    });
                } catch (Exception e) {
                    Log.d("onClick", "didnt create object");
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CalendarActivity.class);
                Log.d("intent", "reached intent");
                startActivity(intent);
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
    public void onDetach ()
    {
        super.onDetach();
        mListener = null;
    }

}
