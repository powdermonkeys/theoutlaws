package com.example.roopalk.voyager;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

public class  BuildFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
}
