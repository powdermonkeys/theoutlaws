package com.example.roopalk.voyager.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roopalk.voyager.Fragments.onFragmentInteractionListener;
import com.example.roopalk.voyager.GlideApp;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.Photo;
import com.example.roopalk.voyager.NetworkUtility;
import com.example.roopalk.voyager.R;
import com.parse.ParseException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.ViewHolder>
{

    ArrayList<Attraction> mAttractions;
    Context context;
    private onFragmentInteractionListener mListener;

    public AttractionAdapter(ArrayList<Attraction> attractions, onFragmentInteractionListener listener)
    {
        mAttractions = attractions;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View attractionView = inflater.inflate(R.layout.item_attraction, parent, false);
        ViewHolder viewHolder = new ViewHolder(attractionView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AttractionAdapter.ViewHolder holder, int position)
    {
        Attraction currentAttraction = mAttractions.get(position);
        Photo p = new Photo();

        NetworkUtility networkUtility = new NetworkUtility(context);
        try
        {
            networkUtility.getImagesFromAttraction(currentAttraction);
            p = networkUtility.getPhotos().get(0);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        holder.tvName.setText(currentAttraction.getAttractionName());
        holder.tvDescription.setText(currentAttraction.getAttractionDescription());
        holder.tvTime.setText(currentAttraction.getEstimatedTime());

        GlideApp.with(context)
                .load(p.getImage().getUrl())
                .into(holder.ivPicture);
    }

    @Override
    public int getItemCount()
    {
        return mAttractions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tvName) TextView tvName;
        @BindView(R.id.picture) ImageView ivPicture;
        @BindView(R.id.tvDescription) TextView tvDescription;
        @BindView(R.id.tvTime) TextView tvTime;

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

            Attraction attraction = mAttractions.get(position);

            if(position != RecyclerView.NO_POSITION)
            {
                Log.i("AttractionAdapter", "Moving to Details Page now...");
                mListener.moveToDetailsPage(attraction);
            }
        }
    }

    public void clear()
    {
        mAttractions.clear();
        notifyDataSetChanged();
    }
}