package com.example.roopalk.voyager.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter{
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Featured", "User Trips", "Your Trips" };
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
                return new AddingAttractionFragment();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return CalendarFragment.newInstance("STARTDATE", "ENDDATE");
            case 2: // Fragment # 1 - This will show SecondFragment
                return BuildFragment.newInstance(2, "Page # 3");
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}