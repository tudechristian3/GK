package com.goodkredit.myapplication.fragments.transactions;

import android.os.Bundle;
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
import com.goodkredit.myapplication.common.CommonVariables;


public class FragmentTransactions extends BaseFragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 4;
    CommonVariables cv;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        try{
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
        }catch (Exception e){}

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate tab_layout and setup Views.
        View rootView = inflater.inflate(R.layout.fragment_transactions, container, false);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);

        //Set an Adapter for the View Pager
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        //       viewPager.setCurrentItem(1, true);

        //   The setupWithViewPager dose't works without the runnable .
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return rootView;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        // Return fragment with respect to Position .


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Borrowings();
                case 2:
                    return new Consummated();
                case 3:
                    return new Transferred();
                case 1:
                    return PurchasesFragment.newInstance();
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
                    return "Borrowings";
                case 2:
                    return "Consummation";
                case 3:
                    return "Transfers";
                case 1:
                    return "Purchases";
            }
            return null;
        }
    }
}
