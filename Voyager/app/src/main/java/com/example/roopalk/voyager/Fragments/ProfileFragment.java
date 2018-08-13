package com.example.roopalk.voyager.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.roopalk.voyager.R;
import com.parse.ParseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment
{
    @BindView(R.id.tvTravelTips) TextView tvTravelTips;

    SignoutListener signoutListener;

    @BindView(R.id.btnSignout) Button btnSignout;
    public static ProfileFragment newInstance(int page, String title) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                signoutListener.goToLogIn();
            }
        });

        tvTravelTips.setText("If you want to learn as much as you can about the   culture of the city you're traveling to, you should: \n" +
                "· visit places of worship\n· take public transportation\n· go to a hole-in-the-wall restaurant\n");

    }

    public interface SignoutListener
    {
        void goToLogIn();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if(context instanceof SignoutListener)
        {
            signoutListener = (SignoutListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " must implement SignoutListener");
        }
    }
}
