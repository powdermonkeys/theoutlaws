package com.example.roopalk.voyager;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roopalk.voyager.Model.Attraction;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private static ArrayList<Attraction> mAttractions = new ArrayList<>();
    static Context context;



    public MyAdapter (ArrayList<Attraction> attractions){
        mAttractions = attractions;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView tvName;
        public TextView tvDescription;
        public TextView tvTime;
        public TextView est;
        public TextView tvPrice;
        public CardView cardView;


        public ViewHolder(CardView v) {
            super(v);


            picture = (ImageView )itemView.findViewById(R.id.picture);
            tvName = (TextView ) itemView.findViewById(R.id.tvName);
            tvDescription = (TextView)itemView.findViewById(R.id.tvDescription);
            tvTime = (TextView)itemView.findViewById(R.id.tvTime);
            est = (TextView) itemView.findViewById(R.id.est);
            tvPrice = (TextView) itemView.findViewById(R.id.price);
            //cardView = itemView.findViewById(R.id.cardView);
            cardView = (CardView) v;

        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
       View tripView = inflater.inflate(R.layout.item_attraction, parent, false);

        ViewHolder viewHolder = new ViewHolder((CardView) tripView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Attraction attraction = mAttractions.get(position);
        //CardView cardView = holder.cardView;

        holder.tvName.setText(attraction.getAttractionName().toString());
        holder.tvDescription.setText(attraction.getAttractionDescription().toString());
        holder.tvPrice.setText(attraction.getEstimatedPrice().toString());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mAttractions.size();
    }

    public void clear() {
        mAttractions.clear();
        notifyDataSetChanged();
    }
}