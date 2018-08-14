package com.example.roopalk.voyager.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.R;


// no longer used, see TripDetailsActivity
public class TripDetailsFragment extends Fragment {

    public static final String EXTRA_TRIP = "EXTRA_TRIP";
    private Trip mTrip;
    private ImageView ivProfile;
    private TextView tvName;
    private TextView tvDesc;
    private TextView tvDesc2;
    private FloatingActionButton btnAddTrip;
    onFragmentInteractionListener mListener;

    public TripDetailsFragment() {
        // Required empty public constructor
    }

    public static TripDetailsFragment newInstance(String param1, String param2) {
        Bundle args = new Bundle();
        TripDetailsFragment fragment = new TripDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip_details, container, false);

        ivProfile = (ImageView) view.findViewById(R.id.ivProfile);
        tvName = (TextView) view.findViewById(R.id.tvName);
        btnAddTrip = (FloatingActionButton) view.findViewById(R.id.btnAddTrip);

        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Go to trip building form",Toast.LENGTH_LONG).show();
//                BuildFragment buildFragment = new BuildFragment();
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_activity, buildFragment).addToBackStack(null).commit();
            }
        });

        return view;
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
}
