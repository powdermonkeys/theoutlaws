package com.example.roopalk.voyager.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.roopalk.voyager.R;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter
{
    List<Integer> images;
    Context context;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, List<Integer> images)
    {
        this.images = images;
        this.context = context;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount()
    {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        View view = inflater.inflate(R.layout.viewpager_item, container, false);

        ImageView ivAttractionPhoto = view.findViewById(R.id.ivAttractionPhoto);
        ivAttractionPhoto.setImageResource(images.get(position));

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        View view = inflater.inflate(R.layout.viewpager_item, container, false);
        container.removeView(view);
    }
}
