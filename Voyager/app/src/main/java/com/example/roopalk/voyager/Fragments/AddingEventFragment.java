package com.example.roopalk.voyager.Fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.example.roopalk.voyager.Activities.CalendarActivity;
import com.example.roopalk.voyager.Adapters.ViewPagerAdapter;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;
import com.parse.ParseException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class AddingEventFragment extends DialogFragment
{
    @BindView(R.id.leaveDialog) ImageView leaveDialog;
    @BindView(R.id.add) Button add;
    @BindView(R.id.etStartTime) EditText etStartTime;
    @BindView(R.id.etEndTime) EditText etEndtime;
    @BindView(R.id.vpImageSlideshow) ViewPager viewPager;
    @BindView(R.id.ciImageSwiper) CircleIndicator circleIndicator;

    ViewPagerAdapter viewPagerAdapter;
    ArrayList<String> imageURLs = new ArrayList<>();

    NetworkUtility networkUtility = new NetworkUtility(getContext());

    int startHour, startMin, endHour, endMin;

    public static AddingEventFragment newInstance(Attraction chosenAttraction)
    {
        Bundle args = new Bundle();
        args.putParcelable("chosenAttraction", chosenAttraction);

        AddingEventFragment addingEventFragment = new AddingEventFragment();
        addingEventFragment.setArguments(args);

        return addingEventFragment;
    }

    public AddingEventFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        final Attraction chosenAttraction = getArguments().getParcelable("chosenAttraction");
        try
        {
            networkUtility.getImages(chosenAttraction);
            imageURLs = networkUtility.getImageURLs();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        viewPagerAdapter = new ViewPagerAdapter(getContext(), imageURLs);
        viewPager.setAdapter(viewPagerAdapter);
        circleIndicator.setViewPager(viewPager);

        etStartTime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                        etStartTime.setText(hourOfDay + ":" + minute);
                        startHour = hourOfDay;
                        startMin = minute;
                    }
                }, startHour, startMin, false);

                timePickerDialog.show();
            }
        });

        etEndtime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                        etEndtime.setText(hourOfDay + ":" + minute);
                        endHour = hourOfDay;
                        endMin = minute;
                    }
                }, endHour, endMin, false);

                timePickerDialog.show();
            }
        });

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((CalendarActivity)getActivity()).setTime(startHour, startMin, endHour, endMin);
                dismiss();
            }
        });

        leaveDialog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });


    }
}
