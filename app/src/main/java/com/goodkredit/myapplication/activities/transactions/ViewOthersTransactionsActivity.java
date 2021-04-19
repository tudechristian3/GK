package com.goodkredit.myapplication.activities.transactions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.fragments.transactions.BorrowingsFragment;
import com.goodkredit.myapplication.fragments.transactions.CSBRedemptionHistoryFragment;
import com.goodkredit.myapplication.fragments.transactions.LogsBillsPaymentFragment;
import com.goodkredit.myapplication.fragments.transactions.LogsPrepaidFragment;
import com.goodkredit.myapplication.fragments.transactions.LogsRetailerReloadingFragment;
import com.goodkredit.myapplication.fragments.transactions.PurchasesFragmentV2;
import com.goodkredit.myapplication.fragments.transactions.ReceivedFragment;
import com.goodkredit.myapplication.fragments.transactions.TransferredFragment;
import com.goodkredit.myapplication.fragments.uno.UNORedemptionHistoryFragment;

import java.util.Objects;

public class ViewOthersTransactionsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_others_transactions);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

        //set toolbar
        setupToolbar();

        int mIndexSelected = getIntent().getIntExtra("index", -1);
        displayView(mIndexSelected, null);
    }

    private void displayView(int id, Bundle args) {
        Fragment fragment = null;
        String title = "";

       

        switch (id) {
            case 0: {
                fragment = new BorrowingsFragment();
                title = "Borrowings";
                break;
            }
            case 1: {
                fragment = new PurchasesFragmentV2();
                title = "Purchases";
                break;
            }
            case 2: {
                fragment = new TransferredFragment();
                title = "Transferred Vouchers";
                break;
            }
            case 3: {
                fragment = new ReceivedFragment();
                title = "Received Vouchers";
                break;
            }
            case 4: {
                fragment = new LogsPrepaidFragment();
                title = "Logs of Prepaid Load";
                break;
            }
            case 5: {
                fragment = new LogsBillsPaymentFragment();
                title = "Logs of Bills Payment";
                break;
            }
            case 6: {
                fragment = new LogsRetailerReloadingFragment();
                title = "Logs of Smart Retailer Reloading";
                break;
            }
            case 7: {
                fragment = new CSBRedemptionHistoryFragment();
                title = "CSB Redemption History";
                break;
            }
            case 8: {
                fragment = new UNORedemptionHistoryFragment();
                title = "UNO Redemption History";
                break;
            }
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            setActionBarTitle(title);
        }
    }

    private void setActionBarTitle(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context, int index) {
        Intent intent = new Intent(context, ViewOthersTransactionsActivity.class);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }
}
