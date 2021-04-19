package com.goodkredit.myapplication.activities.delivery;

import android.app.Activity;
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
import com.goodkredit.myapplication.fragments.gkstore.GKStoreSetUpMapLocationFragment;
import com.goodkredit.myapplication.fragments.gkstore.StoreInfoMapLocationFragment;
import com.goodkredit.myapplication.fragments.skycable.SkycableMapLocationFragment;

public class SetupDeliveryAddressActivity extends BaseActivity {

    int mDisplayedView = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gkstore_setup_delivery_address);
            //All Initializations

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        setupToolbar();
        int mIndexSelected = getIntent().getIntExtra("index", -1);
        Bundle args = getIntent().getBundleExtra("args");

        displayView(mIndexSelected, args);
    }

    public void displayView(int id, Bundle args) {
        Fragment fragment = null;
        String title = "";
        mDisplayedView = id;

        switch (id) {
            case 0: {
                fragment = new GKStoreSetUpMapLocationFragment();
                title = "Address";
                break;
            }
            case 1: {
                fragment = new SkycableMapLocationFragment();
                title = "Address";
                break;
            }

            case 2: {
                fragment = new StoreInfoMapLocationFragment();
                title = "Address";
                break;
            }

        }

        if (args != null) {
            fragment.setArguments(args);
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (fragment != null) {
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.addToBackStack(null);
            } else {
                fragmentTransaction.add(R.id.container_body, fragment);
            }
            fragmentTransaction.commit();

            // set the toolbar title
            setActionBarTitle(title);
        }
    }

    public static void start(Context context, int index) {
        Intent intent = new Intent(context, SetupDeliveryAddressActivity.class);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
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
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            setResult(Activity.RESULT_CANCELED);
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }
    }


}
