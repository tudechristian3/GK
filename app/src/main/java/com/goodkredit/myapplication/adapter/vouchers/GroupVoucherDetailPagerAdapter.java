package com.goodkredit.myapplication.adapter.vouchers;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.fragments.vouchers.GroupVoucherDetailsFragment;
import com.goodkredit.myapplication.fragments.vouchers.GroupVoucherDetailsFragment2;

/**
 * Created by Ban_Lenovo on 5/19/2017.
 */

public class GroupVoucherDetailPagerAdapter extends FragmentPagerAdapter {

    private Voucher mVoucher;

    public GroupVoucherDetailPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return GroupVoucherDetailsFragment.newInstance();
            case 1:
                return GroupVoucherDetailsFragment2.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
