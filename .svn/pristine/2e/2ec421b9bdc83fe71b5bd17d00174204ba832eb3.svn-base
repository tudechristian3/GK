package com.goodkredit.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.Logger;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<Voucher> {

    private CommonFunctions cf;

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<Voucher> mGridData;
    private AQuery aq;

    public GridViewAdapter(Context mContext, int layoutResourceId, ArrayList<Voucher> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;
        this.aq = new AQuery(mContext);
    }


    /**
     * Updates grid data and refresh grid items.
     *
     * @param mGridData
     */
    public void setGridData(ArrayList<Voucher> mGridData) {
        Logger.debug("TAG", "processResult: " + String.valueOf(mGridData.size()));
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    public void clearGridData() {
        this.mGridData.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;
        Voucher voucher = mGridData.get(position);
        try {
            if (row == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new ViewHolder();
                holder.titleTextView = (TextView) row.findViewById(R.id.grid_item_title);
                holder.imageView = (ImageView) row.findViewById(R.id.grid_item_image);
               // holder.editText = (EditText) row.findViewById(R.id.serialno);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }
//            Logger.debug("OkHttp", voucher.getGroupVoucherCode());
            if (voucher.getGroupVoucherCode().equals(".")) {
                aq.id(holder.imageView).image(CommonVariables.s3link + voucher.getProductID() + "-voucher-design.jpg", false, true);
                if (voucher.getRemainingBalance() % 1 == 0)
                    holder.titleTextView.setText("BAL: " + String.valueOf((int) voucher.getRemainingBalance()));
                else
                    holder.titleTextView.setText("BAL: " + String.valueOf(voucher.getRemainingBalance()));
            } else {
                aq.id(holder.imageView).image(CommonVariables.s3link+"group-vouchers.png", false, true);
                holder.titleTextView.setText(voucher.getGroupBalance());
            }
            aq.id(holder.imageView).image(CommonVariables.s3link + voucher.getProductID() + "-voucher-design.jpg", false, true);
            if (voucher.getRemainingBalance() % 1 == 0)
                holder.titleTextView.setText("BAL: " + String.valueOf((int) voucher.getRemainingBalance()));
            else
                holder.titleTextView.setText("BAL: " + String.valueOf(voucher.getRemainingBalance()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    static class ViewHolder {
        TextView titleTextView;
        ImageView imageView;
        EditText editText;
    }


}