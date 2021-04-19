package com.goodkredit.myapplication.adapter.vouchers;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;

import java.util.ArrayList;

/**
 * Created by Ban_Lenovo on 5/10/2017.
 */

public class AvailableVouchersAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Voucher> arrayList;
    private LayoutInflater layoutInflater;
    private AQuery aq;

    public AvailableVouchersAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();
        aq = new AQuery(this.context);
    }

    public AvailableVouchersAdapter(Context context, ArrayList<Voucher> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        aq = new AQuery(this.context);
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
//        if (position < arrayList.size()) {
            return arrayList.get(position).hashCode();
//        }
//        return -1;
    }

    public void updateData(ArrayList<Voucher> arrList) {
        arrayList.clear();
        arrayList = arrList;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView voucherTag;
        ImageView voucherImage;
        TextView voucherBalance;
        AppCompatTextView groupBalance;
        TextView groupName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder;
        layoutInflater = LayoutInflater.from(context);


        Voucher voucher = arrayList.get(position);
//        try {
            if (view == null) {
                holder = new ViewHolder();
                view = layoutInflater.inflate(R.layout.fragment_available_item, parent, false);
                holder.voucherImage = view.findViewById(R.id.grid_item_image);
                holder.voucherBalance = view.findViewById(R.id.grid_item_title);
                holder.voucherTag = view.findViewById(R.id.prepaid_tag);
                holder.groupBalance = view.findViewById(R.id.group_voucher_balance);
                holder.groupName = view.findViewById(R.id.group_voucher_title);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            String strBal;

            if (voucher.getGroupVoucherCode().equals(".")) {
                if (voucher.getExtra3().equals(".")) {
                    holder.voucherTag.setVisibility(View.GONE);
                } else {
                    holder.voucherTag.setVisibility(View.VISIBLE);
                }

                Glide.with(context)
                        .load(CommonVariables.s3link + voucher.getProductID() + "-voucher-design.jpg")
                        .into(holder.voucherImage);

                holder.voucherBalance.setVisibility(View.VISIBLE);
                holder.groupBalance.setVisibility(View.GONE);
                holder.groupName.setVisibility(View.GONE);

                if (voucher.getRemainingBalance() % 1 == 0)
                    strBal = String.valueOf((int) voucher.getRemainingBalance());
                else
                    strBal = String.valueOf(voucher.getRemainingBalance());

            } else {
                Glide.with(context)
                        .load(CommonVariables.s3link + "group-vouchers.png")
                        .into(holder.voucherImage);

                double doubleBalance = Double.parseDouble(voucher.getGroupBalance());

                if (doubleBalance % 1 == 0)
                    strBal = String.valueOf((int) doubleBalance);
                else
                    strBal = String.valueOf(doubleBalance);

                holder.groupBalance.setVisibility(View.VISIBLE);
                holder.groupName.setVisibility(View.VISIBLE);
                holder.voucherBalance.setVisibility(View.GONE);
                holder.voucherTag.setVisibility(View.GONE);
                holder.groupBalance.setText(CommonFunctions.currencyFormatter(voucher.getGroupBalance()));
                if (!voucher.getGroupName().equals("."))
                    holder.groupName.setText(voucher.getGroupName());
            }

            if (strBal.length() < 5) {
                strBal = "BAL: " + strBal;
            }
            holder.voucherBalance.setText(strBal);

//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return view;
    }
}
