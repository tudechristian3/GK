package com.goodkredit.myapplication.activities.billspayment;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.fragments.billspayment.BillsPaymentBillerFragment;
import com.goodkredit.myapplication.fragments.billspayment.BillsPaymentBorrowerBillerList;
import com.goodkredit.myapplication.fragments.billspayment.BillsPaymentCategoriesFragment;
import com.goodkredit.myapplication.utilities.V2Utils;

public class BillsPaymentActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int int_items = 3;
    private String PROCESS = "";
    //SERVICES
    private GKService gkservice;
    private Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_payment);

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);


        Bundle b = getIntent().getExtras();
        PROCESS = b.getString("PROCESS").toString();

        if(PROCESS.equals("VIEWBORROWERBILLER")) {
            String servicecode = getIntent().getStringExtra("ServiceCode");
            args = new Bundle();
            args.putString("ServiceCode", servicecode);
        }
        else {
            gkservice = getIntent().getParcelableExtra("GKSERVICE_OBJECT");
            args = new Bundle();
            args.putString("ServiceCode", gkservice.getServiceCode());
        }


        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        //   The setupWithViewPager dose't works without the runnable .
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);

                if (PROCESS.equals("VIEWBORROWERBILLER")) {
                    tabLayout.getTabAt(1).select();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {

            if (PROCESS.equals("VIEWBORROWERBILLER")) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                CommonVariables.VOUCHERISFIRSTLOAD = true;
                startActivity(intent);
            } else {
                finish();
            }


        }
        return super.onOptionsItemSelected(item);
    }

    private class MyAdapter extends FragmentPagerAdapter {

        private MyAdapter(FragmentManager fm) {
            super(fm);
        }

        // Return fragment with respect to Position .
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch (position) {
                case 0:
                    fragment = new BillsPaymentBillerFragment();
                    if (args != null) {
                        fragment.setArguments(args);
                    }
                    return fragment;
                case 1:
                    fragment = new BillsPaymentBorrowerBillerList();
                    if (args != null) {
                        fragment.setArguments(args);
                    }
                    return fragment;
                case 2:
                    fragment = new BillsPaymentCategoriesFragment();
                    if (args != null) {
                        fragment.setArguments(args);
                    }
                    return fragment;
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
                    return "All Billers";
                case 1:
                    return "My Billers";
                case 2:
                    return "Categories";
            }
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(tabLayout != null && viewPager != null &&
                PROCESS != null) {
            //init();
        }
    }
}




