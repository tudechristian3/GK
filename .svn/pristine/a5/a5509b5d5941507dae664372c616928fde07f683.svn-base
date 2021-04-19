package com.goodkredit.myapplication.adapter.transactions;

import android.content.Context;
import android.content.res.Resources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.fragments.transactions.Consummated;
import com.goodkredit.myapplication.fragments.transactions.OthersFragment;
import com.goodkredit.myapplication.utilities.V2Utils;

/**
 * Created by User-PC on 7/28/2017.
 */

public class ViewTransactionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private final Resources mRes;

    public ViewTransactionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        mRes = mContext.getResources();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return Consummated.newInstance(mRes.getString(R.string.str_usage));
            case 1:
                return OthersFragment.newInstance(mRes.getString(R.string.str_others));
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_REGULAR, mContext.getResources().getStringArray(R.array.transactions_tabs_array)[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
