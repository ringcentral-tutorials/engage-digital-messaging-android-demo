package com.dimelo.sampleapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public abstract class ViewPagerIconAndTextAdapter extends FragmentPagerAdapter {

    protected String mTitles[];
    protected int mIconIds[];
    protected int mSelectedIconIds[];

    public ViewPagerIconAndTextAdapter(FragmentManager fm, String titles[], int iconIds[], int selectedIconIds[]) {
        super(fm);
        this.mTitles = titles;
        this.mIconIds = iconIds;
        this.mSelectedIconIds = selectedIconIds;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    public int getPageIconId(int position) {
        return mIconIds[position];
    }

    public int getSelectedPageIconId(int position) {
        return mSelectedIconIds[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return mTitles.length;
    }
}