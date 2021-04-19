package com.goodkredit.myapplication.adapter.payment;

import android.content.ClipData;
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
import com.goodkredit.myapplication.common.PaymentActivity;

import java.util.ArrayList;

public class PaymentVouchersPaymentAdapter extends RecyclerView.Adapter<PaymentVouchersPaymentAdapter.PaymentViewHolder> {

    private Context mContext;
    private ArrayList<Voucher> mVoucherListData;
    private int mPosition = 0;
    private Voucher mVoucher;
    private PaymentActivity mPaymentActivity;

    public PaymentVouchersPaymentAdapter(Context context, ArrayList<Voucher> vouchersData) {
        mContext = context;
        mVoucherListData = vouchersData;
        mPaymentActivity = (PaymentActivity) mContext;
    }

    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group_voucher, parent, false);

        return new PaymentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PaymentViewHolder holder, int position) {
        try {
            Voucher voucher = mVoucherListData.get(position);

            if (!mVoucherListData.isEmpty()) {
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
            holder.itemView.setTag(voucher);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    setClickedPosition(holder.getPosition());
                    setClickedVoucher(mVoucherListData.get(getClickedPosition()));

                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadow = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadow, null, 0);

                    return false;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getClickedPosition() {
        return mPosition;
    }

    private void setClickedPosition(int pos) {
        mPosition = pos;
    }

    private void setClickedVoucher(Voucher voucher) {
        mVoucher = voucher;
    }

    @Override
    public int getItemCount() {
        return mVoucherListData.size();
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder {
        private ImageView mVoucherImage;
        private TextView mVoucherBalance;
        private TextView mGroupVoucherBalance;
        private TextView mGroupVoucherName;
        private ImageView mVoucherTag;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            mVoucherImage = itemView.findViewById(R.id.imgv_voucher_image);
            mVoucherBalance = itemView.findViewById(R.id.tv_voucher_balance);
            mVoucherTag = itemView.findViewById(R.id.imgv_prepaid_tag);
            mGroupVoucherBalance = itemView.findViewById(R.id.group_voucher_balance);
            mGroupVoucherName = itemView.findViewById(R.id.group_voucher_title);
        }
    }
}
