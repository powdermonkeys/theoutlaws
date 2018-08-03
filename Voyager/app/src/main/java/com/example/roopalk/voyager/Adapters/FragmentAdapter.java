package com.example.roopalk.voyager.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.roopalk.voyager.Fragments.BuildFragment;
import com.example.roopalk.voyager.Fragments.FeaturedTripsFragment;

public class FragmentAdapter extends FragmentPagerAdapter{
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Featured",  "Your Trips" };
    private Context context;

    public FragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    // uses a switch to get each fragment display
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return new FeaturedTripsFragment();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return new BuildFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        // Generate title based on item position
        return tabTitles[position];
    }
}