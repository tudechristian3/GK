package com.goodkredit.myapplication.adapter.whatsnew;


import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.goodkredit.myapplication.fragments.whatsnew.WhatsNewCommonFragment;
import com.goodkredit.myapplication.utilities.V2Utils;

public class WhatsNewVPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public WhatsNewVPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return WhatsNewCommonFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_REGULAR, "New Updates");

            case 1:
                return V2Utils.setTypeFace(mContext, V2Utils.ROBOTO_REGULAR, "Promotions");
        }
        return null;
    }
}
