package com.example.roopalk.voyager.Fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.R;

public class BuildFragment extends Fragment {
    public TextView create;
    public EditText destinationNamed;
    public EditText departureDate;
    public EditText arrivalDate;
    public EditText etGuests;
    public EditText etBudget;
    public Button btnDone;


    onFragmentInteractionListener mListener;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_build, container, false);

        create = view.findViewById(R.id.create);
        destinationNamed = view.findViewById(R.id.destinationNamed);
        departureDate = view.findViewById(R.id.departureDate);
        arrivalDate = view.findViewById(R.id.arrivalDate);
        etGuests = view.findViewById(R.id.etGuests);
        etBudget = view.findViewById(R.id.etBudget);
    //    btnDone = view.findViewById(R.id.btnDone);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        arrivalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
                Log.d("datePicker", "Showed Date picker");

            }
        });

        departureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
                Log.d("datePicker", "Showed Date picker");


                //TODO- remove later, work on getting this test case to log

                try{
                    Log.d("onClick", "reached the try catch statement");
                    new Trip(Integer.parseInt(etGuests.getText().toString()), departureDate.getText().toString(), arrivalDate.getText().toString(), destinationNamed.getText().toString());
                    //TODO- make sure test cases returns an object

                }catch (Exception e){ Log.d("onClick", "didnt create object"); }
            }
        });

//        btnDone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String strguests = etGuests.getText().toString();
//                int  guests = Integer.parseInt(strguests);
//
//                //TODO- remove later, work on getting this test case to log
//                try{
//                    Log.d("onClick", "reached the try catch statement");
//                    new Trip(destinationNamed.getText().toString(), "test", departureDate.getText().toString(), arrivalDate.getText().toString(), guests);
//
//                    //TODO- make sure test cases returns an object
//                }catch (Exception e){ Log.d("onClick", "didnt create object"); }
//
//                showAttractionFragment();
//            }
//        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onFragmentInteractionListener) {
            mListener = (onFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getFragmentManager(), "datePicker");


    }

    public void showAttractionFragment()
    {
        Fragment fr = new AddingAttractionFragment();
        onFragmentInteractionListener fc = (onFragmentInteractionListener) getActivity();
        fc.replaceToolbarFragment(fr);
    }

}
