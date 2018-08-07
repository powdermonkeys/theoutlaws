package com.example.roopalk.voyager.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.roopalk.voyager.Fragments.FeaturedTripsFragment;
import com.example.roopalk.voyager.Fragments.ProfileFragment;
import com.example.roopalk.voyager.Fragments.YourTripsFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Featured", "My Trips", "My Profile"};
    Context context;

    public FragmentAdapter(FragmentManager fm, Context context)
    {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount () {
        return PAGE_COUNT;
    }

    // uses a switch to get each fragment display
    @Override
    public Fragment getItem ( int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return FeaturedTripsFragment.newInstance(0, "Page 1");
            case 1:
                return YourTripsFragment.newInstance(1, "Page 2");
            case 2:
                return ProfileFragment.newInstance(2, "Page 3");
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle ( int position)
    {
        // Generate title based on item position
        return tabTitles[position];
    }
}