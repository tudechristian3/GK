package com.goodkredit.myapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.VoucherSummary;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.Logger;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by user on 2/2/2017.
 */

public class VoucherSummeryAdapter extends BaseAdapter {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<VoucherSummary> mListData;
    LayoutInflater layoutInflater;
    AQuery aq;
    private CommonFunctions cf;

    public VoucherSummeryAdapter(Context context, int layoutResourceId) {
        this.mContext = context;
        this.mListData = new ArrayList<>();
        this.layoutResourceId = layoutResourceId;
    }

    public VoucherSummeryAdapter(Context mContext, int layoutResourceId, ArrayList<VoucherSummary> mListData) {
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mListData = new ArrayList<>();
        this.mListData = mListData;
    }


    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public VoucherSummary getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mListData.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        VoucherSummeryAdapter.ViewHolder holder;
        aq = new AQuery(mContext);
        try {

            layoutInflater = LayoutInflater.from(mContext);
            VoucherSummary item = mListData.get(position);

            if (row == null) {


                row = layoutInflater.inflate(layoutResourceId, parent, false);
                holder = new VoucherSummeryAdapter.ViewHolder();
                holder.voucherimage = (ImageView) row.findViewById(R.id.voucherimage);
                holder.totalbalance = (TextView) row.findViewById(R.id.totalbalance);
                holder.totalnumber = (TextView) row.findViewById(R.id.totalnumber);
                holder.totaldenoms = (TextView) row.findViewById(R.id.totaldenom);

                row.setTag(holder);
            } else {
                holder = (VoucherSummeryAdapter.ViewHolder) row.getTag();
            }
            DecimalFormat formatter = new DecimalFormat("#,###,##0.00");
            holder.totalbalance.setText("BAL: " + formatter.format(item.getTotalBalance()));
            holder.totalnumber.setText(String.valueOf(item.getTotalNumber()));
            holder.totaldenoms.setText("Denom: " + formatter.format(item.getTotalDenom()));
            aq.id(holder.voucherimage).image(CommonVariables.s3link + item.getProductID() + "-voucher-design.jpg", false, true);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return row;
    }

    static class ViewHolder {
        ImageView voucherimage;
        TextView totalbalance;
        TextView totalnumber;
        TextView totaldenoms;
    }

    public void setData(ArrayList<VoucherSummary> arrayList) {
        Logger.debug("antonhttp", "adapter summary voucher: " + new Gson().toJson(arrayList));
        this.mListData = arrayList;
        notifyDataSetChanged();
    }


}
