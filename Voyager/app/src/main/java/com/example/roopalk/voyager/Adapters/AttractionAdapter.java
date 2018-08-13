package com.example.roopalk.voyager.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
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

    private ArrayList<Attraction> mAttractions;

    public ArrayList<Attraction> chosenAttractions;

    private Context context;
    private BudgetBar budgetBar;

    public AttractionAdapter(ArrayList<Attraction> attractions, BudgetBar budgetBar) {
        mAttractions = attractions;
        this.budgetBar = budgetBar;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View attractionView = inflater.inflate(R.layout.item_attraction, parent, false);
        return new ViewHolder(attractionView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position)
    {
        Attraction currentAttraction = mAttractions.get(position);

        holder.tvName.setText(currentAttraction.getAttractionName());
        holder.tvDescription.setText(currentAttraction.getAttractionDescription());
        holder.tvTime.setText(currentAttraction.getEstimatedTime());

        setPriceOnCardView(holder, position);

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
        @BindView(R.id.price) TextView price;

        ViewHolder(View itemView)
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

            moveToDetailsPage(attraction, budgetBar);
        }
    }

    private void moveToDetailsPage(Attraction attraction, BudgetBar budgetBar)
    {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AttractionDetailsFragment attractionDetailsFragment = AttractionDetailsFragment.newInstance(attraction, budgetBar);
        attractionDetailsFragment.show(fragmentTransaction, "fragment_attraction_details");
    }

    private void setPriceOnCardView(ViewHolder viewHolder, int position)
    {
        Attraction temp = mAttractions.get(position);
        int price = temp.getEstimatedPrice();

        if(price > 0 && price < 10)
        {
            viewHolder.price.setText("$");
        }
        else if(price > 10 && price < 20)
        {
            viewHolder.price.setText("$$");
        }
        else if(price > 20)
        {
            viewHolder.price.setText("$$$");
        }
    }
}