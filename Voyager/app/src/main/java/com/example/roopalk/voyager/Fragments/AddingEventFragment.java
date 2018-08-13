package com.example.roopalk.voyager.Fragments;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
    @BindView(R.id.etStartTime) TextView etStartTime;
    @BindView(R.id.etEndTime) TextView etEndtime;
    @BindView(R.id.vpImageSlideshow) ViewPager viewPager;
    @BindView(R.id.ciImageSwiper) CircleIndicator circleIndicator;
    @BindView(R.id.attractionName) TextView attractioName;
    @BindView(R.id.tvRecommendedTime) TextView tvRecommendedTime;

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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        final Attraction chosenAttraction = getArguments().getParcelable("chosenAttraction");
        attractioName.setText(chosenAttraction.getAttractionName().toString());

        String timeSpent = chosenAttraction.getEstimatedTime();

        tvRecommendedTime.setText("Average time spent here: " + timeSpent);

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

        etStartTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etStartTime.setInputType(InputType.TYPE_NULL);
                if (event.getActionMasked() == 0) {
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
                return false;
            }
        });



        etEndtime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etEndtime.setInputType(InputType.TYPE_NULL);
                if (event.getActionMasked() == 0) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener()
                    {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                        {
                            etEndtime.setText(hourOfDay + ":" + minute);
                            startHour = hourOfDay;
                            startMin = minute;
                        }
                    }, startHour, startMin, false);

                    timePickerDialog.show();

                }
                return false;
            }
        });

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((CalendarActivity)getActivity()).setTime(startHour, startMin, endHour, endMin, chosenAttraction);
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
