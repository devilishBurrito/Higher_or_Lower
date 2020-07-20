package com.example.higherorlower;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class PagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch  (position){
            case 0:
                return new HighLowFragment();

            case 1:
                return new BlankTab1Fragment();

            case 2:
                return new BlankTab2Fragment();
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
