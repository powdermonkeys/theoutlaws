package com.example.roopalk.voyager.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.roopalk.voyager.Fragments.AttractionDetailsFragment;
import com.example.roopalk.voyager.Fragments.onFragmentInteractionListener;
import com.example.roopalk.voyager.Model.Attraction;
import com.example.roopalk.voyager.Model.BudgetBar;
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
    BudgetBar budgetBar;

    ArrayList<Attraction> chosenAttractions;

    public AttractionAdapter(ArrayList<Attraction> attractions, onFragmentInteractionListener listener, BudgetBar budgetBar) {
        mAttractions = attractions;
        mListener = listener;
        this.budgetBar = budgetBar;
    }

    public AttractionAdapter(ArrayList<Attraction> attractions, BudgetBar budgetBar)
    {
        mAttractions = attractions;
        this.budgetBar = budgetBar;
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
    public void onBindViewHolder(final AttractionAdapter.ViewHolder holder, int position)
    {
        Attraction currentAttraction = mAttractions.get(position);

        holder.tvName.setText(currentAttraction.getAttractionName());
        holder.tvDescription.setText(currentAttraction.getAttractionDescription());
        holder.tvTime.setText(currentAttraction.getEstimatedTime());

        NetworkUtility networkUtility = new NetworkUtility(context);
        try
        {
            networkUtility.getImages(currentAttraction);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(context)
                .load(networkUtility.getImageURLs().get(0))
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
            Log.i("List", "works");
            Attraction attraction = mAttractions.get(position);

            if(position != RecyclerView.NO_POSITION)
            {
                moveToDetailsPage(attraction, budgetBar);
            }
        }
    }

    public void clear()
    {
        mAttractions.clear();
        notifyDataSetChanged();
    }

    private void moveToDetailsPage(Attraction attraction, BudgetBar budgetBar)
    {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AttractionDetailsFragment attractionDetailsFragment = AttractionDetailsFragment.newInstance(attraction, budgetBar);
        attractionDetailsFragment.show(fragmentTransaction, "fragment_attraction_details");
    }
}