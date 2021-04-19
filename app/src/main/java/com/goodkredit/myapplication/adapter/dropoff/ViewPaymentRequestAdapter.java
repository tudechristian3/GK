package com.goodkredit.myapplication.adapter.dropoff;

import android.content.Context;
import android.content.res.Resources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.fragments.dropoff.PaymentRequestCompletedFragment;
import com.goodkredit.myapplication.fragments.dropoff.PaymentRequestPendingFragment;

public class ViewPaymentRequestAdapter extends FragmentPagerAdapter{

    private final Context mContext;
    private final Resources mRes;

    public ViewPaymentRequestAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        mRes = mContext.getResources();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return PaymentRequestPendingFragment.newInstance(mRes.getString(R.string.str_pending));
            case 1:
                return PaymentRequestCompletedFragment.newInstance(mRes.getString(R.string.str_completed));
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getStringArray(R.array.paymentrequest_tabs_array)[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
