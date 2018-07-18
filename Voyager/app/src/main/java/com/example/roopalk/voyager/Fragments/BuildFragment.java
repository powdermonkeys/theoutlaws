package com.example.roopalk.voyager.Fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.roopalk.voyager.R;


public class BuildFragment extends Fragment {
    public TextView create;
    public EditText destinationNamed;
    public Button btnArrival;
    public Button btnDeparture;

    private OnFragmentInteractionListener mListener;

    // Required empty public constructor
    public BuildFragment() { }

    // newInstance constructor for creating fragment with arguments
    public static BuildFragment newInstance(int page, String title) {
        BuildFragment fragmentFirst = new BuildFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_build, container, false);

        create = view.findViewById(R.id.create);
        destinationNamed = view.findViewById(R.id.destinationNamed);
        btnArrival = view.findViewById(R.id.btnArrival);
        btnDeparture = view.findViewById(R.id.btnDeparture);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClick", "ArrivalClicked!");
                showDatePickerDialog(v);
            }
        });

        btnDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClick", "DepartureClicked!");
                showDatePickerDialog(v);
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getFragmentManager(), "datePicker");
        Log.d("Date Picker", "Date Picker Success!");
    }

}
