package com.goodkredit.myapplication.adapter.vouchers;

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

import java.util.ArrayList;

/**
 * Created by Ban_Lenovo on 5/24/2017.
 */

public class GroupDetailsGridViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Voucher> arrayList;
    private LayoutInflater layoutInflater;
    private AQuery aq;

    private boolean isDialog = false;

    public GroupDetailsGridViewAdapter(Context context, ArrayList<Voucher> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        aq = new AQuery(this.context);
    }

    public GroupDetailsGridViewAdapter(Context context, ArrayList<Voucher> arrayList, boolean isDialog) {
        this.context = context;
        this.arrayList = arrayList;
        aq = new AQuery(this.context);
        this.isDialog = isDialog;
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

    public void updateData(ArrayList<Voucher> arrList) {
        arrayList = arrList;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView voucherImage;
        TextView voucherBalance;
        TextView groupBalance;
        TextView groupName;
        ImageView mVoucherTag;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder;
        layoutInflater = LayoutInflater.from(context);


        Voucher voucher = arrayList.get(position);
        try {
            if (view == null) {
                holder = new ViewHolder();
                view = layoutInflater.inflate(R.layout.fragment_available_item, parent, false);
                holder.voucherImage = (ImageView) view.findViewById(R.id.grid_item_image);
                holder.voucherBalance = (TextView) view.findViewById(R.id.grid_item_title);

                holder.groupBalance = (TextView) view.findViewById(R.id.group_voucher_balance);
                holder.groupName = (TextView) view.findViewById(R.id.group_voucher_title);

                holder.mVoucherTag = (ImageView) view.findViewById(R.id.prepaid_tag);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            String strBal = "0";
            aq.id(holder.voucherImage).image(CommonVariables.s3link + voucher.getProductID() + "-voucher-design.jpg", false, true);
            if (voucher.getExtra3().equals(".")) {
                holder.mVoucherTag.setVisibility(View.GONE);
            } else {
                holder.mVoucherTag.setVisibility(View.VISIBLE);
            }
            holder.voucherBalance.setVisibility(View.VISIBLE);
            holder.groupBalance.setVisibility(View.GONE);
            holder.groupName.setVisibility(View.GONE);

            if (voucher.getRemainingBalance() % 1 == 0)
                strBal = String.valueOf((int) voucher.getRemainingBalance());
            else
                strBal = String.valueOf(voucher.getRemainingBalance());

            if (strBal.length() < 5) {
                strBal = "BAL: " + strBal;
            }
            holder.voucherBalance.setText(strBal);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }
}
