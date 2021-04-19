package com.goodkredit.myapplication.adapter.prepaidrequest;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.goodkredit.myapplication.fragments.prepaidrequest.BarcodeFormatFragment;

/**
 * Created by User-PC on 7/18/2017.
 */

public class BarcodeFormatPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;
    private final String billingid;


    public BarcodeFormatPagerAdapter(FragmentManager fm, Context context, String billId) {
        super(fm);
        mContext = context;
        billingid = billId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return BarcodeFormatFragment.newInstance(billingid, "CODE_128");
            }
            case 1: {
                return BarcodeFormatFragment.newInstance(billingid, "CODABAR");
            }
            case 2: {
                return BarcodeFormatFragment.newInstance(billingid, "CODE_39");
            }
            case 3: {
                return BarcodeFormatFragment.newInstance(billingid, "QR_CODE");
            }
        }
        return null;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return CommonFunctions.setTypeFace(mContext, "fonts/robotocondensedbold.ttf", mContext.getResources().getStringArray(R.array.shop_tabs_array)[position]);
//    }

    @Override
    public int getCount() {
        return 4;
    }

}
