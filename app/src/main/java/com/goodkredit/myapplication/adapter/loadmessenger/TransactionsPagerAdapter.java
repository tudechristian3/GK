package com.goodkredit.myapplication.adapter.loadmessenger;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.goodkredit.myapplication.bean.loadmessenger.BorrowerFB;
import com.goodkredit.myapplication.fragments.loadmessenger.LoadTransactionsFragment;
import com.goodkredit.myapplication.fragments.loadmessenger.ReplenishLogsFragment;

public class TransactionsPagerAdapter  extends FragmentStateAdapter {

    BorrowerFB borrowerFB = null;
    public TransactionsPagerAdapter(@NonNull FragmentActivity fragmentActivity,BorrowerFB borrowerFB) {
        super(fragmentActivity);
        this.borrowerFB = borrowerFB;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position){
            case 0:
                fragment =  LoadTransactionsFragment.newInstance(borrowerFB);
                break;
            case 1:
                fragment =  ReplenishLogsFragment.newInstance(borrowerFB);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
