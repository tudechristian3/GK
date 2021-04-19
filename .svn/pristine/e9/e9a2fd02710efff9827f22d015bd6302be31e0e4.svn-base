package com.goodkredit.myapplication.adapter.dropoff;

import android.content.Context;
import android.content.res.Resources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.fragments.dropoff.DropOffCompletedFragment;
import com.goodkredit.myapplication.fragments.dropoff.DropOffPendingFragment;

public class ViewDropOffAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private final Resources mRes;

    public ViewDropOffAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        mRes = mContext.getResources();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return DropOffPendingFragment.newInstance(mRes.getString(R.string.str_pending));
            case 1:
                return DropOffCompletedFragment.newInstance(mRes.getString(R.string.str_completed));
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
