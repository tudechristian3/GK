package com.goodkredit.myapplication.adapter.vouchers.payoutone;

import android.content.Context;
import android.content.res.Resources;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.fragments.vouchers.payoutone.VoucherPayoutOneBankDepositHistoryFragment;
import com.goodkredit.myapplication.fragments.vouchers.payoutone.VoucherPayoutOneBankDepositQueueFragment;
import com.goodkredit.myapplication.utilities.V2Utils;

public class ViewVoucherPayoutOneBankDepositAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private final Resources mRes;

    public ViewVoucherPayoutOneBankDepositAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        mRes = mContext.getResources();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return VoucherPayoutOneBankDepositQueueFragment.newInstance(mRes.getString(R.string.str_payoutone_queue));
            case 1:
                return VoucherPayoutOneBankDepositHistoryFragment.newInstance(mRes.getString(R.string.str_payoutone_history));
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_REGULAR, mContext.getResources().getStringArray(R.array.payoutone_tabs_array)[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
