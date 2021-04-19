package com.goodkredit.myapplication.adapter.whatsnew;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.goodkredit.myapplication.fragments.whatsnew.GKAdsCommonFragment;
import com.goodkredit.myapplication.model.gkads.GKAds;
import com.goodkredit.myapplication.utilities.CacheManager;

import java.util.List;

public class GKAdsVPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private List<GKAds> mGKAds;

    public GKAdsVPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        mGKAds = CacheManager.getInstance().getPromotions();
    }

    @Override
    public Fragment getItem(int position) {
        return GKAdsCommonFragment.newInstance(mGKAds.get(position));
    }

    @Override
    public int getCount() {
        return mGKAds.size();
    }
}