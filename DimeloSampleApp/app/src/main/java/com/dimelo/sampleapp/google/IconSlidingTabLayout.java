/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dimelo.sampleapp.google;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dimelo.sampleapp.R;
import com.dimelo.sampleapp.ViewPagerIconAndTextAdapter;

/**
 * To be used with ViewPager to provide a tab indicator component which give constant feedback as to
 * the user's scroll progress.
 * <p>
 * To use the component, simply add it to your view hierarchy. Then in your
 * {@link android.app.Activity} or {@link android.support.v4.app.Fragment} call
 * {@link #setViewPager(ViewPager)} providing it the ViewPager this layout is being used for.
 * <p>
 * The colors can be customized in two ways. The first and simplest is to provide an array of colors
 * via {@link #setSelectedIndicatorColors(int...)}. The
 * alternative is via the {@link TabColorizer} interface which provides you complete control over
 * which color is used for any individual position.
 * <p>
 * The views used as tabs can be customized by calling {@link #setCustomTabView(int, int)},
 * providing the layout ID of your custom layout.
 */
public class IconSlidingTabLayout extends SlidingTabLayout {

    public IconSlidingTabLayout(Context context) {
        this(context, null);
    }

    public IconSlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconSlidingTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Sets the associated view pager. Note that the assumption here is that the pager content
     * (number of tabs and tab titles) does not change after this call has been made.
     */
    public void setViewPager(ViewPager viewPager) {
        mTabStrip.removeAllViews();

        mViewPager = viewPager;
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener(){
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    updateIcons();
                }
            });
            populateTabStrip();
        }
    }


    @Override
    protected void populateTabStrip() {
        final PagerAdapter adapter = mViewPager.getAdapter();
        final View.OnClickListener tabClickListener = new TabClickListener();

        for (int i = 0; i < adapter.getCount(); i++) {
            View tabView = null;
            TextView tabTitleView = null;
            ImageView iconImageView = null;


            if (mTabViewLayoutId == 0){
                setCustomTabView(R.layout.icon_sliding_tab, R.id.tab_layout_title);
            }

            // If there is a custom tab view layout id set, try and inflate it
            tabView = LayoutInflater.from(getContext()).inflate(R.layout.icon_sliding_tab, mTabStrip,
                    false);
            tabTitleView = (TextView) tabView.findViewById(mTabViewTextViewId);

            TypedValue outValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground,
                    outValue, true);
            tabTitleView.setBackgroundResource(outValue.resourceId);
            tabTitleView.setAllCaps(true);

            int padding = (int) (TAB_VIEW_PADDING_DIPS * getResources().getDisplayMetrics().density);
            tabTitleView.setPadding(padding, padding, padding, padding);

            iconImageView = (ImageView) tabView.findViewById(R.id.tab_layout_icon);

//            if (tabView == null) {
//                tabView = createDefaultTabView(getContext());
//            }
//
//            if (tabTitleView == null && TextView.class.isInstance(tabView)) {
//                tabTitleView = (TextView) tabView;
//            }

            if (mDistributeEvenly) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                lp.width = 0;
                lp.weight = 1;
            }

            tabTitleView.setText(adapter.getPageTitle(i));
            // Not very beautifull, can be improved in the future
            if (adapter instanceof ViewPagerIconAndTextAdapter){
//                int id = ((ViewPagerIconAndTextAdapter)adapter).getPageIconId(i);
//                Drawable drawable = getContext().getResources().getDrawable(id);
//                DrawableCompat.setTint(drawable, Color.GREEN);
//                iconImageView.setImageDrawable(drawable);
                updateIcon(iconImageView, i);
            }
            tabView.setOnClickListener(tabClickListener);
            String desc = mContentDescriptions.get(i, null);
            if (desc != null) {
                tabView.setContentDescription(desc);
            }

            mTabStrip.addView(tabView);
            if (i == mViewPager.getCurrentItem()) {
                tabView.setSelected(true);
            }
            tabTitleView.setTextColor(getResources().getColorStateList(R.color.text_slider_selector));
        }
    }


    private void updateIcons(){
        for (int i = 0; i < mTabStrip.getChildCount(); i++) {
            View v = mTabStrip.getChildAt(i).findViewById(R.id.tab_layout_icon);
            if (v != null) {
                updateIcon((ImageView)v, i);
            }

        }
    }

    // Can be improved by storing and reusing drawable (less allocation)
    private void updateIcon(ImageView iconImageView, int position){
        if (mViewPager == null){
            return ;
        }
        int icon_id;
        ViewPagerIconAndTextAdapter adapter = (ViewPagerIconAndTextAdapter) mViewPager.getAdapter();
        if (position == mViewPager.getCurrentItem()) {
            icon_id = adapter.getSelectedPageIconId(position);
        }
        else{
            icon_id = adapter.getPageIconId(position);
        }
        Resources resources = getContext().getResources();
        Drawable icon = DrawableCompat.wrap(resources.getDrawable(icon_id));
        DrawableCompat.setTint(icon, resources.getColor(R.color.accent));
        iconImageView.setImageDrawable(icon);
    }

}
