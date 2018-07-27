package com.example.roopalk.voyager.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.roopalk.voyager.Model.Trip;
import com.example.roopalk.voyager.R;

import java.util.List;

// Provide the underlying view for an individual list item.
public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.VH> {
    private Activity mContext;
    private List<Trip> mTrips;

    public FeaturedAdapter(Activity context, List<Trip> trips) {
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
        Glide.with(mContext).load(trip.getThumbnailDrawable()).centerCrop().into(holder.ivProfile);

        SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                Glide.with(mContext).load(resource).centerCrop().into(holder.ivProfile);
//
                // Set the result as the background color for `R.id.vPalette` view containing the trip's name.
//                Palette palette = Palette.from(resource).generate();
//                Palette.Swatch vibrant = palette.getVibrantSwatch();
//                if (vibrant != null) {
//                    // Set the background color of a layout based on the vibrant color
//                    holder.vPalette.setBackgroundColor(vibrant.getRgb());
//                    // Update the title TextView with the proper text color
//                    holder.tvName.setTextColor(vibrant.getTitleTextColor());
//                }
            }
        };

        // Store the target into the tag for the profile to ensure target isn't garbage collected prematurely
//        holder.ivProfile.setTag(target);

        // Instruct Picasso to load the bitmap into the target defined above
        Glide.with(mContext)
                .load(trip.getThumbnailDrawable())
                .asBitmap()
                .centerCrop()
                .into(target);
    }

    @Override
    public int getItemCount() {
        return mTrips.size();
    }

    // Provide a reference to the views for dieach contact item
    public class VH extends RecyclerView.ViewHolder {
        final View rootView;
        final ImageView ivProfile;
        final TextView tvName;
//        final View vPalette;
        final FrameLayout flGradient;

        public VH(View itemView, final Context context) {
            super(itemView);
            rootView = itemView;
            ivProfile = (ImageView)itemView.findViewById(R.id.ivProfile);
            flGradient = (FrameLayout) itemView.findViewById(R.id.flGradient);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
//            vPalette = itemView.findViewById(R.id.vPalette);

            // Navigate to contact details activity on click of card view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Trip trip = (Trip)v.getTag();
                    if (trip != null) {
                        // Fire an intent when a trip is selected
//                        Intent intent = new Intent(mContext, DetailsActivity.class);
//                        intent.putExtra(DetailsActivity.EXTRA_TRIP, trip);
                        // Pass trip object in the bundle and populate details activity.
//                        mContext.startActivity(intent);

                    }
                }
            });
        }
    }
}
