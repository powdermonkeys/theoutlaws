package com.example.roopalk.voyager.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.Integer.parseInt;

public class BuildFragment extends Fragment
{
    @BindView(R.id.destinationNamed) AutoCompleteTextView destination;
    @BindView(R.id.departureDate) EditText departureDate;
    @BindView(R.id.arrivalDate) EditText arrivalDate;
    @BindView(R.id.sbBudget) SeekBar sbBudget;
    @BindView(R.id.sbGuests) SeekBar sbGuests;
    @BindView(R.id.btnDone) Button btnDone;
    @BindView(R.id.tvGuests) TextView tvGuests;
    @BindView(R.id.tvBudget) TextView tvBudget;

    Calendar mCurrentDate;
    int day;
    int month;
    int year;

    int guests = 1;
    int budget = 0;

    private onFragmentInteractionListener mListener;

    public BuildFragment()
    {
        // Required empty public constructor
    }

    public static BuildFragment newInstance(int page, String title)
    {
        BuildFragment fragment = new BuildFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_build, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        tvGuests.setText("" + guests);
        tvBudget.setText("" + budget);
        sbGuests.setMax(10);
        sbBudget.setMax(40);
        sbGuests.setProgress(guests);
        sbBudget.setProgress(budget);

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
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //listener for budget drag bar
        sbBudget.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                budget = progress * 50;
                tvBudget.setText("" + budget);
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
                if (event.getActionMasked() == 0) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month + 1;
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
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
                final int LENGTH = getTripLength(CHECKIN, CHECKOUT);
                try {
                    Log.d("onClick", "reached the try catch statement");
                    // a new trip object being created
                    final Trip newTrip = ParseObject.create(Trip.class);

                    newTrip.setTripInfo(DESTINATION, CHECKIN, CHECKOUT, NUM_GUESTS, BUDGET, LENGTH);

                    newTrip.saveInBackground(new SaveCallback()
                    {

                        @Override
                        public void done(ParseException e)
                        {
                            mListener.moveToCalendarPage(newTrip);
                        }
                    });
                } catch (Exception e) {
                    Log.d("onClick", "didnt create object");
                }
            }
        });
    }

    @Override
    public void onAttach(Context context)
    {
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public int getTripLength(String checkin, String checkout)
    {
        int length = 0;
        try
        {
            SimpleDateFormat myFormat = new SimpleDateFormat(" MM dd yyyy");
            Date date1 = myFormat.parse(checkin);
            Date date2 = myFormat.parse(checkout);
            long diff = date2.getTime() - date1.getTime();
            System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            length = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        }
        catch (java.text.ParseException e)
        {
            e.printStackTrace();
        }

        return length;
    }
}
