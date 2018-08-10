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

import com.example.roopalk.voyager.Fragments.BuildFragment;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.R;

import java.util.ArrayList;

// Provide the underlying view for an individual list item.
public class MyTripsAdapter extends RecyclerView.Adapter<MyTripsAdapter.VH>
{
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
        Trip trip = mTrips.get(position);
        holder.rootView.setTag(trip);
//        holder.tvName.setText("Insert Your Dream Place Here");
//        Glide.with(mContext).load(trip.getThumbnailDrawable()).centerCrop().into(holder.ivProfile);
        //        Glide.with(mContext).load(trip.getImage.centerCrop().into(holder.ivProfile);
        // function from the trip model, gets image of that trip

//        Log.e("FeaturedAdapter",trip.getThumbnailDrawable() + "");


//        Glide.with(mContext)
//                .load(trip.getThumbnailDrawable())
//                .asBitmap()
//                .centerCrop()
//                .into(target);

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
        final FrameLayout flGradient;

        public VH(View itemView, final Context context)
        {
            super(itemView);
            rootView = itemView;
            ivProfile = (ImageView)itemView.findViewById(R.id.ivProfile);
            flGradient = (FrameLayout) itemView.findViewById(R.id.flGradient);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
        }
    }
}
