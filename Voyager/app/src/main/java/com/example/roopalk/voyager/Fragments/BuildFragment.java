package com.example.roopalk.voyager.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
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
    int day; int month; int year;
    int guests = 1;
    int budget = 0;

    private onFragmentInteractionListener mListener;

    // Required empty public constructor
    public BuildFragment() { }

    // newInstance constructor for creating fragment with arguments
    public static BuildFragment newInstance() {
        BuildFragment fragmentFirst = new BuildFragment();
        Bundle args = new Bundle();
        fragmentFirst.setArguments(args);
        return fragmentFirst;
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

//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_build, container, false);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);


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

        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        tvGuests.setText("# of Voyagers");
        tvBudget.setText("Your budget");
        tvGuests.setTextSize(12);
        tvBudget.setTextSize(12);


        // listener for guest drag bar
        sbGuests.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                guests = progress;
                tvGuests.setText("" + guests);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //listener for budget drag bar
        sbBudget.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                budget = progress * 50;
                tvBudget.setText("" + budget);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        arrivalDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                arrivalDate.setInputType(InputType.TYPE_NULL);
                if (event.getActionMasked() == 0){
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month + 1;
                            String strmonth = String.valueOf(month);
                            String strDay = String.valueOf(dayOfMonth);

                            if (month < 10) { strmonth = "0" + month; }
                            if (dayOfMonth < 10){ strDay = "0" + dayOfMonth; }
                            arrivalDate.setText(strmonth + "/" + strDay + "/" + year);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
                return false;            }
        });


        // listener that sets text of departure date
        departureDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                departureDate.setInputType(InputType.TYPE_NULL);
                if (event.getActionMasked() == 0) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month + 1;
                            String strmonth = String.valueOf(month);
                            String strDay = String.valueOf(dayOfMonth);

                            if (month < 10) { strmonth = "0" + month; }
                            if (dayOfMonth < 10){ strDay = "0" + dayOfMonth; }
                            departureDate.setText(strmonth + "/" + strDay + "/" + year);
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
                int LENGTH=0;

                //converting strings to simple date format
                try {
                    Date cinDate = new SimpleDateFormat("MM/dd/yyyy").parse(CHECKIN);
                    Date coutDate = new SimpleDateFormat("MM/dd/yyyy").parse(CHECKOUT);
                    long diff = coutDate.getTime() - cinDate.getTime();
                    LENGTH = (int) (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                try {
                    Log.d("onClick", "reached the try catch statement");
                    // a new trip object being created
                    final Trip newTrip = ParseObject.create(Trip.class);

                    newTrip.setTripInfo(DESTINATION, CHECKIN, CHECKOUT, NUM_GUESTS, BUDGET, LENGTH);
                    newTrip.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
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

}

