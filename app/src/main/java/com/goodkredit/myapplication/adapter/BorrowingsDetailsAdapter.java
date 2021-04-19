package com.goodkredit.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonVariables;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Ban_Lenovo on 12/28/2016.
 */

public class BorrowingsDetailsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Voucher> arrayList;
    private LayoutInflater layoutInflater;
    private AQuery aq;
    private boolean isPrepaid = false;

    public BorrowingsDetailsAdapter(Context context, ArrayList<Voucher> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        aq = new AQuery(this.context);
    }

    public BorrowingsDetailsAdapter(Context context, ArrayList<Voucher> arrayList, boolean isPrepaid) {
        this.context = context;
        this.arrayList = arrayList;
        aq = new AQuery(this.context);
        this.isPrepaid = isPrepaid;
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Voucher getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).hashCode();
    }

    static class ViewHolder {
        //contains declaration of views e.g TextView, Button
        ImageView imgVproductID;
        TextView tvVoucherCode;
        TextView tvVoucherBalance;
        ImageView mVoucherTag;
        TextView tvVoucherSerialNo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder;
        try {
            layoutInflater = LayoutInflater.from(context);
            final String prodID = arrayList.get(position).getProductID();
            final String voucherCode = arrayList.get(position).getVoucherCode();
            final double voucherBalance = arrayList.get(position).getVoucherDenom();
            final String voucherSerialNum = arrayList.get(position).getVoucherSerialNo();
            final String vcode = voucherCode.substring(0, 2) + "-" + voucherCode.substring(2, 6) + "-" + voucherCode.substring(6, 11) + "-" + voucherCode.substring(11, 12);

            if (view == null) {
                holder = new ViewHolder();
                view = layoutInflater.inflate(R.layout.activity_borrowings_details_item, parent, false);
                holder.imgVproductID = (ImageView) view.findViewById(R.id.voucherLogo);
                holder.tvVoucherCode = (TextView) view.findViewById(R.id.vouchercode);
                holder.tvVoucherBalance = (TextView) view.findViewById(R.id.totalamount);
                holder.mVoucherTag = (ImageView) view.findViewById(R.id.prepaid_tag);
                holder.tvVoucherSerialNo = (TextView) view.findViewById(R.id.serialNum);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            aq.id(holder.imgVproductID).image(buildURL(prodID), false, true);
            holder.tvVoucherCode.setText(vcode);
            DecimalFormat formatter = new DecimalFormat("#,###,##0.00");
            holder.tvVoucherBalance.setText(formatter.format((voucherBalance)));
            if (isPrepaid) {
                holder.mVoucherTag.setVisibility(View.VISIBLE);
            }

            holder.tvVoucherSerialNo.setText(voucherSerialNum);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    //url builder for voucher image
    private String buildURL(String prodID) {
        String ext = prodID + "-voucher-design.jpg";
        String builtURL = CommonVariables.s3link + ext;
        return builtURL;
    }

    public void updateData(ArrayList<Voucher> arrayList) {
        this.arrayList.clear();
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
}
