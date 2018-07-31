package com.example.roopalk.voyager.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.roopalk.voyager.Fragments.onFragmentInteractionListener;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.Photo;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;
import com.parse.ParseException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HorizontalAttractionAdapter  extends RecyclerView.Adapter<HorizontalAttractionAdapter.ViewHolder>
{

    ArrayList<Attraction> chosenAttractions;
    Context context;

    onFragmentInteractionListener listener;

    public HorizontalAttractionAdapter(ArrayList<Attraction> chosenAttractions, onFragmentInteractionListener listener)
    {
        this.chosenAttractions = chosenAttractions;
        this.listener = listener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View attractionView = inflater.inflate(R.layout.item_attraction_horizontal, parent, false);

        ViewHolder viewHolder = new HorizontalAttractionAdapter.ViewHolder(attractionView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HorizontalAttractionAdapter.ViewHolder holder, int position)
    {
        Attraction attraction = chosenAttractions.get(position);
        Photo photo = new Photo();

        NetworkUtility networkUtility = new NetworkUtility(context);

        try
        {
            networkUtility.getImagesFromAttraction(attraction);
            photo = networkUtility.getPhotos().get(0);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        holder.tvAttractionName.setText(attraction.getAttractionName());

        Glide.with(context)
             .load(photo.getImage().getUrl())
             .into(holder.ivAttractionPhoto);
    }

    @Override
    public int getItemCount()
    {
        return chosenAttractions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.ivAttractionPhoto) ImageView ivAttractionPhoto;
        @BindView(R.id.tvAttractionName) TextView tvAttractionName;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();

            Attraction attraction = chosenAttractions.get(position);
            listener.moveToAddEventPage(attraction);
        }
    }
}
