package com.goodkredit.myapplication.fragments.coopassistant.member;

import android.content.Context;
import android.content.res.Resources;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.utilities.V2Utils;

public class ViewCoopAssistantSOAPagerAdapter extends FragmentPagerAdapter {


    private final Context mContext;
    private final Resources mRes;

    public ViewCoopAssistantSOAPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        mRes = mContext.getResources();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return CoopAssistantBillsFragment.newInstance(mRes.getString(R.string.str_coopassistant_bills));
            case 1:
                return CoopAssistantPaymentsFragment.newInstance(mRes.getString(R.string.str_coopassistant_payments));
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_REGULAR, mContext.getResources().getStringArray(R.array.coopassistantsoaandpayments_tabs_array)[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
