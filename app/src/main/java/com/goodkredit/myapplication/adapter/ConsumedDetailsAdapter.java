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
import com.goodkredit.myapplication.bean.Transaction;
import com.goodkredit.myapplication.common.CommonVariables;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Ban_Lenovo on 1/16/2017.
 */

public class ConsumedDetailsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Transaction> arrayList;
    LayoutInflater layoutInflater;
    AQuery aq;

    public ConsumedDetailsAdapter(Context context, ArrayList<Transaction> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        aq = new AQuery(this.context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Transaction getItem(int position) {
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
        TextView tvVoucherTransactionNumber;
        TextView tvVoucherSerial;
        TextView tvVoucherAmountConsumed;
        ImageView mVoucherTag;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        try {
            layoutInflater = LayoutInflater.from(context);
            final String prodID = arrayList.get(position).getProdID();
            final String voucherCode = arrayList.get(position).getVoucherCode();
            final String voucherTrans = arrayList.get(position).getTransactionNumber();
            final String voucherSerial = arrayList.get(position).getSerial();
            final String voucherAmountConsumed = arrayList.get(position).getAmount();
            final String vcode = voucherCode.substring(0, 2) + "-" + voucherCode.substring(2, 6) + "-" + voucherCode.substring(6, 11) + "-" + voucherCode.substring(11, 12);

            if (view == null) {
                holder = new ViewHolder();
                view = layoutInflater.inflate(R.layout.consumed_details_item, parent, false);
                holder.imgVproductID = (ImageView) view.findViewById(R.id.detailsVoucherImage);
                holder.tvVoucherCode = (TextView) view.findViewById(R.id.detailsCode);
                holder.tvVoucherTransactionNumber = (TextView) view.findViewById(R.id.detailsTransNo);
                holder.tvVoucherSerial = (TextView) view.findViewById(R.id.detailsSerial);
                holder.tvVoucherAmountConsumed = (TextView) view.findViewById(R.id.detailsConsumed);
                holder.mVoucherTag = (ImageView) view.findViewById(R.id.prepaid_tag);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            DecimalFormat formatter = new DecimalFormat("#,###,##0.00");
            aq.id(holder.imgVproductID).image(buildURL(prodID), false, true);

            if (arrayList.get(position).getExtra3().equals(".")) {
                holder.mVoucherTag.setVisibility(View.GONE);
            } else {
                holder.mVoucherTag.setVisibility(View.VISIBLE);
            }

            holder.tvVoucherCode.setText(vcode);
            holder.tvVoucherTransactionNumber.setText(voucherTrans);
            holder.tvVoucherSerial.setText(voucherSerial);
            holder.tvVoucherAmountConsumed.setText(formatter.format(Double.parseDouble(voucherAmountConsumed)));


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
}
