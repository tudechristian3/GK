package com.goodkredit.myapplication.adapter.gkservices;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.fragments.vouchers.GKServicesFragment;

/**
 * Created by Ban_Lenovo on 10/25/2017.
 */

public class GKServicesVPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private DatabaseHandler db;
    private int mSize = 0;

    public GKServicesVPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        db = new DatabaseHandler(mContext);
    }

    public GKServicesVPagerAdapter(FragmentManager fm, Context context, int size) {
        super(fm);
        mContext = context;
        db = new DatabaseHandler(mContext);
        mSize = size;
    }

    public void setSize(int size){
        mSize = size;
    }

    @Override
    public Fragment getItem(int position) {
        return GKServicesFragment.newInstance(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        //final int EXCLUDE_SERVICES = 11;
        final int EXCLUDE_SERVICES = db.getCountGKServicesCount(db);
        float nofPages = 0;
        try {
            //db.getGKServices(db).size()

            nofPages = ((float) mSize - EXCLUDE_SERVICES) / (float) 12;

            if (mSize - EXCLUDE_SERVICES > 12) {
                nofPages = (int) Math.ceil(nofPages);
            } else {
                nofPages = 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }

        return (int) nofPages;
    }
}
