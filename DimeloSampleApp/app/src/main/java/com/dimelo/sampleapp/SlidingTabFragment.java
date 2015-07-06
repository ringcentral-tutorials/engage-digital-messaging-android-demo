package com.dimelo.sampleapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dimelo.sampleapp.chats.TabBank;
import com.dimelo.sampleapp.chats.TabStart;
import com.dimelo.sampleapp.chats.TabSupport;
import com.dimelo.sampleapp.chats.TabTelecom;
import com.dimelo.sampleapp.google.SlidingTabLayout;

public class SlidingTabFragment extends Fragment {

    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.slider_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

            String[] tabTitles = {"Start", "Bank", "Telecom", "Support"};
            int[] iconIds = {
                    R.drawable.start_icon,
                    R.drawable.bank_icon,
                    R.drawable.telco_icon,
                    R.drawable.support_icon};
            int[] selectedIconIds = {
                    R.drawable.start_icon_selected,
                    R.drawable.bank_icon_selected,
                    R.drawable.telco_icon_selected,
                    R.drawable.support_icon_selected};

            // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
            mViewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), tabTitles, iconIds, selectedIconIds);

            // Assigning ViewPager View and setting the adapter
            mViewPager = (ViewPager) view.findViewById(R.id.pager);
            mViewPager.setAdapter(mViewPagerAdapter);
//            mViewPager.setOffscreenPageLimit(3);

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

    public boolean isAnyChatDisplayed(){
        if (mViewPagerAdapter != null) {
            return mViewPagerAdapter.isChatDisplayed(mViewPager.getCurrentItem());
        }
        return false;
    }

    public class ViewPagerAdapter extends ViewPagerIconAndTextAdapter {

        TabStart start;
        TabBank bank;
        TabTelecom telecom;
        TabSupport support;

        public ViewPagerAdapter(FragmentManager fm, String titles[], int iconIds[], int selectedIconIds[]) {
            super(fm, titles, iconIds, selectedIconIds);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                start = new TabStart();
                return start;
            }
            else if(position == 1) {
                bank = new TabBank();
                return bank;
            }
            else if(position == 2) {
                telecom = new TabTelecom();
                return telecom;
            }
            else if(position == 3) {
                support = new TabSupport();
                return support;
            }
            else{ // Not suppose to happen
                return new TabStart();
            }
        }

        boolean isChatDisplayed(int position){
            boolean isAnyChatDisplayed = false;

            if (position == 0 && start != null && start.isChatDisplayed()){
                isAnyChatDisplayed = true;
            }
            else if (position == 1 && bank != null && bank.isChatDisplayed()){
                isAnyChatDisplayed = true;
            }
            else if (position == 2 && telecom != null && telecom.isChatDisplayed()){
                isAnyChatDisplayed = true;
            }
            else if (position == 3 && support != null && support.isChatDisplayed()){
                isAnyChatDisplayed = true;
            }
            return isAnyChatDisplayed;
        }

    }

}
