package com.example.roopalk.voyager.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.roopalk.voyager.Fragments.TripDetailsFragment;
import com.example.roopalk.voyager.Model.Photo;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;
import com.parse.ParseException;

import java.util.ArrayList;

// Provide the underlying view for an individual list item.
public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.VH>{
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
        // current trip

        Log.d("APP_DEBUG", "Bound trips : " + mTrips.size());
        Trip trip = mTrips.get(position);
        Photo p = new Photo();

//        holder.rootView.setTag(trip);
//        Glide.with(mContext).load(trip.getThumbnailDrawable())
//                .centerCrop()
//                .into(holder.ivProfile);

//        holder.tvName.setText(trip.getTripName());
//        holder.tvDesc.setText(trip.getTripDescription());
//        holder.tvBudget.setText(trip.getBudget());

//        Glide.with(mContext)
//                .load(trip.getImage().getUrl())
//                .centerCrop()
//                .into(holder.ivProfile);
        // function from the trip model, gets image of that trip (Photo model that points to trip?)

        NetworkUtility networkUtility = new NetworkUtility(mContext);
        try
        {
            //trips here
            networkUtility.getTripImage(trip);
            p = networkUtility.getPhotos().get(0);


            Log.d("APP_DEBUG", "Loaded photos : " + networkUtility.getPhotos().size());

            Glide.with(mContext)
                    .load(p.getImage().getUrl())
                    .into(holder.ivProfile);

            Log.d("APP_DEBUG", "trip : " + mTrips.get(position).getObjectId() + " : " + p.getImage().getName() + ": " + p.getImage().getUrl());

        } catch (ParseException e) {
            e.printStackTrace();
        }


//        Similar to how it was done in AttractionAdapter




//        Log.e("FeaturedAdapter",trip.getThumbnailDrawable());


//        recyclerView.addItemDecoration(sectionItemDecoration);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Here goes your desired onClick behaviour. Like:
                Toast.makeText(view.getContext(), "You have clicked " + view.getId(), Toast.LENGTH_SHORT).show(); //you can add data to the tag of your cardview in onBind... and retrieve it here with with.getTag().toString()..
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                TripDetailsFragment myFragment = new TripDetailsFragment();
                //Create a bundle to pass data, add data, set the bundle to your fragment and:
                // TODO: Fix fragment staying on top
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
