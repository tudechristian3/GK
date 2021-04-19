package com.goodkredit.myapplication.activities.uno;

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
import com.goodkredit.myapplication.fragments.uno.UnoRewardsFragment;

public class UnoRewardsActivity extends BaseActivity {

    private static final String KEY_PROVIDER_ID = "provider_id";

    public static final int UNO_REWARDS = 101;

    private GKService mService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_rewards);
        init();
    }

    private void init() {

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
                case UNO_REWARDS:
                    fragment = UnoRewardsFragment.newInstance();
                    title = "UNO";
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
        Intent intent = new Intent(context, UnoRewardsActivity.class);
        intent.putExtra(KEY_PROVIDER_ID, provider_id);
        intent.putExtra(GKService.KEY_SERVICE_OBJ, service);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
