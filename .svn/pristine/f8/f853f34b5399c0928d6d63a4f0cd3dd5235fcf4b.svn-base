package com.goodkredit.myapplication.activities.dropoff;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.fragments.dropoff.PaymentRequestCompletedFragment;
import com.goodkredit.myapplication.fragments.dropoff.PaymentRequestFragment;
import com.goodkredit.myapplication.fragments.dropoff.PaymentRequestPendingFragment;
import com.goodkredit.myapplication.utilities.Logger;

public class PaymentRequestActivity extends BaseActivity {

    private int mDisplayedView = 0;
    private Fragment fragment = null;
    private Bundle globalargs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropoff_paymentrequest);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

        Logger.debug("vanhttp", "okay, paymentrequestactivity");

        try {
            init();
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        setupToolbar();
    }

    private void initData() {
        int mIndexofFragment = getIntent().getIntExtra("index", -1);
        Bundle args = getIntent().getBundleExtra("args");
        displayView(mIndexofFragment, args);
    }

    public void displayView(int position, Bundle args){
        String title = "";
        mDisplayedView = position;

        try{
            switch (position){
                case 1: {
                    fragment = new PaymentRequestFragment();
                    break;
                }
                case 2: {
                    fragment = new PaymentRequestPendingFragment();
                    break;
                }
                case 3: {
                    fragment = new PaymentRequestCompletedFragment();
                    break;
                }
            }

            if(args != null){
                fragment.setArguments(args);
                globalargs = args;
            }

            if(fragment != null){
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.container_body, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void start(Context context, int index, GKService service, Bundle args) {
        Intent intent = new Intent(context, PaymentRequestActivity.class);
        intent.putExtra("index", index);
        intent.putExtra(GKService.KEY_SERVICE_OBJ, service);
        intent.putExtra("args", args);
        context.startActivity(intent);
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
        if(getSupportFragmentManager().getBackStackEntryCount() > 1){
            getSupportFragmentManager().popBackStack();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);

        } else{
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }
    }
}
