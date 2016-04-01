package com.dimelo.sampleapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dimelo.sampleapp.chats.TabBank;
import com.dimelo.sampleapp.chats.TabStart;
import com.dimelo.sampleapp.chats.TabSupport;
import com.dimelo.sampleapp.chats.TabTelecom;
import com.dimelo.sampleapp.google.SlidingTabLayout;

import com.dimelo.dimelosdk.main.Dimelo;

public class SlidingTabFragment extends Fragment {

    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;

    private TabBank bank;
    private TabTelecom telecom;
    private TabSupport support;
    private TabStart start;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.slider_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        String[] tabTitles = {"Bank", "Telecom", "Support", "Test"};
        int[] iconIds = {
                R.drawable.bank_icon,
                R.drawable.telco_icon,
                R.drawable.support_icon,
                R.drawable.start_icon};
        int[] selectedIconIds = {
                R.drawable.bank_icon_selected,
                R.drawable.telco_icon_selected,
                R.drawable.support_icon_selected,
                R.drawable.start_icon_selected};

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        mViewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), tabTitles, iconIds, selectedIconIds);
//            mViewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), tabTitles, iconIds, selectedIconIds);

        // Assigning ViewPager View and setting the adapter
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);

        // Assiging the Sliding Tab Layout View
        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(false); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.accent);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewPager = null;
        mViewPagerAdapter = null;
        bank = null;
        telecom = null;
        support = null;
        start = null;
    }

    private Fragment getFragment(int position) {
        if (position == 0) {
            bank = new TabBank();
            return bank;
        } else if (position == 1) {
            telecom = new TabTelecom();
            return telecom;
        } else if (position == 2) {
            support = new TabSupport();
            return support;
        } else if (position == 3) {
            start = new TabStart();
            return start;
        } else { // Not suppose to happen
            return new TabBank();
        }
    }


    // delegate management of back to tab if need be
    public boolean isHandlingBack() {
        if (mViewPagerAdapter != null) {
            return mViewPagerAdapter.isHandlingBack(mViewPager.getCurrentItem());
        }
        return false;
    }

    public class ViewPagerAdapter extends ViewPagerIconAndTextAdapter {

        public ViewPagerAdapter(FragmentManager fm, String titles[], int iconIds[], int selectedIconIds[]) {
            super(fm, titles, iconIds, selectedIconIds);
        }

        public Object instantiateItem(ViewGroup container, int position) {
            Fragment f = (Fragment)super.instantiateItem(container, position);
            if (f instanceof TabBank){
                bank = (TabBank)f;
            }
            else if (f instanceof TabTelecom){
                telecom = (TabTelecom)f;
            }
            else if (f instanceof TabSupport){
                support = (TabSupport)f;
            }
            else if (f instanceof TabStart){
                start = (TabStart)f;
            }
            return f;
        }


        @Override
        public Fragment getItem(int position) {
            return getFragment(position);
        }

        boolean isHandlingBack(int position) {
            boolean isHandlingBack = false;


            if (position == 0 && bank != null && bank.isHandlingBack()) {
                isHandlingBack = true;
            } else if (position == 1 && telecom != null && telecom.isHandlingBack()) {
                isHandlingBack = true;
            } else if (position == 2 && support != null && support.isHandlingBack()) {
                isHandlingBack = true;
            } else if (position == 3 && start != null && start.isHandlingBack()) {
                isHandlingBack = true;
            }
            return isHandlingBack;
        }

    }

}
