package com.goodkredit.myapplication.adapter.payment;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonVariables;

import java.util.ArrayList;

public class AvailableVouchersPaymentAdapter extends RecyclerView.Adapter<AvailableVouchersPaymentAdapter.AvailableViewHolder> {

    private ArrayList<Voucher> mAvailableVouchers = new ArrayList<>();
    private Context mContext;

    public AvailableVouchersPaymentAdapter(Context context, ArrayList<Voucher> availableVouchersList) {
        mAvailableVouchers = availableVouchersList;
        mContext = context;
    }

    @Override
    public AvailableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_voucher_normal, parent, false);

        return new AvailableViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AvailableViewHolder holder, int position) {
        try {
            if (!mAvailableVouchers.isEmpty()) {
                Voucher voucher = mAvailableVouchers.get(position);
                String balance;

                if (voucher.getGroupVoucherCode().equals(".")) {
                    if (voucher.getExtra3().equals(".")) {
                        holder.mVoucherTag.setVisibility(View.GONE);
                    } else {
                        holder.mVoucherTag.setVisibility(View.VISIBLE);
                    }

                    Glide.with(mContext)
                            .load(CommonVariables.s3link + voucher.getProductID() + "-voucher-design.jpg")
                            .into(holder.mVoucherImage);

                    holder.mVoucherBalance.setVisibility(View.VISIBLE);
                    holder.mGroupVoucherBalance.setVisibility(View.GONE);
                    holder.mGroupVoucherName.setVisibility(View.GONE);

                    if (voucher.getRemainingBalance() % 1 == 0)
                        balance = String.valueOf((int) voucher.getRemainingBalance());
                    else
                        balance = String.valueOf(voucher.getRemainingBalance());

                    if (balance.length() < 5) {
                        balance = "BAL: " + balance;
                    }

                } else {
                    Glide.with(mContext)
                            .load(CommonVariables.s3link + "group-vouchers.png")
                            .into(holder.mVoucherImage);

                    double doubleBalance = Double.parseDouble(voucher.getGroupBalance());

                    if (doubleBalance % 1 == 0)
                        balance = String.valueOf((int) doubleBalance);
                    else
                        balance = String.valueOf(doubleBalance);

                    holder.mGroupVoucherBalance.setVisibility(View.VISIBLE);
                    holder.mGroupVoucherName.setVisibility(View.VISIBLE);
                    holder.mVoucherBalance.setVisibility(View.GONE);
                    holder.mVoucherTag.setVisibility(View.GONE);
                    holder.mGroupVoucherBalance.setText(voucher.getGroupBalance());
                    if (!voucher.getGroupName().equals("."))
                        holder.mGroupVoucherName.setText(voucher.getGroupName());
                }
                holder.mVoucherBalance.setText(balance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mAvailableVouchers.size();
    }

    public class AvailableViewHolder extends RecyclerView.ViewHolder {

        private ImageView mVoucherImage;
        private TextView mVoucherBalance;
        private TextView mGroupVoucherBalance;
        private TextView mGroupVoucherName;
        private ImageView mVoucherTag;

        public AvailableViewHolder(View itemView) {
            super(itemView);
            mVoucherImage = itemView.findViewById(R.id.imgv_voucher_image);
            mVoucherBalance = itemView.findViewById(R.id.tv_voucher_balance);
            mVoucherTag = itemView.findViewById(R.id.imgv_prepaid_tag);
            mGroupVoucherBalance = itemView.findViewById(R.id.group_voucher_balance);
            mGroupVoucherName = itemView.findViewById(R.id.group_voucher_title);
        }
    }
}
