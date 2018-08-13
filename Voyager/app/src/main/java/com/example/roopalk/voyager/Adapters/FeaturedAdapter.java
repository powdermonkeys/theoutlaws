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

import com.brandongogetap.stickyheaders.exposed.StickyHeaderHandler;
import com.bumptech.glide.Glide;
import com.example.roopalk.voyager.Fragments.TripDetailsFragment;
import com.example.roopalk.voyager.Model.Photo;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;
import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.List;

// Provide the underlying view for an individual list item.
public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.VH> implements StickyHeaderHandler {
    private Activity mContext;
    private ArrayList<Trip> mTrips;

    public FeaturedAdapter(Activity context, ArrayList<Trip> trips) {
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

        // get the current trip that I'm trying to populate the itemView with
        Trip trip = mTrips.get(position);

        Photo photo = networkUtility.getImageFromTrip(trip);
        if (photo != null) {
            ParseFile file = photo.getImage();

            // turn that image parsefile into imageurl and load that image url into the itemView
            Glide.with(mContext)
                    .load(file.getUrl())
                    .into(holder.ivProfile);
        }

        // load the name of the Trip into the itemView
        holder.tvName.setText(trip.getName());
        // load the description (Country Name) of the Trip
        holder.tvDesc.setText(trip.getDestination() + " · ");
        // load the budget (either in dollar amount or dollar signs)
        holder.tvBudget.setText("$" + trip.getBudget());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                // TODO: Fix fragment staying on top
//                TODO: Solution to launch activity on top instead of fragment
                // switch to an activity instead of a Fragment
//                Intent intent = new Intent(activity, TripDetailsActivity.class);
//                startActivity(intent);
                TripDetailsFragment myFragment = new TripDetailsFragment();
                //Create a bundle to pass data, add data, set the bundle to your fragment and:
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_activity, myFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTrips.size();
    }

    @Override
    public List<Trip> getAdapterData() {
        return mTrips;
    }

    // Provide a reference to the views for each trip item
    public class VH extends RecyclerView.ViewHolder {
        final View rootView;
        final ImageView ivProfile;
        final TextView tvName;
        final TextView tvDesc;
        final TextView tvBudget;
        final FrameLayout flGradient;

        public VH(View itemView, final Context context) {
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
