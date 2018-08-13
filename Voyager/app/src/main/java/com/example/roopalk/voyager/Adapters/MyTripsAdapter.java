package com.example.roopalk.voyager.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.roopalk.voyager.Fragments.BuildFragment;
import com.example.roopalk.voyager.Model.Photo;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;
import com.parse.ParseFile;

import java.util.ArrayList;

// Provide the underlying view for an individual list item.
public class MyTripsAdapter extends RecyclerView.Adapter<MyTripsAdapter.VH> {
    private Activity mContext;
    private ArrayList<Trip> mTrips;

    public MyTripsAdapter(Activity context, ArrayList<Trip> trips) {
        mContext = context;
        if (trips == null) {
            throw new IllegalArgumentException("trips must not be null");
        }
        mTrips = trips;
    }

    // Inflate the view based on the viewType provided.
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);
        return new VH(itemView, mContext);
    }

    // Display data at the specified position
    @Override
    public void onBindViewHolder(final VH holder, int position) {
        NetworkUtility networkUtility = new NetworkUtility(mContext);

        // get current trip that i'm trying to populate itemView with
        Trip trip = mTrips.get(position);

        // get the DestinationName of the trip
        holder.tvName.setText(trip.getDestination());

        // get the image and set it as the background of the cardView
        Photo photo = networkUtility.getImageFromTrip(trip);

        // make sure the photo is not null
        if(photo != null) {
            ParseFile file = photo.getImage();

            // turn that image parsefile into imageurl and load that image url into the itemView
            Glide.with(mContext)
                    .load(file.getUrl())
                    .into(holder.ivProfile);
        }

        // load the name of the Trip's City into the itemView
        holder.tvName.setText(trip.getDestination());
        // load the description (Country Name) of the Trip
        holder.tvDesc.setText(trip.getDestination() + " · ");
        // load the budget (either in dollar amount or dollar signs)
        holder.tvBudget.setText("$" + trip.getBudget());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                //Here goes your desired onClick behaviour. Like:
                //You can change the fragment, something like this, not tested, please correct for your desired output:
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                BuildFragment myFragment = new BuildFragment();
                //Create a bundle to pass data, add data, set the bundle to your fragment and:
                // replaces main_activity and not placeholder so the user doesn't try accessing changing the tab
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_activity, myFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTrips.size();
    }

    // Provide a reference to the views for each trip item
    public class VH extends RecyclerView.ViewHolder {
        final View rootView;
        final ImageView ivProfile;
        final TextView tvName;
        final TextView tvDesc;
        final TextView tvBudget;
        final FrameLayout flGradient;

        public VH(View itemView, final Context context)
        {
            // initialize Views
            super(itemView);
            rootView = itemView;
            ivProfile = (ImageView)itemView.findViewById(R.id.ivProfile);
            flGradient = (FrameLayout) itemView.findViewById(R.id.flGradient);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
            tvBudget = (TextView) itemView.findViewById(R.id.tvBudget);
        }
    }
}
