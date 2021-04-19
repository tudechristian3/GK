package com.goodkredit.myapplication.activities.prepaidrequest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.fragments.prepaidrequest.BillingVirtualVoucherFragment;
import com.goodkredit.myapplication.fragments.prepaidrequest.PaymentChannelsFragment;
import com.goodkredit.myapplication.fragments.prepaidrequest.VirtualVoucherProductFragment;

public class VoucherPrepaidRequestActivity extends BaseActivity {

    private String requestingComponent;
    private double mPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_prepaid_request);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        setupToolbar();

        setTitle("Order Vouchers");

        int mIndexSelected = getIntent().getIntExtra("index", -1);
        requestingComponent = getIntent().getStringExtra("args");
        mPoints = getIntent().getDoubleExtra("points", 0);
        String totalAmount = getIntent().getStringExtra("totalamount");

        displayView(mIndexSelected, requestingComponent, totalAmount);
    }

    public void displayView(int id, String requestingComponent, String totalAmount) {
        Fragment fragment = null;
        String title = "";

        switch (id) {
            case 0: {
                fragment = new VirtualVoucherProductFragment();
                title = "Virtual Voucher";
                Bundle b = new Bundle();
                b.putString(VirtualVoucherProductFragment.KEY_MODE, requestingComponent);
                b.putDouble("points", mPoints);
                fragment.setArguments(b);
                break;
            }
            case 1: {
                fragment = new BillingVirtualVoucherFragment();
                title = "Order Vouchers";
                Bundle b = new Bundle();
                b.putString("billingid", requestingComponent);
                b.putString("totalamount", totalAmount);
                fragment.setArguments(b);
                break;
            }
            case 2: {
                fragment = new PaymentChannelsFragment();
                title = "Payment Channels";
                Bundle b = new Bundle();
                b.putString("billingid", requestingComponent);
                b.putString("totalamount", totalAmount);
                fragment.setArguments(b);
                break;
            }

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
//            fragmentTransaction.replace(R.id.container_body, fragment);
//            if (!title.equals("Virtual Voucher")) {
//                fragmentTransaction.addToBackStack(title);
//            }
            fragmentTransaction.commit();

            // set the toolbar title
            setActionBarTitle(title);
        }

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public static void start(Context context, int index, String requestingComponent, double points) {
        Intent intent = new Intent(context, VoucherPrepaidRequestActivity.class);
        intent.putExtra("index", index);
        intent.putExtra("args", requestingComponent);
        intent.putExtra("points", points);
        context.startActivity(intent);
    }

    public static void start(Context context, int index, String billingID, String totalAmount) {
        Intent intent = new Intent(context, VoucherPrepaidRequestActivity.class);
        intent.putExtra("index", index);
        intent.putExtra("args", billingID);
        intent.putExtra("totalamount", totalAmount);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1  && resultCode  == RESULT_OK) {
            String billingid = data.getStringExtra("BILLINGID");
            String mOrderTotal = data.getStringExtra("TOTALORDER");
            displayView(1, billingid, mOrderTotal);
        }

    }
}
