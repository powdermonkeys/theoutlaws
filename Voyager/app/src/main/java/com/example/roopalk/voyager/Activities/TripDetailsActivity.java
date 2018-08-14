package com.example.roopalk.voyager.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.roopalk.voyager.Model.Photo;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class TripDetailsActivity extends AppCompatActivity {

    private static final String TAG = "TripDetailsActivity";
    ImageView ivProfile;
    TextView tvName;
    TextView tvBudget;
    TextView tvAttractionName;
    TextView tvGuestNumber;
    TextView tvDates;
    TextView tvTips;
    TextView tvCheckIn;
    TextView tvCheckOut;
    TextView tvLong;
    FloatingActionButton btnAddTrip;
    Trip mTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        tvName = (TextView) findViewById(R.id.tvName);
        tvBudget = (TextView) findViewById(R.id.tvBudget);
        tvGuestNumber = (TextView) findViewById(R.id.tvGuestNumber);
        tvDates = (TextView) findViewById(R.id.tvDates);
        tvCheckIn = (TextView) findViewById(R.id.tvCheckIn);
        tvCheckOut = (TextView) findViewById(R.id.tvCheckOut);
        tvTips = (TextView) findViewById(R.id.tvTips);
        tvAttractionName = (TextView) findViewById(R.id.tvAttractionName);
        tvLong = (TextView) findViewById(R.id.tv_long);
        btnAddTrip = (FloatingActionButton) findViewById(R.id.btnAddTrip);

        // Extract trip from bundle
        mTrip = Parcels.unwrap(getIntent().getParcelableExtra("trip parcel"));

        //set the image for the Trip Details Page
        NetworkUtility networkUtility = new NetworkUtility(getApplicationContext());

        Photo photo = networkUtility.getImageFromTrip(mTrip);
        if (photo != null) {
            ParseFile file = photo.getImage();

            // turn that image parse file into image url and load that image url into the itemView
            Glide.with(getApplicationContext())
                    .load(file.getUrl())
                    .into(ivProfile);
        }

        // set the name of the trip (ONLY FEATURED TRIPS), a user trip does not have this attribute
        tvName.setText(mTrip.getName());
        // set the budget allocated for this trip
        tvBudget.setText("Budget: $" + Integer.toString(mTrip.getBudget()));
        // set the number of guests already allocated to this trip
        tvGuestNumber.setText("Number of Guests: " + Integer.toString(mTrip.getNumGuests()));
        // set the dates assigned to this trip (checkin and checkout)
        tvCheckIn.setText("Checkin : " + mTrip.getCheckin());
        tvCheckOut.setText("Checkout : " + mTrip.getCheckout());
        // set the Tips assigned for this specific trip
        tvTips.setText("Tips: ");
        // could also set tips in the tvLong TextView, not using it for anything else

        // set the list of attractions assigned to this trip, currently only one attraction/event


        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Go to trip building form", Toast.LENGTH_LONG).show();
                // Pass in the parcelable trip into the bundle
                //                Bundle args = new Bundle();
                //                args.putParcelable("featured_trip", Parcels.wrap(mTrip));
                // Create a build fragment instance
                //                BuildFragment buildFragment = new BuildFragment();
                //                buildFragment.setArguments(args);
                // launch that instance on top of our details page
                //                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //                transaction.replace(R.id.rlTripDetails, buildFragment);
                //                transaction.commit();
            }
        });
    }

}
