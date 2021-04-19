package com.goodkredit.myapplication.activities.csbrewards;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.MenuItem;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.fragments.rewards.CSBChangeMobileFragment;
import com.goodkredit.myapplication.fragments.rewards.CSBRewardsFragment;

public class CSBSettingsCommonActivity extends BaseActivity {

    private static final String KEY_VIEW_ID = "view_id";
    private static final String KEY_APP_DETAILS = "app_details";

    public static final int CSB_REWARDS_CHANGE_MOBILE = 111;
    public static final int CSB_REWARDS_CONTACT_US = 222;
    public static final int CSB_REWARDS_ABOUT_US = 333;

    private int mID;


    public static void start(Context context, int ID, GKService service) {
        Intent intent = new Intent(context, CSBSettingsCommonActivity.class);
        intent.putExtra(KEY_VIEW_ID, ID);
        intent.putExtra(KEY_APP_DETAILS, service);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_rewards);
        init();

    }

    private void init() {

        mID = getIntent().getIntExtra(KEY_VIEW_ID, 0);
        setupToolbar();
        displayView(mID);

    }

    private void displayView(int id) {
        try {
            Fragment fragment = null;
            String title = "Claim Reward";
            switch (id) {
                case CSB_REWARDS_CHANGE_MOBILE:
                    fragment = CSBChangeMobileFragment.newInstance();
                    title = "Change Mobile Number";
                    break;
                case CSB_REWARDS_CONTACT_US:
                    fragment = CSBRewardsFragment.newInstance();
                    title = "Contact Us";
                    break;
                case CSB_REWARDS_ABOUT_US:
                    fragment = CSBRewardsFragment.newInstance();
                    title = "About Us";
                    break;
            }

            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.claim_rewards_container, fragment);
                fragmentTransaction.commit();

                // set the toolbar title
                getSupportActionBar().setTitle(title);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
