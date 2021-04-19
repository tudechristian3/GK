package com.goodkredit.myapplication.activities.csbrewards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.fragments.rewards.CSBRewardsFragment;

/**
 * Created by Ban_Lenovo on 11/27/2017.
 */

public class CSBRewardsActivity extends BaseActivity {

    public static final String KEY_PROVIDER_ID = "provider_id";

    public static final int CSB_REWARDS = 100;

    private GKService mService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_rewards);

        setupToolbar();

        int provider_id = getIntent().getIntExtra(KEY_PROVIDER_ID, -1);
        displayView(provider_id);

        mService = getIntent().getParcelableExtra(GKService.KEY_SERVICE_OBJ);
    }

    private void displayView(int provider_id) {
        try {
            Fragment fragment = null;
            String title = "Claim Reward";
            switch (provider_id) {
                case CSB_REWARDS:
                    fragment = CSBRewardsFragment.newInstance();
                    title = "CSB Rewards";
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

    public static void start(Context context, int provider_id, GKService service) {
        Intent intent = new Intent(context, CSBRewardsActivity.class);
        intent.putExtra(KEY_PROVIDER_ID, provider_id);
        intent.putExtra(GKService.KEY_SERVICE_OBJ, service);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_csb, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.csb_settings) {
            CSBSettingsActivity.start(getViewContext(), mService);
        }

        return super.onOptionsItemSelected(item);
    }
}
