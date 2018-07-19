package com.example.roopalk.voyager.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public  class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    EditText arrivalDate;
    EditText departureDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, month, day);
        //TODO- SEE WHAT THIS PRINTS OUT AS A TEST CASE
        System.out.print(year + month + day);
    }

}
