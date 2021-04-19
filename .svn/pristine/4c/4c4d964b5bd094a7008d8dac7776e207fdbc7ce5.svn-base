package com.goodkredit.myapplication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.fragments.soa.Billings;
import com.goodkredit.myapplication.fragments.soa.SubscriberPaymentsFragment;
import com.goodkredit.myapplication.utilities.V2Utils;


public class StatementOfAccountFragment extends BaseFragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_notification_0).setVisible(false);
        menu.findItem(R.id.action_notification_1).setVisible(false);
        menu.findItem(R.id.action_notification_2).setVisible(false);
        menu.findItem(R.id.action_notification_3).setVisible(false);
        menu.findItem(R.id.action_notification_4).setVisible(false);
        menu.findItem(R.id.action_notification_5).setVisible(false);
        menu.findItem(R.id.action_notification_6).setVisible(false);
        menu.findItem(R.id.action_notification_7).setVisible(false);
        menu.findItem(R.id.action_notification_8).setVisible(false);
        menu.findItem(R.id.action_notification_9).setVisible(false);
        menu.findItem(R.id.action_notification_9plus).setVisible(false);
        menu.findItem(R.id.action_process_queue).setVisible(false);
        menu.findItem(R.id.sortitem).setVisible(false);
        menu.findItem(R.id.summary).setVisible(false);
        menu.findItem(R.id.group_voucher).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate tab_layout and setup Views.
        View rootView = inflater.inflate(R.layout.fragment_soa, container, false);
        tabLayout = rootView.findViewById(R.id.tabs);
        viewPager = rootView.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(1);

        //Set an Adapter for the View Pager
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        // The setupWithViewPager dose't works without the runnable .
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return rootView;
    }

  public class MyAdapter extends FragmentPagerAdapter {

        MyAdapter(FragmentManager fm) {
            super(fm);
        }

      @NonNull
      @Override
      public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return new SubscriberPaymentsFragment();
                case 0:
                    return new Billings();
            }
            return null;
        }

        @Override
        public int getCount() {
            return int_items;
        }

        // This method returns the title of the tab according to the position.

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_REGULAR, "Billing");
                case 1:
                    return V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_REGULAR, "Payments");
            }
            return null;
        }
    }
}
